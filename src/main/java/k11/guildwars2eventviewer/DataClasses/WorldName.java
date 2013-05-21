package k11.guildwars2eventviewer.DataClasses;

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
public class WorldName {
    private Integer id;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void parseJSON(JSONObject jsonObject) throws JSONException, ParseException {
        id = jsonObject.getInt("id");
        name = jsonObject.getString("name");
    }

    static public void readList(EventViewerApplication application, final WorldListCallback callback) {
        new HTTPTask("GET", application.URL + "/world_names.json").execute(new HTTPCallback() {

            @Override
            public void success(String output) {
                ArrayList<WorldName> worldNameList = new ArrayList<WorldName>();
                try {
                    JSONArray jsonArray = new JSONArray(output);
                    int length = jsonArray.length();
                    for (int i=0; i<length; i++) {
                        WorldName worldName = new WorldName();
                        worldName.parseJSON(jsonArray.getJSONObject(i));
                        worldNameList.add(worldName);
                    }
                    callback.success(worldNameList);
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
