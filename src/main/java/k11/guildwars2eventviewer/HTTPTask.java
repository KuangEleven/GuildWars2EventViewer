package k11.guildwars2eventviewer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;
import k11.guildwars2eventviewer.Callbacks.HTTPCallback;

import javax.net.ssl.HttpsURLConnection;

public class HTTPTask extends AsyncTask<HTTPCallback, Void, Integer> { //Callback is executed in UI thread
    private String input;
    private String output;
    private String method;
    private String urlInput;
    private Exception exception;
    private HTTPCallback callback;

    public HTTPTask(String method, String url) {
        this.method = method;
        this.urlInput = url;
        input = null;
        output = null;
        exception = null;
    }
    public void setInput(String input) {
        this.input = input;
    }

    protected Integer doInBackground(HTTPCallback... callback) { //Always returns 0
        this.callback = callback[0];
        //Open HTTP Connection
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlInput);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            Log.d("HTTPurl",urlInput);

            if (input != null) {
                Log.d("HTTPinput",input);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setFixedLengthStreamingMode(input.getBytes().length);
                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                out.write(input.getBytes());
                out.flush();
            }

            if ((urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) || (urlConnection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) || (urlConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED)) {
                InputStream content = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                output = builder.toString();
            }
            else {
                exception = new Exception("HTTP Response Code " + urlConnection.getResponseCode());
            }

        }
        catch (Exception exception) {
            this.exception = exception;
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return 0;
    }

    protected void onPostExecute(Integer i) {
        if (exception != null) {
            callback.failure(exception);
        }
        else if (output != null) {
            callback.success(output);
        }
        else {
            callback.success(null);
        }
    }

}
