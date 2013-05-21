package k11.guildwars2eventviewer.Callbacks;

import k11.guildwars2eventviewer.DataClasses.WorldName;
import k11.guildwars2eventviewer.DataClasses.WorldName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by bay on 5/20/13.
 */
public interface WorldListCallback {
    void success(ArrayList<WorldName> output);
    void failure(Exception e);
}
