package com.example.android.test;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.test.models.Quakes;
import com.example.android.test.models.QuakesAdapter;
import com.example.android.test.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Quakes>> {
    private QuakesAdapter quakesAdapter;
    private ListView listView;
    private TextView emptyView;
    private ProgressBar progressBar;



    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int QUAKE_LOADER_ID = 1;
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query";

//    private static final String SAMPLE_JSON_RESPONSE = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1462295443000,\"url\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-01-31&minmag=6&limit=10\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.5.2\",\"limit\":10,\"offset\":1,\"count\":10},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":7.2,\"place\":\"88km N of Yelizovo, Russia\",\"time\":1454124312220,\"updated\":1460674294040,\"tz\":720,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004vvx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004vvx&format=geojson\",\"felt\":2,\"cdi\":3.4,\"mmi\":5.82,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":798,\"net\":\"us\",\"code\":\"20004vvx\",\"ids\":\",at00o1qxho,pt16030050,us20004vvx,gcmt20160130032510,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":0.958,\"rms\":1.19,\"gap\":17,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.2 - 88km N of Yelizovo, Russia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[158.5463,53.9776,177]},\"id\":\"us20004vvx\"},\n" +
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"94km SSE of Taron, Papua New Guinea\",\"time\":1453777820750,\"updated\":1460156775040,\"tz\":600,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us20004uks\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us20004uks&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":4.1,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":572,\"net\":\"us\",\"code\":\"20004uks\",\"ids\":\",us20004uks,gcmt20160126031023,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.537,\"rms\":0.74,\"gap\":25,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 94km SSE of Taron, Papua New Guinea\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[153.2454,-5.2952,26]},\"id\":\"us20004uks\"},\n" +
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.3,\"place\":\"50km NNE of Al Hoceima, Morocco\",\"time\":1453695722730,\"updated\":1460156773040,\"tz\":0,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gy9\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gy9&format=geojson\",\"felt\":117,\"cdi\":7.2,\"mmi\":5.28,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":695,\"net\":\"us\",\"code\":\"10004gy9\",\"ids\":\",us10004gy9,gcmt20160125042203,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.201,\"rms\":0.92,\"gap\":20,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.3 - 50km NNE of Al Hoceima, Morocco\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-3.6818,35.6493,12]},\"id\":\"us10004gy9\"},\n" +
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":7.;1,\"place\":\"86km E of Old Iliamna, Alaska\",\"time\":1453631430230,\"updated\":1460156770040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004gqp\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004gqp&format=geojson\",\"felt\":1816,\"cdi\":7.2,\"mmi\":6.6,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":1496,\"net\":\"us\",\"code\":\"10004gqp\",\"ids\":\",at00o1gd6r,us10004gqp,ak12496371,gcmt20160124103030,\",\"sources\":\",at,us,ak,gcmt,\",\"types\":\",cap,dyfi,finite-fault,general-link,general-text,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,trump-origin,\",\"nst\":null,\"dmin\":0.72,\"rms\":2.11,\"gap\":19,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 7.1 - 86km E of Old Iliamna, Alaska\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-153.4051,59.6363,129]},\"id\":\"us10004gqp\"},\n" +
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.6,\"place\":\"215km SW of Tomatlan, Mexico\",\"time\":1453399617650,\"updated\":1459963829040,\"tz\":-420,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004g4l\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004g4l&format=geojson\",\"felt\":11,\"cdi\":2.7,\"mmi\":3.92,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":673,\"net\":\"us\",\"code\":\"10004g4l\",\"ids\":\",at00o1bebo,pt16021050,us10004g4l,gcmt20160121180659,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":2.413,\"rms\":0.98,\"gap\":74,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.6 - 215km SW of Tomatlan, Mexico\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-106.9337,18.8239,10]},\"id\":\"us10004g4l\"},\n" +
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.7,\"place\":\"52km SE of Shizunai, Japan\",\"time\":1452741933640,\"updated\":1459304879040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebx\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebx&format=geojson\",\"felt\":51,\"cdi\":5.8,\"mmi\":6.45,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":720,\"net\":\"us\",\"code\":\"10004ebx\",\"ids\":\",us10004ebx,pt16014050,at00o0xauk,gcmt20160114032534,\",\"sources\":\",us,pt,at,gcmt,\",\"types\":\",associate,cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":0.281,\"rms\":0.98,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.7 - 52km SE of Shizunai, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[142.781,41.9723,46]},\"id\":\"us10004ebx\"},\n" +
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"12km WNW of Charagua, Bolivia\",\"time\":1452741928270,\"updated\":1459304879040,\"tz\":-240,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004ebw\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004ebw&format=geojson\",\"felt\":3,\"cdi\":2.2,\"mmi\":2.21,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":573,\"net\":\"us\",\"code\":\"10004ebw\",\"ids\":\",us10004ebw,gcmt20160114032528,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":5.492,\"rms\":1.04,\"gap\":16,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - 12km WNW of Charagua, Bolivia\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-63.3288,-19.7597,582.56]},\"id\":\"us10004ebw\"},\n" +
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.2,\"place\":\"74km NW of Rumoi, Japan\",\"time\":1452532083920,\"updated\":1459304875040,\"tz\":540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004djn\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004djn&format=geojson\",\"felt\":8,\"cdi\":3.4,\"mmi\":3.74,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":594,\"net\":\"us\",\"code\":\"10004djn\",\"ids\":\",us10004djn,gcmt20160111170803,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":1.139,\"rms\":0.96,\"gap\":33,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.2 - 74km NW of Rumoi, Japan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[141.0867,44.4761,238.81]},\"id\":\"us10004djn\"},\n" +
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6.5,\"place\":\"227km SE of Sarangani, Philippines\",\"time\":1452530285900,\"updated\":1459304874040,\"tz\":480,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004dj5\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004dj5&format=geojson\",\"felt\":1,\"cdi\":2.7,\"mmi\":7.5,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":1,\"sig\":650,\"net\":\"us\",\"code\":\"10004dj5\",\"ids\":\",at00o0srjp,pt16011050,us10004dj5,gcmt20160111163807,\",\"sources\":\",at,pt,us,gcmt,\",\"types\":\",cap,dyfi,geoserve,impact-link,impact-text,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,tectonic-summary,\",\"nst\":null,\"dmin\":3.144,\"rms\":0.72,\"gap\":22,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.5 - 227km SE of Sarangani, Philippines\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[126.8621,3.8965,13]},\"id\":\"us10004dj5\"},\n" +
//            "{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"Pacific-Antarctic Ridge\",\"time\":1451986454620,\"updated\":1459202978040,\"tz\":-540,\"url\":\"http://earthquake.usgs.gov/earthquakes/eventpage/us10004bgk\",\"detail\":\"http://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us10004bgk&format=geojson\",\"felt\":0,\"cdi\":1,\"mmi\":0,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"10004bgk\",\"ids\":\",us10004bgk,gcmt20160105093415,\",\"sources\":\",us,gcmt,\",\"types\":\",cap,dyfi,geoserve,losspager,moment-tensor,nearby-cities,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":30.75,\"rms\":0.67,\"gap\":71,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - Pacific-Antarctic Ridge\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-136.2603,-54.2906,10]},\"id\":\"us10004bgk\"}],\"bbox\":[-153.4051,-54.2906,10,158.5463,59.6363,582.56]}";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_list);

//        List<Quakes> quakesList = QueryUtils.parseJsonResponse(SAMPLE_JSON_RESPONSE);

        progressBar = findViewById(R.id.progress_bar);

        List<Quakes> quakesList = new ArrayList<>();

        listView = findViewById(R.id.list);

        quakesAdapter = new QuakesAdapter(this, quakesList);

        listView.setAdapter(quakesAdapter);

        emptyView = findViewById(R.id.empty_text);

        listView.setEmptyView(emptyView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                com.example.android.test.models.Quakes earthquake = quakesAdapter.getItem(position);

                Uri quakeUri = Uri.parse(earthquake.getUrl());

                Intent uriIntent = new Intent(Intent.ACTION_VIEW, quakeUri);

                startActivity(uriIntent);


            }
        });

//        QuakesAsyncTask task = new QuakesAsyncTask();
//        task.execute(USGS_REQUEST_URL);

        // Get a reference to the ConnectivityManager, to check network state
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Check network state
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        // If device is connected, initiate loaderManger.
        // Else, display informatory message.
        if (networkInfo != null && networkInfo.isConnected()){

            // Get a reference to the LoaderManager, in order to interact with the loader
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(QUAKE_LOADER_ID, null, this);

        }else {
            // Hide the progress bar
            progressBar.setVisibility(View.GONE);
            // Display informatory message
            emptyView.setText(R.string.no_internet);
        }



    }


    // Load USGS quake results in QuakeLoader
    @Override
    public Loader<List<Quakes>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String minimumMagnitude = sharedPreferences.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        Uri uri = Uri.parse(USGS_REQUEST_URL);

        Uri.Builder uriBuilder = uri.buildUpon();
        uriBuilder
                .appendQueryParameter("format", "geojson")
                .appendQueryParameter("limit", "10")
                .appendQueryParameter("minmag", "minMagnitude")
                .appendQueryParameter("orderby", "time");

        return new QuakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Quakes>> loader, List<Quakes> data) {

        // Remove progress bar
        progressBar.setVisibility(View.GONE);

        // Clear previously loaded quake data from the adapter
        quakesAdapter.clear();

        // If data is not null or empty, add it to the adapter
        // Else set empty textView to "No data"
        if (data != null && !data.isEmpty()) {
            quakesAdapter.addAll(data);
        }else{
            emptyView.setText(R.string.no_data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Quakes>> loader) {
        // Loader has been reset; clear loaded data
        quakesAdapter.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);

        return super.onOptionsItemSelected(item);
    }

    //    private class QuakesAsyncTask extends AsyncTask<String, Void, List<Quakes>> {
//
//        @Override
//        protected List<Quakes> doInBackground(String... params) {
//            Log.e(LOG_TAG, "The doInBackground method works");
//
//
//            // If url array is empty and the first url element in the array is null,
//            // return null
//            if (params.length < 1 || params[0] == null) {
//                return null;
//            }
//
//            List<Quakes> quakesList = QueryUtils.fetchQuakeData(params[0]);
//            Log.i(LOG_TAG, "quakesList: " + quakesList.toString());
//
//            return quakesList;
//        }
//
//        @Override
//        protected void onPostExecute(List<Quakes> data) {
//            Log.e(LOG_TAG, "The onPostExecute method works");
//
//            quakesAdapter.clear();
//
//            // If data is not null or empty, add it to the adapter
//            if (data != null && !data.isEmpty()) {
//                quakesAdapter.addAll(data);
//            }
//
//        }
//
//    }
}
