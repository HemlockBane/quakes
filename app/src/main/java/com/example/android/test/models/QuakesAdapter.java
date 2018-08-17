package com.example.android.test.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.test.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuakesAdapter extends ArrayAdapter<Quakes> {
    private final String TAG = QuakesAdapter.class.getSimpleName();

    private final String DEFAULT_SEPARATOR = "of";
    private final String ALTERNATE_SEPARATOR = "Near the";

    public QuakesAdapter(Context context, List<Quakes> quakesList) {
        super(context, 0, quakesList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.queue_view, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        Quakes currentQuakes = getItem(position);



        // Find the TextView with the given ID magnitude_view
        TextView magView = listItemView.findViewById(R.id.magnitude_view);
        // Display the magnitude of the current earthquake in that TextView

        double plainMagnitude = currentQuakes.getMagnitude();
        // Format the magnitude to 1 d.p
        String magnitude = formatDecimal(plainMagnitude);
        magView.setText(magnitude);

        String[] locationParts;
        String plainLocation = currentQuakes.getLocation();
        String locationOffset;
        String locationPrimary;


        if (plainLocation.contains(DEFAULT_SEPARATOR)) {
            locationParts = plainLocation.split(DEFAULT_SEPARATOR);
            locationOffset = locationParts[0] + DEFAULT_SEPARATOR;
            locationPrimary = locationParts[1];

            Log.e(TAG, "offset: " + locationOffset);
            Log.e(TAG, "primary: " + locationPrimary);



        } else {
            locationOffset = ALTERNATE_SEPARATOR;
            locationPrimary = plainLocation;

        }

        // Find the TextView with the given ID magnitude_view
        TextView locationOffsetTextView = listItemView.findViewById(R.id.location_offset_view);
        // Display the magnitude of the current earthquake in that TextView
        locationOffsetTextView.setText(locationOffset);

        // Find the TextView with the given ID magnitude_view
        TextView locationPrimaryTextView = listItemView.findViewById(R.id.location_primary_view);
        // Display the magnitude of the current earthquake in that TextView
        locationPrimaryTextView.setText(locationPrimary);


        Date dateObject = new Date(currentQuakes.getTime());


        // Find the TextView with the given ID magnitude_view
        TextView dateTextView = listItemView.findViewById(R.id.date_view);
        // Display the magnitude of the current earthquake in that TextView
        String date = formatDate(dateObject);
        dateTextView.setText(date);

        TextView timeTextView = listItemView.findViewById(R.id.time_view);

        String time = formatTime(dateObject);

        timeTextView.setText(time);

        // Return the listItemView that is now showing the appropriate data
        return listItemView;
    }

    private String formatDate(Date dateObject) {
        //DateFormat dateFormat = new DateFormat("dd mm yyyy");
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(dateObject);

    }

    private String formatTime(Date dateObject) {
        //SimpleDateFormat timeFormat = new SimpleDateFormat("h: mm a");
        DateFormat timeFormat = DateFormat.getTimeInstance();
        return timeFormat.format(dateObject);
    }

    private String formatDecimal(double magnitude){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }
}
