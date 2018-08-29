package com.example.android.test.models;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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

        GradientDrawable magnitudeCircle = (GradientDrawable) magView.getBackground();


        // Get the magnitude colour using the magnitude
        int magnitudeColour = getMagnitudeColour(currentQuakes.getMagnitude());

        // Set the magnitude circle colour based on the magnitude
        magnitudeCircle.setColor(magnitudeColour);

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

    private String formatDecimal(double magnitude) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(magnitude);
    }

    private int getMagnitudeColour(double magnitude) {

        // Get the
        int magnitudeColourResourceID = 0;
        int singleDigitMagnitude = (int) Math.floor(magnitude);
        switch (singleDigitMagnitude) {
            case 0:
            case 1:
                magnitudeColourResourceID = R.color.magnitude1;
                break;
            case 2:
                magnitudeColourResourceID = R.color.magnitude2;
                break;
            case 3:
                magnitudeColourResourceID = R.color.magnitude3;
                break;
            case 4:
                magnitudeColourResourceID = R.color.magnitude4;
                break;
            case 5:
                magnitudeColourResourceID = R.color.magnitude5;
                break;
            case 6:
                magnitudeColourResourceID = R.color.magnitude6;
                break;
            case 7:
                magnitudeColourResourceID = R.color.magnitude7;
                break;
            case 8:
                magnitudeColourResourceID = R.color.magnitude8;
                break;
            case 9:
                magnitudeColourResourceID = R.color.magnitude9;
                break;
            default:
                magnitudeColourResourceID = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColourResourceID);


    }
}
