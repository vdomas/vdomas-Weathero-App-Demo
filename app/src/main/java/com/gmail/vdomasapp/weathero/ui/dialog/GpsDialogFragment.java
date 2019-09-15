package com.gmail.vdomasapp.weathero.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.gmail.vdomasapp.weathero.R;
import com.gmail.vdomasapp.weathero.utils.SharedPrefHelper;

import static com.gmail.vdomasapp.weathero.utils.Constants.GPS_ENABLED_FROM_DIALOG;
import static com.gmail.vdomasapp.weathero.utils.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;


public class GpsDialogFragment extends DialogFragment {

    private Context dialogFragmentContext;

    public GpsDialogFragment() {
    }

    public static GpsDialogFragment newInstance() {
        return new GpsDialogFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogFragmentContext = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(dialogFragmentContext)
                // set Dialog Title
                .setTitle(getString(R.string.gps_dialog_title))
                // Set Dialog Message
                .setMessage(getString(R.string.gps_dialog_message))

                // positive button
                .setPositiveButton(getString(R.string.gps_dialog_confirmation_button), (dialog, which) -> {
                    Intent enableGpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                    SharedPrefHelper.saveSharedPrefBooleanValues(dialogFragmentContext, GPS_ENABLED_FROM_DIALOG,true);
                })
                // negative button
                .setNegativeButton(getString(R.string.gps_dialog_cancel_button), (dialog, which) -> {
                    dialog.dismiss();
                    SharedPrefHelper.saveSharedPrefBooleanValues(dialogFragmentContext, GPS_ENABLED_FROM_DIALOG,false);
                })
                .create();
    }

}
