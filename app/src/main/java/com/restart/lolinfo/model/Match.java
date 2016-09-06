package com.restart.lolinfo.model;


public class Match {
    private String queue, thumbnailUrl, champion, stats, spell1, spell2;
    private long time;
    private boolean win;
    private int[] items;

    public Match() {
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String name) {
        this.queue = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getChampion() {
        return champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String lane_role) {
        this.stats = lane_role;
    }

    public boolean getWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int[] getItems() {
        return items;
    }

    public void setItems(int[] items) {
        this.items = items;
    }

    public String getSpell1() {
        return spell1;
    }

    public void setSpell1(String spell1) {
        this.spell1 = spell1;
    }

    public String getSpell2() {
        return spell2;
    }

    public void setSpell2(String spell2) {
        this.spell2 = spell2;
    }
}
