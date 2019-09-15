package com.gmail.vdomasapp.weathero.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.gmail.vdomasapp.weathero.BuildConfig;
import com.gmail.vdomasapp.weathero.R;
import com.gmail.vdomasapp.weathero.utils.LogErrors;
import com.gmail.vdomasapp.weathero.utils.SharedPrefHelper;

import static com.gmail.vdomasapp.weathero.utils.Constants.BEFORE_CHANGES_UNIT_VALUE;
import static com.gmail.vdomasapp.weathero.utils.Constants.BUILD_VERSION;
import static com.gmail.vdomasapp.weathero.utils.Constants.CRASH_REPORT_CHECK_BOX;
import static com.gmail.vdomasapp.weathero.utils.Constants.EMAIL_CHOOSER;
import static com.gmail.vdomasapp.weathero.utils.Constants.FEEDBACK_PREFERENCE;
import static com.gmail.vdomasapp.weathero.utils.Constants.IMPERIAL_UNIT;
import static com.gmail.vdomasapp.weathero.utils.Constants.KEY_UNIT;
import static com.gmail.vdomasapp.weathero.utils.Constants.MAIL_TO;
import static com.gmail.vdomasapp.weathero.utils.Constants.METRIC_UNIT;
import static com.gmail.vdomasapp.weathero.utils.Constants.MY_EMAIL;
import static com.gmail.vdomasapp.weathero.utils.Constants.POLICY_PREFERENCE;

public class SettingsFragment extends PreferenceFragmentCompat {

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private Context fragmentContext;
    private ListPreference unitListPref;
    private Preference buildVersionPref;
    private CheckBoxPreference checkBoxPref;
    private Preference policyPref;
    private Preference feedbackPref;
    private View settingFragmentView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentContext = context;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        unitListPref = (ListPreference) findPreference(KEY_UNIT);
        buildVersionPref = (Preference)findPreference(BUILD_VERSION);
        checkBoxPref = (CheckBoxPreference)findPreference(CRASH_REPORT_CHECK_BOX);
        policyPref = (Preference)findPreference(POLICY_PREFERENCE);
        feedbackPref = (Preference)findPreference(FEEDBACK_PREFERENCE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundColor(ContextCompat.getColor(fragmentContext,R.color.colorDarkGrey));
        settingFragmentView = view;

        listeningForListPreference();
        listenForCheckBoxPreference();
        openPrivacyPolicyDocument();
        sendUserFeedback();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        setListPrefTitle();
        setBuildVersion();
        setCheckMarkForCheckBox();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    private void sendUserFeedback() {
        feedbackPref.setOnPreferenceClickListener(preference -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(MAIL_TO, MY_EMAIL, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.application_feedback));
            try {
                if (emailIntent.resolveActivity(fragmentContext.getPackageManager()) != null){
                    startActivity(Intent.createChooser(emailIntent, EMAIL_CHOOSER));
                }
            } catch (NullPointerException e){
                LogErrors.logException(e);
            }

            return true;
        });
    }

    private void openPrivacyPolicyDocument() {
        policyPref.setOnPreferenceClickListener(preference -> {
            if (settingFragmentView != null){
                Navigation.findNavController(settingFragmentView).navigate(R.id.action_settingsFragment_to_privacyPolicyFragment);
            }
            return true;
        });
    }

    private void listenForCheckBoxPreference() {
        checkBoxPref.setOnPreferenceClickListener(preference -> {
            Toast.makeText(fragmentContext, getString(R.string.changes_require_app_restart), Toast.LENGTH_SHORT).show();
            setCheckMarkForCheckBox();
            return true;
        });
    }

    private void setCheckMarkForCheckBox() {
        if (checkBoxPref.isChecked()){
            checkBoxPref.setSummary(getString(R.string.active));
        } else {
            checkBoxPref.setSummary(getString(R.string.disabled));
        }
    }

    private void listeningForListPreference() {
        preferenceChangeListener = (sharedPreferences, key) -> {
            if (key.equals(KEY_UNIT)){
                setListPrefTitle();
            }
        };
    }

    private void setListPrefTitle() {
        if (unitListPref.getValue().equals(METRIC_UNIT)){
            unitListPref.setSummary(getString(R.string.celsius));
            SharedPrefHelper.saveSharedPrefIntValues(fragmentContext, BEFORE_CHANGES_UNIT_VALUE, 0);
        }
        if (unitListPref.getValue().equals(IMPERIAL_UNIT)){
            unitListPref.setSummary(getString(R.string.fahrenheit));
            SharedPrefHelper.saveSharedPrefIntValues(fragmentContext, BEFORE_CHANGES_UNIT_VALUE, 1);
        }
    }

    private void setBuildVersion() {
        buildVersionPref.setSummary(BuildConfig.VERSION_NAME);
    }
}
