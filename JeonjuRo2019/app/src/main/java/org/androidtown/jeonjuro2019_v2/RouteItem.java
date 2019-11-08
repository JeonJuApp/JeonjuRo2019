package org.androidtown.jeonjuro2019_v2;

import java.util.ArrayList;

public class RouteItem {
    private ArrayList<ItemData> routeList;
    private String totalTime;
    private String title;

    public void setRouteList(ArrayList<ItemData> routeList) {
        this.routeList = routeList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotalTime(String totalTile) {
        this.totalTime = totalTile;
    }

    public ArrayList<ItemData> getRouteList() {
        return routeList;
    }

    public String getTitle() {
        return title;
    }

    public String getTotalTime() {
        return totalTime;
    }
}
