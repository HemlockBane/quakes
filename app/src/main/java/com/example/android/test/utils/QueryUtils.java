package com.example.android.test.utils;

import android.util.Log;

import com.example.android.test.models.Quakes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    public static final String LOG_TAG = QueryUtils.class.getSimpleName();


    public static List<Quakes> fetchQuakeData(String requestUrl) {
        URL url = createURL(requestUrl);
        Log.e(LOG_TAG, "Please work");

        String jsonResult = null;

        try {
            jsonResult = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<Quakes> listOfQuakes = parseJsonResponse(jsonResult);


        return listOfQuakes;

    }


    private static URL createURL(String stringURL) {
        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = getJsonFromStream(inputStream);

                //Log JSON response
                Log.e(LOG_TAG, "The JSON response is :" + jsonResponse);
            } else {
                Log.e(LOG_TAG, "The JSON response is :" + jsonResponse);

            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the quakes", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }


    private static String getJsonFromStream(InputStream inputStream) throws IOException {

        StringBuilder builder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
        }
        Log.e(LOG_TAG, "The string from the buffered reader is : " + builder.toString());
        return builder.toString();
    }

    public static List<Quakes> parseJsonResponse(String jsonResponse) {
        // Create an empty ArrayList that we can start adding earthquakes to
        List<Quakes> quakesList = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(jsonResponse);
            JSONArray featuresArray = rootObject.getJSONArray("features");

            for (int i = 0; i < featuresArray.length(); i++) {

                JSONObject featuresChildObject = featuresArray.optJSONObject(i);
                JSONObject properties = featuresChildObject.optJSONObject("properties");

                double magnitude = properties.optDouble("mag");
                String place = properties.optString("place");
                long time = properties.optLong("time");
                String url = properties.optString("url");

                Quakes quakes = new Quakes(magnitude, place, time, url);
                quakesList.add(quakes);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing the quake JSON response");
        }

        return quakesList;
    }

}
