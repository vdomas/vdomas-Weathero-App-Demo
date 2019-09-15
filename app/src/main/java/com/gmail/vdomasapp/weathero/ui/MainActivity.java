package com.gmail.vdomasapp.weathero.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import com.gmail.vdomasapp.weathero.R;
import com.gmail.vdomasapp.weathero.services.LocationUpdatesService;
import com.gmail.vdomasapp.weathero.ui.dialog.GpsDialogFragment;
import com.gmail.vdomasapp.weathero.utils.NetworkState;

import static com.gmail.vdomasapp.weathero.utils.Constants.GPS_DIALOG_FRAGMENT;
import static com.gmail.vdomasapp.weathero.utils.Constants.GPS_ENABLED_FROM_DIALOG;
import static com.gmail.vdomasapp.weathero.utils.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private boolean enabledFromGpsDialog;

    // A reference to the service used to get location updates.
    private LocationUpdatesService locationService = null;

    // Tracks the bound state of the service.
    private boolean mBound = false;

    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            locationService = binder.getService();
            mBound = true;

            if (locationService != null) {
                locationService.requestLocationUpdates();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            locationService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // new activity created; force its orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        enabledFromGpsDialog = sharedPreferences.getBoolean(GPS_ENABLED_FROM_DIALOG, true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Make different permissions check before launching app
        checkPermissions();

        //Check network state, if there is no internet do not bind a service otherwise it may lead to memory leak
        if (NetworkState.isConnected(this)) {
            // Bind to the service. If the service is in foreground mode, this signals to the service
            // that since this activity is in the foreground, the service can exit foreground mode.
            bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }


    @Override
    protected void onPause() {
        if (locationService != null) {
            locationService.removeLocationUpdates();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            // Request for location permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted.
                locationService.requestLocationUpdates();
            }
        }
    }

    //Check for gps and location permission on the device
    private void checkPermissions() {
        //Check if network is enabled
        if (NetworkState.isConnected(this)) {
            //Check if GPS location is enabled
            if (!isGpsEnabled()) {
                //Check if GPS was enabled from the Dialog
                if (enabledFromGpsDialog){
                    buildGpsDialog();
                } else {
                    Toast.makeText(this, getString(R.string.gps_signal_is_disabled), Toast.LENGTH_SHORT).show();
                }
            } else {
                checkLocationPermission();
            }
        } else {
            Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkLocationPermission() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Permission is missing and must be requested.
                requestLocationPermission();
            }
        }
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Request the permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    //Check for Gps permission if it is enabled
    private boolean isGpsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    //Build GPS dialog fragment to enable GPS preferences
    private void buildGpsDialog() {
        GpsDialogFragment gpsDialogFragment = GpsDialogFragment.newInstance();
        gpsDialogFragment.show(getSupportFragmentManager(), GPS_DIALOG_FRAGMENT);
    }
}
