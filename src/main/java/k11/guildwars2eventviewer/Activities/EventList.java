package k11.guildwars2eventviewer.Activities;

import android.R;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final EventViewerApplication application = (EventViewerApplication) getApplication();
        final Bundle bundle = getIntent().getExtras();

        final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
        Event.readList(application,application.getHomeWorld().getId(),bundle.getInt("id"),null,new EventListCallback() {
            @Override
            public void success(ArrayList<Event> eventList) {
                ArrayList<Event> displayEventList = new ArrayList<Event>();
                Integer size = eventList.size();
                for (int i=0;i<size;i++) {
                    if (eventList.get(i).getState().equals(Event.ACTIVE)) {
                        displayEventList.add(eventList.get(i));
                    }
                }
                EventAdapter adapter = new EventAdapter(EventList.this,android.R.layout.simple_list_item_1,displayEventList);
                setListAdapter(adapter);
                dialog.dismiss();

                //EventAdapter adapter = new EventAdapter(getApplication(),android.R.layout.simple_list_item_1,output);
                //setListAdapter(adapter);
            }

            @Override
            public void failure(Exception e) {
                Toast.makeText(application, "CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    private class AdapterTuple {
        public String text;

        AdapterTuple(String text) {
            this.text = text;
        }
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
            return view;
        }
    }
}
