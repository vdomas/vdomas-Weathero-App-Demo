package com.gmail.vdomasapp.weathero.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceViewHolder;

import com.gmail.vdomasapp.weathero.R;

public class CustomListPreference extends ListPreference {

    public CustomListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListPreference(Context context) {
        super(context);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        TextView titleView = (TextView)holder.findViewById(android.R.id.title);
        TextView summaryView = (TextView)holder.findViewById(android.R.id.summary);

        titleView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        summaryView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
    }


}
