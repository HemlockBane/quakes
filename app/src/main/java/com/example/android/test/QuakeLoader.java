package com.example.android.test;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.test.models.Quakes;
import com.example.android.test.utils.QueryUtils;

import java.util.List;

public class QuakeLoader extends AsyncTaskLoader<List<Quakes>> {
    private static final String LOG_TAG = QuakeLoader.class.getSimpleName();
    private String url;

    public QuakeLoader(Context context, String url){
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Quakes> loadInBackground() {
        List<Quakes> quakesList = QueryUtils.fetchQuakeData(url);
        return quakesList;
    }
}
