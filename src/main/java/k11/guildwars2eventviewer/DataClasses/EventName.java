package k11.guildwars2eventviewer.DataClasses;

import k11.guildwars2eventviewer.Callbacks.EventNameListCallback;
import k11.guildwars2eventviewer.Callbacks.HTTPCallback;
import k11.guildwars2eventviewer.Callbacks.WorldListCallback;
import k11.guildwars2eventviewer.EventViewerApplication;
import k11.guildwars2eventviewer.HTTPTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by bay on 5/20/13.
 */
public class EventName {
    private String id;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void parseJSON(JSONObject jsonObject) throws JSONException, ParseException {
        id = jsonObject.getString("id");
        name = jsonObject.getString("name");
    }

    static public void readList(EventViewerApplication application, final EventNameListCallback callback) {
        new HTTPTask("GET", application.URL + "/event_names.json").execute(new HTTPCallback() {

            @Override
            public void success(String output) {
                ArrayList<EventName> eventNameList = new ArrayList<EventName>();
                try {
                    JSONArray jsonArray = new JSONArray(output);
                    int length = jsonArray.length();
                    for (int i=0; i<length; i++) {
                        EventName eventName = new EventName();
                        eventName.parseJSON(jsonArray.getJSONObject(i));
                        eventNameList.add(eventName);
                    }
                    callback.success(eventNameList);
                } catch (Exception e) {
                    callback.failure(e);
                }
            }

            @Override
            public void failure(Exception e) {
                callback.failure(e);
            }

        });

    }
}
