package com.gmail.vdomasapp.weathero.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;

import com.gmail.vdomasapp.weathero.R;

public class CustomCheckBoxPreference extends CheckBoxPreference {

    public CustomCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomCheckBoxPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomCheckBoxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCheckBoxPreference(Context context) {
        super(context);
    }


    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        TextView titleView = (TextView)holder.findViewById(android.R.id.title);
        TextView summaryView = (TextView)holder.findViewById(android.R.id.summary);
        CheckBox checkBoxView = (CheckBox)holder.findViewById(android.R.id.checkbox);

        titleView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        summaryView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
        checkBoxView.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorWhite)));
    }
}
