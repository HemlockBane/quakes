package com.example.android.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.test.models.Quakes;
import com.example.android.test.models.QuakesAdapter;
import com.example.android.test.utils.QueryUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.queue_list);



        List<Quakes> quakesArrayList = QueryUtils.extractEarthquakes();
//        quakesArrayList.add(new Quakes("5.0", "Lagos", "24 January, 2018"));
//        quakesArrayList.add(new Quakes("5.0", "Lagos", "24 January, 2018"));
//        quakesArrayList.add(new Quakes("5.0", "Lagos", "24 January, 2018"));
//        quakesArrayList.add(new Quakes("5.0", "Lagos", "24 January, 2018"));
//        quakesArrayList.add(new Quakes("5.0", "Lagos", "24 January, 2018"));


        QuakesAdapter quakesAdapter = new QuakesAdapter(this,quakesArrayList);

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(quakesAdapter);
    }
}
