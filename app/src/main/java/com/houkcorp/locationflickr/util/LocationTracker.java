package com.houkcorp.locationflickr.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.houkcorp.locationflickr.Constants;
import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.model.LocationHolder;

public class LocationTracker implements LocationListener {
    private Context mContext;

    public LocationTracker(Context context) {
        mContext = context;
    }

    public LocationHolder getLocation() {
        Location location;
        LocationHolder locationHolder = new LocationHolder();
        LocationManager locationManager =
                (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabeled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabeled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!gpsEnabeled && !networkEnabeled) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext)
                    .setTitle(R.string.location_error)
                    .setMessage(R.string.gps_and_network_disabled)
                    .setCancelable(false)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            return null;
        }

        if(gpsEnabeled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    Constants.SERVER_TIME_OUT, 10, this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null) {
                locationHolder.location = location;
                locationHolder.latitude = location.getLatitude();
                locationHolder.longitude = location.getLongitude();
            }
        }

        if(networkEnabeled && !gpsEnabeled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    Constants.SERVER_TIME_OUT, 10, this);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location != null) {
                locationHolder.location = location;
                locationHolder.latitude = location.getLatitude();
                locationHolder.longitude = location.getLongitude();
            }
        }

        return locationHolder;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}