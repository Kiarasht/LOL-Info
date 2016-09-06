package com.restart.lolinfo.model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Convert {
    public static String getGame(String game) {
        switch (game) {
            case "NONE":
                game = "None";
                break;
            case "NORMAL":
                game = "Normal";
                break;
            case "NORMAL_3x3":
                game = "Normal TT";
                break;
            case "BOT":
                game = "Bot";
                break;
            case "BOT_3x3":
                game = "Bot TT";
                break;
            case "RANKED_SOLO_5x5":
                game = "Ranked Solo";
                break;
            case "RANKED_PREMADE_5x5":
                game = "Ranked Premade";
                break;
            case "RANKED_TEAM_5x5":
                game = "Ranked Team";
                break;
            case "RANKED_PREMADE_3x3":
                game = "Ranked Premade TT";
                break;
            case "RANKED_TEAM_3x3":
                game = "Ranked Team TT";
                break;
            case "ODIN_UNRANKED":
                game = "Dominion";
                break;
            case "CAP_5x5":
                game = "Cap (5v5)";
                break;
            case "ARAM_UNRANKED_5x5":
                game = "Aram";
                break;
            case "ONEFORALL_5x5":
                game = "One for All";
                break;
            case "FIRSTBLOOD_1x1":
                game = "Snowdown Showdown 1v1";
                break;
            case "FIRSTBLOOD_2x2":
                game = "Snowdown Showdown 2v2";
                break;
            case "SR_6x6":
                game = "HexaKill";
                break;
            case "URF":
                game = "URF";
                break;
            case "URF_BOT":
                game = "URF Bot";
                break;
            case "NIGHTMARE_BOT":
                game = "Doom Bot";
                break;
            case "ASCENSION":
                game = "Ascension";
                break;
            case "HEXAKILL":
                game = "HexaKill TT";
                break;
            case "KING_PORO":
                game = "King Poro";
                break;
            case "COUNTER_PICK":
                game = "Counter Pick";
                break;
            case "BILGEWATER":
                game = "Bilgewater";
                break;
            case "SIEGE":
                game = "Nexus Siege";
                break;
        }
        return game;
    }

    public static String getDate(long past) {
        Date old = new Date(past);
        Date current = new Date();
        long difference = current.getTime() - old.getTime();

        int difference_minutes = (int) TimeUnit.MILLISECONDS.toMinutes(difference);
        int difference_hours = (int) TimeUnit.MILLISECONDS.toHours(difference);
        int difference_days = (int) TimeUnit.MILLISECONDS.toDays(difference);
        int difference_months = difference_days / 30;

        if (difference_minutes <= 1) {
            return "Just Now";
        } else if (difference_minutes < 60) {
            return String.valueOf(difference_minutes) + " minutes ago";
        } else if (difference_hours < 24) {
            if (difference_hours == 1) return "1 hour ago";
            return String.valueOf(difference_hours) + " hours ago";
        } else if (difference_days < 30) {
            if (difference_days == 1) return "1 day ago";
            return String.valueOf(difference_days) + " days ago";
        } else {
            return String.valueOf(difference_months) + " months ago";
        }
    }

}
