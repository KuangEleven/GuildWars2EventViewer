package k11.guildwars2eventviewer.Callbacks;

import k11.guildwars2eventviewer.DataClasses.Event;

import java.util.ArrayList;

/**
 * Created by bay on 5/20/13.
 */
public interface EventListCallback {
    void success(ArrayList<Event> output);
    void failure(Exception e);
}
