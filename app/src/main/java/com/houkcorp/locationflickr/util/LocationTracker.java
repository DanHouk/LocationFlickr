package com.houkcorp.locationflickr.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.houkcorp.locationflickr.Constants;
import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.model.LocationHolder;

public class LocationTracker implements LocationListener {
    private Activity mContext;
    private Location mLocation;
    private LocationHolder mLocationHolder;
    private LocationManager mLocationManager;

    public LocationTracker(Activity context) {
        mContext = context;
    }



    public LocationHolder getLocation() {
        mLocationHolder = new LocationHolder();
        mLocationManager =
                (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabeled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkEnabeled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

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
            int temp = 0;

            //int fineCheckInt =
            int coarseCheckInt = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION);
            int fineCheckInt = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);

            if (coarseCheckInt != PackageManager.PERMISSION_GRANTED || fineCheckInt != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(mContext, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
                callLocation();
            } else {
                callLocation();
            }
        }

        if(networkEnabeled && !gpsEnabeled) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    Constants.SERVER_TIME_OUT, 10, this);
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(mLocation != null) {
                mLocationHolder.location = mLocation;
                mLocationHolder.latitude = mLocation.getLatitude();
                mLocationHolder.longitude = mLocation.getLongitude();
            }
        }

        return mLocationHolder;
    }

    public void callLocation() {
        int coarseCheckInt = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int fineCheckInt = ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION);

        if (fineCheckInt == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    Constants.SERVER_TIME_OUT, 10, this);
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(mLocation != null) {
                mLocationHolder.location = mLocation;
                mLocationHolder.latitude = mLocation.getLatitude();
                mLocationHolder.longitude = mLocation.getLongitude();
            }
        }
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