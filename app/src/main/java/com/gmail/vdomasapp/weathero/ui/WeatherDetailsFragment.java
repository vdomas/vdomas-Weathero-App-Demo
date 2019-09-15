package com.gmail.vdomasapp.weathero.ui;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.vdomasapp.weathero.R;
import com.gmail.vdomasapp.weathero.adapter.GlideApp;
import com.gmail.vdomasapp.weathero.utils.LogErrors;
import com.gmail.vdomasapp.weathero.utils.WeatherIcons;
import com.gmail.vdomasapp.weathero.utils.WindDirection;

import static com.gmail.vdomasapp.weathero.utils.Constants.KEY_UNIT;
import static com.gmail.vdomasapp.weathero.utils.Constants.METRIC_UNIT;

public class WeatherDetailsFragment extends Fragment {

    private TextView tvWeatherDate;
    private TextView tvWeatherTemperature;
    private TextView tvWeatherDescription;
    private TextView tvMorningTemperature;
    private TextView tvEveningTemperature;
    private TextView tvNightTemperature;
    private TextView tvWeatherWindSpeed;
    private TextView tvWeatherWindDegree;
    private ImageView imgWeatherIcon;
    private Resources res;
    private SharedPreferences sharedPref;
    private Context fragmentContext;
    private String weatherUnit;

    public WeatherDetailsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getResources();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(fragmentContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWeatherDate = (TextView)view.findViewById(R.id.fragment_weather_details_tv_date);
        tvWeatherTemperature = (TextView)view.findViewById(R.id.fragment_weather_details_tv_day_temperature);
        tvWeatherDescription = (TextView)view.findViewById(R.id.fragment_weather_details_tv_weather_description);
        tvMorningTemperature = (TextView)view.findViewById(R.id.fragment_weather_details_tv_morning_temperature);
        tvEveningTemperature = (TextView)view.findViewById(R.id.fragment_weather_details_tv_evening_temperature);
        tvNightTemperature = (TextView)view.findViewById(R.id.fragment_weather_details_tv_night_temperature);
        tvWeatherWindSpeed = (TextView)view.findViewById(R.id.fragment_weather_details_tv_wind_speed);
        tvWeatherWindDegree = (TextView)view.findViewById(R.id.fragment_weather_details_tv_wind_degree);
        imgWeatherIcon = (ImageView)view.findViewById(R.id.fragment_weather_details_img_weather_icon);

        weatherUnit = sharedPref.getString(KEY_UNIT, METRIC_UNIT);

        displayWeatherDetailsOnUi();
    }

    private void displayWeatherDetailsOnUi() {
        try {
            tvWeatherDate.setText(WeatherDetailsFragmentArgs.fromBundle(getArguments()).getDate());
            tvWeatherTemperature.setText(res.getString(R.string.current_temperature, WeatherDetailsFragmentArgs.fromBundle(getArguments()).getTemperature()));
            tvWeatherDescription.setText(WeatherDetailsFragmentArgs.fromBundle(getArguments()).getDescription());
            tvMorningTemperature.setText(res.getString(R.string.current_temperature, WeatherDetailsFragmentArgs.fromBundle(getArguments()).getMorningTemp()));
            tvEveningTemperature.setText(res.getString(R.string.current_temperature, WeatherDetailsFragmentArgs.fromBundle(getArguments()).getEveningTemp()));
            tvNightTemperature.setText(res.getString(R.string.current_temperature, WeatherDetailsFragmentArgs.fromBundle(getArguments()).getNightTemp()));

            if (weatherUnit.equals(METRIC_UNIT)){
                tvWeatherWindSpeed.setText(res.getString(R.string.wind_speed_meters_per_second,WeatherDetailsFragmentArgs.fromBundle(getArguments()).getWindSpeed()));
            } else {
                tvWeatherWindSpeed.setText(res.getString(R.string.wind_speed_miles_per_hour,WeatherDetailsFragmentArgs.fromBundle(getArguments()).getWindSpeed()));
            }

            tvWeatherWindDegree.setText(WindDirection.getWeatherDirection(WeatherDetailsFragmentArgs.fromBundle(getArguments()).getWindDegree()));

            Object weatherDetailsIcon = WeatherIcons.weatherIconCollection().get(WeatherDetailsFragmentArgs.fromBundle(getArguments()).getIcon());

            GlideApp.with(fragmentContext)
                    .load((Integer) weatherDetailsIcon)
                    .into(imgWeatherIcon);

        } catch (Resources.NotFoundException | NullPointerException e){
            LogErrors.logException(e);
        }
    }
}
