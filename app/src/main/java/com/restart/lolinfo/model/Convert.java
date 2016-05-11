package com.restart.lolinfo.model;


import java.util.Date;

public class Convert {
    public static String getLane(String lane) throws Exception {
        switch (lane) {
            case "MID":
            case "MIDDLE":
                lane = "Middle";
                break;
            case "TOP":
                lane = "Top";
                break;
            case "JUNGLE":
                lane = "Jungle";
                break;
            case "BOT":
            case "BOTTOM":
                lane = "Bottom";
                break;
            default:
                throw new Exception("Invalid Lane");
        }
        return lane;
    }

    public static String getRole(String role) throws Exception {
        switch (role) {
            case "DUO":
                role = "Duo";
                break;
            case "NONE":
                role = "None";
                break;
            case "SOLO":
                role = "Solo";
                break;
            case "DUO_CARRY":
                role = "Carry";
                break;
            case "DUO_SUPPORT":
                role = "Support";
                break;
            default:
                throw new Exception("Invalid Role");
        }
        return role;
    }

    public static String getGame(String game) {
        switch (game) {
            case "NONE":
                game = "None";
                break;
            case "NORMAL":
                game = "Normal";
                break;
            case "BOT":
                game = "Bot";
                break;
            case "RANKED_SOLO_5x5":
                game = "Ranked Solo (5v5)";
                break;
            case "RANKED_PREMADE_3x3":
                game = "Ranked Premade (3v3)";
                break;
            case "RANKED_PREMADE_5x5":
                game = "Ranked Premade (5v5)";
                break;
            case "ODIN_UNRANKED":
                game = "Dominion";
                break;
            case "RANKED_TEAM_3x3":
                game = "Ranked Team (3v3)";
                break;
            case "RANKED_TEAM_5x5":
                game = "Ranked Team (5v5)";
                break;
            case "NORMAL_3x3":
                game = "Normal (3v3)";
                break;
            case "BOT_3x3":
                game = "Bot (3v3)";
                break;
            case "CAP_5x5":
                game = "Cap (5v5)";
                break;
            case "ARAM_UNRANKED_5x5":
                game = "ARAM";
                break;
            case "ONEFORALL_5x5":
                game = "One for All";
                break;
            case "FIRSTBLOOD_1x1":
                game = "Snowdown Showdown (1v1)";
                break;
            case "FIRSTBLOOD_2x2":
                game = "Snowdown Showdown (2v2)";
                break;
            case "SR_6x6":
                game = "HexaKill (SR)";
                break;
            case "URF":
                game = "URF";
                break;
            case "URF_BOT":
                game = "URF Bot";
                break;
            case "NIGHTMARE_BOT":
                game = "Nightmare Bot";
                break;
            case "ASCENSION":
                game = "Ascension";
                break;
            case "HEXAKILL":
                game = "HexaKill (TT)";
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
        }
        return game;
    }

    public static String getDate(long past) {
        long current = new Date().getTime();

        long difference = Math.abs(past - current);
        long difference_seconds = (difference / 1000);
        long difference_minutes = (difference / (60 * difference_seconds));
        long difference_hours = (difference / (60 * difference_minutes));
        long difference_days = (difference / (24 * difference_hours));

        if (difference_minutes < 1) {
            return String.valueOf((int) difference_seconds);
        } else if (difference_hours < 1) {
            return String.valueOf((int) difference_minutes);
        } else if (difference_days < 1) {
            return String.valueOf((int) difference_hours);
        } else {
            return String.valueOf((int) difference_days);
        }
    }

}
