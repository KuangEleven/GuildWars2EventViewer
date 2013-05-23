package k11.guildwars2eventviewer.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import k11.guildwars2eventviewer.DataClasses.MapName;
import k11.guildwars2eventviewer.DataClasses.WorldName;
import k11.guildwars2eventviewer.EventViewerApplication;

/**
 * Created by bay on 5/20/13.
 */
public class SelectZone extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EventViewerApplication application = (EventViewerApplication) getApplication();

        ArrayAdapter<MapName> adapter = new ArrayAdapter<MapName>(this, android.R.layout.simple_list_item_1, android.R.id.text1, application.getMapNames());
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {

        MapName mapName = (MapName) getListAdapter().getItem(position);
        Intent i = new Intent(this, EventList.class);
        Bundle bundle = new Bundle();
        bundle.putInt("mapID", mapName.getId());
        i.putExtras(bundle);
        startActivity(i);
    }
}
