package k11.guildwars2eventviewer.Activities;

import android.R;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import k11.guildwars2eventviewer.Callbacks.EventListCallback;
import k11.guildwars2eventviewer.DataClasses.Event;
import k11.guildwars2eventviewer.EventViewerApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bay on 5/20/13.
 */
public class EventList extends ListActivity {
    private Integer mapID;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EventViewerApplication application = (EventViewerApplication) getApplication();
        final Bundle bundle = getIntent().getExtras();

        mapID = bundle.getInt("mapID");

        //final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
        refresh();

        this.mHandler = new Handler();
        this.mHandler.postDelayed(m_Runnable,application.getRefresh() * 1000);
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {
            refresh();
            final EventViewerApplication application = (EventViewerApplication) getApplication();
            mHandler.postDelayed(m_Runnable, application.getRefresh() * 1000);
        }

    };

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        //MapName mapName = (MapName) getListAdapter().getItem(position);
        //String url = ((Event)getListAdapter().getItem(position)).getEventName()
        final EventViewerApplication application = (EventViewerApplication) getApplication();
        Event event = (Event) getListAdapter().getItem(position);
        String eventName = event.getEventName(application).getName();
        eventName = eventName.replace(" ","+");
        if (eventName.endsWith(".")) {
            eventName = eventName.substring(0,eventName.length() - 1);
        }
        String url = "http://wiki.guildwars2.com/index.php?title=Special%3ASearch&search=" + eventName;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private class EventAdapter extends ArrayAdapter<Event> {
        private int textViewResourceId;
        private LayoutInflater layoutInflater;
        public EventAdapter(Context context, int textViewResourceId,List<Event> objects) {
            super(context, textViewResourceId, objects);
            textViewResourceId = this.textViewResourceId;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (convertView == null) {
                view = layoutInflater.inflate(R.layout.simple_list_item_1, null);
            }

            final EventViewerApplication application = (EventViewerApplication) getApplication();

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position).getEventName(application).getName());
            if (getItem(position).getState().equals(Event.PREPARATION)) {
                textView.setBackgroundColor(Color.argb(64,255,255,128));
            }
            else {
                //textView.setBackgroundColor(getResources().getColor(R.color.background_light));
                textView.setBackgroundColor(Color.TRANSPARENT);
            }
            return view;
        }
    }

    private void refresh() {
        final EventViewerApplication application = (EventViewerApplication) getApplication();

        Event.readList(application,application.getHomeWorld().getId(),mapID,null,new EventListCallback() {
            @Override
            public void success(ArrayList<Event> eventList) {
                ArrayList<Event> displayEventList = new ArrayList<Event>();
                Integer size = eventList.size();
                for (int i=0;i<size;i++) {
                    if (eventList.get(i).getState().equals(Event.ACTIVE) || eventList.get(i).getState().equals(Event.PREPARATION)) {
                        displayEventList.add(eventList.get(i));
                    }
                }
                EventAdapter adapter = new EventAdapter(EventList.this,android.R.layout.simple_list_item_1,displayEventList);
                setListAdapter(adapter);
                //dialog.dismiss();
            }

            @Override
            public void failure(Exception e) {
                Toast.makeText(application, "CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                //dialog.dismiss();
            }
        });

    }
}
