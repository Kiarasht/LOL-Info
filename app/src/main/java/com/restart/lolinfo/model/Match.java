package com.restart.lolinfo.model;


public class Match {
    private String queue, thumbnailUrl, champion, lane_role;
    private long time;

    public Match() {
    }

    public Match(String queue, String thumbnailUrl, String champion, String genre, long time) {
        this.queue = queue;
        this.thumbnailUrl = thumbnailUrl;
        this.time = time;
        this.champion = champion;
        this.lane_role = genre;
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

    public String getLane_role() {
        return lane_role;
    }

    public void setLane_role(String lane_role) {
        this.lane_role = lane_role;
    }

}
