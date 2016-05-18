package com.restart.lolinfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.restart.lolinfo.adapter.CustomListAdapter;
import com.restart.lolinfo.app.AppController;
import com.restart.lolinfo.app.CircleImageView;
import com.restart.lolinfo.app.CustomVolleyRequest;
import com.restart.lolinfo.model.Convert;
import com.restart.lolinfo.model.Match;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = ".MainActivity";

    private ProgressDialog pDialog;
    private List<Match> matchList = new ArrayList<>();
    private CustomListAdapter adapter;
    private SharedPreferences account;
    private NetworkImageView imageView;
    private String summoner_name;
    private String summoner_region;
    private long summoner_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        account = getSharedPreferences("savefile", MODE_PRIVATE);
        summoner_id = account.getLong(getString(R.string.summoner_id), -1);
        summoner_region = account.getString(getString(R.string.summoner_region), "NA");
        summoner_name = account.getString(getString(R.string.summoner_name), "-1");

        if (summoner_id == -1) {
            askSummoner();
        } else {
            getSummoner(summoner_name, summoner_region);
        }
    }

    private void askSummoner() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("New User");
        alertDialog.setMessage("Enter summoner name and region");
        alertDialog.setIcon(R.drawable.ic_account_circle);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", null);

        final EditText input = new EditText(MainActivity.this);
        input.setTextColor(Color.BLACK);
        input.setPadding(20, 0, 30, 30);

        final Spinner spinner = new Spinner(MainActivity.this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(MainActivity.this,
                R.array.regions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPadding(0, 60, 0, 60);

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(80, 0, 170, 0);

        layout.addView(input, params);
        layout.addView(spinner, params);
        alertDialog.setView(layout);

        final AlertDialog final_dialog = alertDialog.create();
        final_dialog.show();

        final_dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input.getText().toString();
                if (name.compareTo("") != 0) {
                    getSummoner(name, spinner.getSelectedItem().toString());
                    Toast.makeText(MainActivity.this, "You can always change your summoner by going to settings",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Nothing was entered", Toast.LENGTH_SHORT).show();
                    return;
                }
                final_dialog.dismiss();
            }
        });
    }

    private void getSummoner(final String name, final String region) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://na.api.pvp.net/api/lol/" +
                region +
                "/v1.4/summoner/by-name/" +
                name +
                "?api_key=cbc7f3e0-ba8d-4713-bf40-10f4dbdb476e";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject(name.toLowerCase());
                    String name = response.getString("name");
                    long id = response.getLong("id");
                    Log.e(TAG, id + "");
                    int profile_icon = response.getInt("profileIconId");
                    long summoner_level = response.getLong("summonerLevel");
                    account.edit().putString(getString(R.string.summoner_name), name).apply();
                    account.edit().putLong(getString(R.string.summoner_id), id).apply();
                    account.edit().putString(getString(R.string.summoner_region), region).apply();
                    summoner_name = name;
                    summoner_id = id;
                    summoner_region = region;

                    TextView level_drawer = (TextView) findViewById(R.id.level);
                    TextView name_drawer = (TextView) findViewById(R.id.name);
                    imageView = (CircleImageView) findViewById(R.id.icon);

                    String level = "Level: " + summoner_level;
                    level_drawer.setText(level);
                    name_drawer.setText(name);
                    String link = "http://ddragon.leagueoflegends.com/cdn/6.9.1/img/profileicon/" +
                            profile_icon +
                            ".png";
                    loadImage(imageView, R.id.icon, link);
                    matchHistory(id, region, false);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                int networkResponse = e.networkResponse.statusCode;

                String reason = "Something went wrong. Error: " + networkResponse;

                Toast.makeText(MainActivity.this, reason + ".", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void matchHistory(long id, String region, boolean isRefresh) {
        ListView listView = (ListView) findViewById(R.id.list);
        if (isRefresh) {
            matchList = new ArrayList<>();
        }

        adapter = new CustomListAdapter(this, matchList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Champion_Desc.class);
                startActivity(intent);
            }
        });

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url = "https://na.api.pvp.net/api/lol/" +
                region +
                "/v1.3/game/by-summoner/" +
                summoner_id +
                "/recent?api_key=cbc7f3e0-ba8d-4713-bf40-10f4dbdb476e";
        JsonObjectRequest matchReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        try {
                            JSONArray games = response.getJSONArray("games");

                            for (int i = 0; i < games.length(); i++) {
                                JSONObject obj = games.getJSONObject(i);
                                JSONObject stats = obj.getJSONObject("stats");

                                Match match = new Match();
                                match.setQueue(Convert.getGame(obj.getString("subType")));
                                getChampion(obj.getInt("championId"), match);
                                match.setTime(obj.getLong("createDate"));
                                match.setWin(stats.getBoolean("win"));

                                matchList.add(match);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                int networkResponse = e.networkResponse.statusCode;

                String reason = "Something went wrong. Error: " + networkResponse;

                Toast.makeText(MainActivity.this, reason + ".", Toast.LENGTH_LONG).show();
                hidePDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(matchReq);
    }

    private void getChampion(int champion_id, final Match match) {
        String url = "https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion/" +
                champion_id +
                "?api_key=cbc7f3e0-ba8d-4713-bf40-10f4dbdb476e";
        JsonObjectRequest matchReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        try {
                            match.setChampion(response.getString("name"));
                            String title = response.getString("title").substring(0, 1).toUpperCase()
                                    + response.getString("title").substring(1);
                            match.setLane_role(title);
                            String key = response.getString("key");
                            match.setThumbnailUrl("http://ddragon.leagueoflegends.com/cdn/6.9.1/img/champion/" +
                                    key +
                                    ".png");
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                int networkResponse = e.networkResponse.statusCode;

                String reason = "Something went wrong. Error: " + networkResponse;

                Toast.makeText(MainActivity.this, reason + ".", Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(matchReq);
    }

    private void loadImage(NetworkImageView imageView, int view, String link) {
        ImageLoader imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(link, ImageLoader.getImageListener(imageView,
                view, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(link, imageLoader);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_refresh) {
            matchHistory(summoner_id, summoner_region, true);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
