package sj.stronk3.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Corsair on 6-6-2016.
 */
public class DatabaseTask extends AsyncTask<String, Void, String> {
    private Context context;
    private PHPTask task;

    public DatabaseTask(Context context, PHPTask task) {
        this.context = context;
        this.task = task;
    }

    @Override
    protected String doInBackground(String... params) {
        String response = null;
        switch(task) {
            case INSERT_WEIGHT:
                response = doInternetAction(task, getData(task, params));
            break;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("app", result);
    }

    private String getData(PHPTask task, String...params) {
        String data = "";

        // The parameters that are expected.
        String[] requiredParams = task.getParams();
        Log.d("app", "parameterscount:" + requiredParams.length);

        for(String param : requiredParams) {
            Log.d("app", "Parameters:"+param);
        }

        try {
            for (int i = 0; i < task.count; i++) {
                data += URLEncoder.encode(requiredParams[i], "UTF-8") + "=" + URLEncoder.encode(params[i], "UTF-8");
                Log.d("app", "Line:"+URLEncoder.encode(requiredParams[i], "UTF-8") + "=" + URLEncoder.encode(params[i], "UTF-8"));
                if (i < task.count - 1) {
                    data += "&";
                }
            }
            Log.d("app", "NEWDATA:"+data);
        } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            Log.d("app", "getData error");
        }
        return data;
    }

    private String doInternetAction(PHPTask task, String data) {
        String response = "";
        Log.d("app", "DATA: " + data);
        try {
            URL url = new URL(task.getUrl());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String line = "";

            // Get php response
            while ((line = bufferedReader.readLine())!=null)
            {
                response += "\n" + line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            httpURLConnection.disconnect();

            response += "\nSuccess!";
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
