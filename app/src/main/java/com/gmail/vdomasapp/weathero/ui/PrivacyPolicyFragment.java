package com.gmail.vdomasapp.weathero.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.gmail.vdomasapp.weathero.R;

import static com.gmail.vdomasapp.weathero.utils.Constants.PRIVACY_POLICY_FILE_LOCATION;

public class PrivacyPolicyFragment extends Fragment {

    public static PrivacyPolicyFragment newInstance() {
        return new PrivacyPolicyFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_privacy_policy, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WebView webView = (WebView)view.findViewById(R.id.fragment_privacy_policy_webview);
        webView.loadUrl(PRIVACY_POLICY_FILE_LOCATION);
    }
}
