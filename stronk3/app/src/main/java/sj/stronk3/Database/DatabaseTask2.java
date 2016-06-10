package sj.stronk3.Database;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Corsair on 10-6-2016.
 */
public class DatabaseTask2 extends AsyncTask<String, Void, String> {

    public static final String READ_QUERY_URL = "http://sjacobs.me/stronk/readquery.php";
    private String query;
    private QueryTypes queryType;

    public DatabaseTask2(String query, QueryTypes queryType) {
        this.query = query;
        this.queryType = queryType;
    }

    public DatabaseTask2(Queries query) {
        this.query = query.getQuery();
        this.queryType = query.getQueryType();
    }

    @Override
    protected String doInBackground(String... params) {
        String response = null;
        switch(queryType) {
            case READ:
                response = readQuery(query);
                break;
            case EXECUTE:
                //TODO
                break;
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("app", result);
    }

    // TODO: parse result as json
    private String readQuery(String query) {
        String response = "";
        try {
            URL url = new URL(READ_QUERY_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bufferedWriter.write("query="+query);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();
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

            //response += "\nDone!";
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
