package k11.guildwars2eventviewer;

import android.app.Application;
import android.content.SharedPreferences;
import k11.guildwars2eventviewer.DataClasses.EventName;
import k11.guildwars2eventviewer.DataClasses.MapName;
import k11.guildwars2eventviewer.DataClasses.WorldName;
import k11.guildwars2eventviewer.DataClasses.WorldName;

import java.util.ArrayList;

/**
 * Created by bay on 5/20/13.
 */
public class EventViewerApplication extends Application {
    public static final String URL = "https://api.guildwars2.com/v1";

    private ArrayList<EventName> eventNames;
    private ArrayList<MapName> mapNames;
    private ArrayList<WorldName> worldNames;

    public void setHomeWorld(WorldName homeWorld) {
        SharedPreferences.Editor ed = getSharedPreferences("GW2EventViewer",MODE_PRIVATE).edit();
        ed.putInt("HomeWorldID", homeWorld.getId());
        ed.putString("HomeWorldName", homeWorld.getName());
        ed.commit();
    }

    public WorldName getHomeWorld() {
        WorldName homeWorld = new WorldName();
        homeWorld.setId(getSharedPreferences("GW2EventViewer",MODE_PRIVATE).getInt("HomeWorldID", 0));
        homeWorld.setName(getSharedPreferences("GW2EventViewer", MODE_PRIVATE).getString("HomeWorldName", ""));
        return homeWorld;
    }

    public void setRefresh(Integer refresh) {
        SharedPreferences.Editor ed = getSharedPreferences("GW2EventViewer",MODE_PRIVATE).edit();
        ed.putInt("Refresh", refresh);
        ed.commit();
    }

    public Integer getRefresh() {
        return getSharedPreferences("GW2EventViewer",MODE_PRIVATE).getInt("Refresh", 30);
    }

    public ArrayList<EventName> getEventNames() {
        return eventNames;
    }

    public void setEventNames(ArrayList<EventName> eventNames) {
        this.eventNames = eventNames;
    }

    public ArrayList<MapName> getMapNames() {
        return mapNames;
    }

    public void setMapNames(ArrayList<MapName> mapNames) {
        this.mapNames = mapNames;
    }

    public ArrayList<WorldName> getWorldNames() {
        return worldNames;
    }

    public void setWorldNames(ArrayList<WorldName> worldNames) {
        this.worldNames = worldNames;
    }
}
