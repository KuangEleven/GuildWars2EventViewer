package k11.guildwars2eventviewer.Callbacks;

import k11.guildwars2eventviewer.DataClasses.EventName;

import java.util.ArrayList;

/**
 * Created by bay on 5/20/13.
 */
public interface EventNameListCallback {
    void success(ArrayList<EventName> output);
    void failure(Exception e);
}
