package k11.guildwars2eventviewer.Callbacks;

import k11.guildwars2eventviewer.DataClasses.MapName;

import java.util.ArrayList;

/**
 * Created by bay on 5/20/13.
 */
public interface MapNameListCallback {
    void success(ArrayList<MapName> output);
    void failure(Exception e);
}
