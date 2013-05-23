package k11.guildwars2eventviewer.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import k11.guildwars2eventviewer.EventViewerApplication;
import k11.guildwars2eventviewer.R;

/**
 * Created by bay on 5/22/13.
 */
public class Options extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final EventViewerApplication application = (EventViewerApplication) getApplication();
        EditText refreshInterval = (EditText)findViewById(R.id.refresh);
        refreshInterval.setText(application.getRefresh().toString());
    }

    @Override
    protected void onPause() {
        super.onPause();

        final EventViewerApplication application = (EventViewerApplication) getApplication();
        EditText refreshInterval = (EditText)findViewById(R.id.refresh);
        application.setRefresh(Integer.valueOf(refreshInterval.getText().toString()));
    }
}
