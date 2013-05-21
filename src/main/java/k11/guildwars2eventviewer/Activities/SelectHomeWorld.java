package k11.guildwars2eventviewer.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import k11.guildwars2eventviewer.Callbacks.WorldListCallback;
import k11.guildwars2eventviewer.DataClasses.WorldName;
import k11.guildwars2eventviewer.DataClasses.WorldName;
import k11.guildwars2eventviewer.EventViewerApplication;

import java.util.ArrayList;
import java.util.List;

public class SelectHomeWorld extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EventViewerApplication application = (EventViewerApplication) getApplication();

        ArrayAdapter<WorldName> adapter = new ArrayAdapter<WorldName>(SelectHomeWorld.this, android.R.layout.simple_list_item_1, android.R.id.text1, application.getWorldNames());
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        WorldName worldName = (WorldName) getListAdapter().getItem(position);
        final EventViewerApplication application = (EventViewerApplication) getApplication();
        application.setHomeWorld(worldName);
        finish();
    }
}
