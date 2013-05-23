package k11.guildwars2eventviewer.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import k11.guildwars2eventviewer.Callbacks.EventNameListCallback;
import k11.guildwars2eventviewer.Callbacks.MapNameListCallback;
import k11.guildwars2eventviewer.Callbacks.WorldListCallback;
import k11.guildwars2eventviewer.DataClasses.EventName;
import k11.guildwars2eventviewer.DataClasses.MapName;
import k11.guildwars2eventviewer.DataClasses.WorldName;
import k11.guildwars2eventviewer.EventViewerApplication;
import k11.guildwars2eventviewer.R;

import java.util.ArrayList;

public class MainMenu extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        findViewById(R.id.selectzone).setOnClickListener(this);
        findViewById(R.id.majorevents).setOnClickListener(this);
        findViewById(R.id.selecthomeworld).setOnClickListener(this);
        findViewById(R.id.options).setOnClickListener(this);

        final EventViewerApplication application = (EventViewerApplication) getApplication();

        final ProgressDialog dialog = ProgressDialog.show(this, "", "Loading. Please wait...", true);
        EventName.readList(application,new EventNameListCallback() {
            @Override
            public void success(ArrayList<EventName> output) {
                application.setEventNames(output);
                MapName.readList(application, new MapNameListCallback() {
                    @Override
                    public void success(ArrayList<MapName> output) {
                        application.setMapNames(output);
                        WorldName.readList(application,new WorldListCallback() {
                            @Override
                            public void success(ArrayList<WorldName> output) {
                                application.setWorldNames(output);
                                dialog.dismiss();
                            }

                            @Override
                            public void failure(Exception e) {
                                Toast.makeText(application, "CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                                dialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void failure(Exception e) {
                        Toast.makeText(application, "CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void failure(Exception e) {
                Toast.makeText(application, "CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        final EventViewerApplication application = (EventViewerApplication) getApplication();
        TextView homeWorldView = (TextView)findViewById(R.id.homeworld_name);
        homeWorldView.setText(application.getHomeWorld().getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.selectzone:
                intent = new Intent(this, SelectZone.class);
                startActivity(intent);
                break;
            case R.id.majorevents:
                intent = new Intent(this, MajorEvents.class);
                startActivity(intent);
                break;
            case R.id.selecthomeworld:
                intent = new Intent(this, SelectHomeWorld.class);
                startActivity(intent);
                break;
            case R.id.options:
                intent = new Intent(this, Options.class);
                startActivity(intent);
                break;
        }
    }
    
}
