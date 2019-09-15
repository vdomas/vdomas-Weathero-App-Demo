package com.gmail.vdomasapp.weathero.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.vdomasapp.weathero.R;
import com.gmail.vdomasapp.weathero.adapter.GlideApp;
import com.gmail.vdomasapp.weathero.adapter.RecyclerViewAdapter;
import com.gmail.vdomasapp.weathero.model.currentweather.CurrentWeather;
import com.gmail.vdomasapp.weathero.model.dailyweather.DailyWeatherInfo;
import com.gmail.vdomasapp.weathero.model.locationaddress.HereGeoCoder;
import com.gmail.vdomasapp.weathero.network.ApiService;
import com.gmail.vdomasapp.weathero.network.RetrofitClient;
import com.gmail.vdomasapp.weathero.services.LocationUpdatesService;
import com.gmail.vdomasapp.weathero.utils.LogErrors;
import com.gmail.vdomasapp.weathero.utils.NetworkState;
import com.gmail.vdomasapp.weathero.utils.SharedPrefHelper;
import com.gmail.vdomasapp.weathero.utils.WeatherIcons;
import com.gmail.vdomasapp.weathero.utils.WindCalculations;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.gmail.vdomasapp.weathero.utils.Constants.AFTER_CHANGES_UNIT_VALUE;
import static com.gmail.vdomasapp.weathero.utils.Constants.GEO_CODER_DATA;
import static com.gmail.vdomasapp.weathero.utils.Constants.HERE_APP_CODE;
import static com.gmail.vdomasapp.weathero.utils.Constants.HERE_APP_ID;
import static com.gmail.vdomasapp.weathero.utils.Constants.HERE_GEO_CODER_URL;
import static com.gmail.vdomasapp.weathero.utils.Constants.OPEN_WEATHER_API_KEY;
import static com.gmail.vdomasapp.weathero.utils.Constants.BEFORE_CHANGES_UNIT_VALUE;
import static com.gmail.vdomasapp.weathero.utils.Constants.BROADCAST_LATITUDE;
import static com.gmail.vdomasapp.weathero.utils.Constants.BROADCAST_LONGITUDE;
import static com.gmail.vdomasapp.weathero.utils.Constants.CURRENT_WEATHER;
import static com.gmail.vdomasapp.weathero.utils.Constants.CURRENT_WEATHER_DATA;
import static com.gmail.vdomasapp.weathero.utils.Constants.KEY_UNIT;
import static com.gmail.vdomasapp.weathero.utils.Constants.LANGUAGE_EN;
import static com.gmail.vdomasapp.weathero.utils.Constants.METRIC_UNIT;
import static com.gmail.vdomasapp.weathero.utils.Constants.OPEN_WEATHER_URL;
import static com.gmail.vdomasapp.weathero.utils.Constants.DAILY_WEATHER_LIST;
import static com.gmail.vdomasapp.weathero.utils.Constants.RETRIEVE_AREAS;

public class CurrentWeatherFragment extends Fragment implements RecyclerViewAdapter.OnCardViewListener {

    private CompositeDisposable disposable = new CompositeDisposable();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SharedPreferences sharedPreferences;
    private Context fragmentContext;
    private int predefinedUnitValue;
    private int postUnitValue;
    private ApiService openWeatherApiService;
    private ApiService hereGeoCoderApiService;
    private Gson gson = new Gson();
    private Resources res;
    private String weatherUnit;
    private TextView tvCity;
    private TextView tvTemperature;
    private TextView tvWindSpeed;
    private TextView tvFeelingTemperature;
    private ImageView imgSettingsIcon;
    private ImageView imgCurrentWeatherIcon;

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MyReceiver myReceiver = new MyReceiver();

    public CurrentWeatherFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(fragmentContext);
        openWeatherApiService = RetrofitClient.getRetrofit(OPEN_WEATHER_URL).create(ApiService.class);
        hereGeoCoderApiService = RetrofitClient.getRetrofit(HERE_GEO_CODER_URL).create(ApiService.class);

        res = getResources();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        imgSettingsIcon = (ImageView) view.findViewById(R.id.img_settings_icon);
        tvCity = (TextView) view.findViewById(R.id.tv_city);
        tvTemperature = (TextView) view.findViewById(R.id.tv_temperature);
        tvWindSpeed = (TextView) view.findViewById(R.id.tv_wind_speed);
        tvFeelingTemperature = (TextView) view.findViewById(R.id.tv_feeling_temperature);
        imgCurrentWeatherIcon = (ImageView) view.findViewById(R.id.img_current_weather_icon);

        imgSettingsIcon.setOnClickListener(this::openSettingsFragment);
        weatherUnit = sharedPreferences.getString(KEY_UNIT, METRIC_UNIT);
    }


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(fragmentContext).registerReceiver(myReceiver, new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
        loadCurrentWeatherFromSharedPref();
        loadWeatherListFromSharedPref();
        loadCityNameFromSharedPref();
        getWeatherUnitValues();
        compareWeatherUnitValues();
        checkFragmentStateValues();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(fragmentContext).unregisterReceiver(myReceiver);
        saveWeatherUnitValues();
        saveFragmentStateValueOnPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        disposable.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveFragmentStateValueOnDestroy();
    }

    @Override
    public void onItemClick(View child, int childPosition, DailyWeatherInfo dailyWeatherInfo) {
        passDataToWeatherDetailsFragment(childPosition, dailyWeatherInfo);
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
            if (location != null) {

                try {
                    String locLatitude = String.valueOf(location.getLatitude());
                    String locLongitude = String.valueOf(location.getLongitude());

                    makeHttpConnection(Double.valueOf(locLatitude), Double.valueOf(locLongitude), weatherUnit);

                    SharedPrefHelper.saveSharedPrefStringValues(context, BROADCAST_LATITUDE, locLatitude);
                    SharedPrefHelper.saveSharedPrefStringValues(context, BROADCAST_LONGITUDE, locLongitude);
                } catch (NumberFormatException | NullPointerException e) {
                    LogErrors.logException(e);
                }
            }
        }
    }

    private void checkFragmentStateValues() {
        int fragmentValue = sharedPreferences.getInt(CURRENT_WEATHER, 0);
        if (fragmentValue == 0) {
            initialHttpRequest();
        }
    }

    private void initialHttpRequest() {
        String broadcastLat = sharedPreferences.getString(BROADCAST_LATITUDE, null);
        String broadcastLon = sharedPreferences.getString(BROADCAST_LONGITUDE, null);

        //Check if strings contain value of coordinates
        if (broadcastLat != null && broadcastLon != null) {

            //Check network state, if there is no internet do not make a call
            if (NetworkState.isConnected(fragmentContext)) {
                makeHttpConnection(Double.valueOf(broadcastLat), Double.valueOf(broadcastLon), weatherUnit);
            }
        }
    }

    private void saveFragmentStateValueOnPause() {
        SharedPrefHelper.saveSharedPrefIntValues(fragmentContext, CURRENT_WEATHER, 1);
    }

    private void saveFragmentStateValueOnDestroy() {
        SharedPrefHelper.saveSharedPrefIntValues(fragmentContext, CURRENT_WEATHER, 0);
    }

    //Make http request from 3 different api services
    private void makeHttpConnection(double latitude, double longitude, String unit) {

        //Combining latitude and longitude into one string of coordinates for here.com api
        String coordinates = latitude + "," + longitude;

        disposable.add(hereGeoCoderApiService.getCityName(HERE_APP_ID, HERE_APP_CODE, RETRIEVE_AREAS, coordinates)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayCityNameOnUi, LogErrors::logThrowable)
        );
        disposable.add(openWeatherApiService.getCurrentWeather(latitude, longitude, LANGUAGE_EN, OPEN_WEATHER_API_KEY, unit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayCurrentWeatherOnUi, LogErrors::logThrowable)
        );
        disposable.add(openWeatherApiService.getDailyWeather(latitude, longitude, LANGUAGE_EN, OPEN_WEATHER_API_KEY, unit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dailyWeatherData -> {
                    List<DailyWeatherInfo> dailyWeatherInfoList = dailyWeatherData.getDailyWeatherInfoList();
                    displayDailyWeatherOnRecyclerView(dailyWeatherInfoList);
                }, LogErrors::logThrowable)
        );
    }

    //Display city name on UI
    private void displayCityNameOnUi(HereGeoCoder geoCoder) {
        if (geoCoder != null) {
            try {
                String cityDistrictTitle = geoCoder.getResponse().getView().get(0).getResult().get(0).getLocation().getAddress().getDistrict();
                String cityTitle = geoCoder.getResponse().getView().get(0).getResult().get(0).getLocation().getAddress().getCity();
                String cityCounty = geoCoder.getResponse().getView().get(0).getResult().get(0).getLocation().getAddress().getCounty();

                //display city name based on availability in api data
                if (cityDistrictTitle != null) {
                    if (cityDistrictTitle.length() <= 3) {
                        tvCity.setText(cityTitle);
                    } else {
                        tvCity.setText(cityDistrictTitle);
                    }
                } else if (cityTitle != null) {
                    tvCity.setText(cityTitle);
                } else {
                    tvCity.setText(cityCounty);
                }

                saveCityNameToSharedPref(geoCoder);
            } catch (NullPointerException e) {
                LogErrors.logException(e);
            }
        }
    }

    //Pass data to recycler view
    private void displayDailyWeatherOnRecyclerView(List<DailyWeatherInfo> dailyWeatherInfoList) {
        if (dailyWeatherInfoList != null) {
            try {
                LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                recyclerViewAdapter = new RecyclerViewAdapter(fragmentContext, dailyWeatherInfoList, this);
                recyclerView.setAdapter(recyclerViewAdapter);
                saveWeatherListToSharedPref(dailyWeatherInfoList);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                LogErrors.logException(e);
            }
        }
    }

    //Display data on ui
    private void displayCurrentWeatherOnUi(CurrentWeather currentWeatherData) {
        if (currentWeatherData.getName() != null) {
            try {
                int currentWeatherTemperature = currentWeatherData.getCurrentWeatherInfo().getFormattedTemperature();
                double currentWeatherWindSpeed = currentWeatherData.getCurrentWeatherWind().getSpeed();
                double currentFeelingTemperatureInMetrics = WindCalculations.getFeelingTemperatureInMetricUnit(currentWeatherData.getCurrentWeatherInfo().getTemp(), currentWeatherWindSpeed);
                double currentFeelingTemperatureInImperial = WindCalculations.getFeelingTemperatureInImperialUnit(currentWeatherData.getCurrentWeatherInfo().getTemp(), currentWeatherWindSpeed);
                Object currentWeatherIcon = WeatherIcons.weatherIconCollection().get(currentWeatherData.getCurrentWeatherIconList().get(0).getIcon());

                tvTemperature.setText((res.getString(R.string.current_temperature, currentWeatherTemperature)));

                GlideApp.with(fragmentContext)
                        .load((Integer) currentWeatherIcon)
                        .into(imgCurrentWeatherIcon);

                if (weatherUnit.equals(METRIC_UNIT)) {
                    tvWindSpeed.setText((res.getString(R.string.wind_speed_meters_per_second, (int) Math.round(currentWeatherWindSpeed))));
                    tvFeelingTemperature.setText((res.getString(R.string.feeling_temperature, (int) Math.round(currentFeelingTemperatureInMetrics))));
                } else {
                    tvWindSpeed.setText((res.getString(R.string.wind_speed_miles_per_hour, (int) Math.round(currentWeatherWindSpeed))));
                    tvFeelingTemperature.setText((res.getString(R.string.feeling_temperature, (int) Math.round(currentFeelingTemperatureInImperial))));
                }
                saveCurrentWeatherToSharedPref(currentWeatherData);
            } catch (NullPointerException | IndexOutOfBoundsException e) {
                LogErrors.logException(e);
            }
        }
    }

    private void passDataToWeatherDetailsFragment(int childPosition, DailyWeatherInfo dailyWeatherInfo) {

        CurrentWeatherFragmentDirections.ActionCurrentWeatherFragmentToWeatherDetailsFragment directions = CurrentWeatherFragmentDirections.actionCurrentWeatherFragmentToWeatherDetailsFragment();

        directions.setDate(dailyWeatherInfo.getFormattedDay());
        directions.setTemperature(dailyWeatherInfo.getDailyWeatherTemperature().getDayInt());
        directions.setIcon(dailyWeatherInfo.getDailyWeatherIconList().get(0).getIcon());
        directions.setDescription(dailyWeatherInfo.getDailyWeatherIconList().get(0).getDescription());
        directions.setMorningTemp(dailyWeatherInfo.getDailyWeatherTemperature().getMornInt());
        directions.setEveningTemp(dailyWeatherInfo.getDailyWeatherTemperature().getEveInt());
        directions.setNightTemp(dailyWeatherInfo.getDailyWeatherTemperature().getNightInt());
        directions.setWindSpeed(dailyWeatherInfo.getSpeedInt());
        directions.setWindDegree(dailyWeatherInfo.getDegInt());

        Navigation.findNavController(recyclerView).navigate(directions);
    }

    private void saveCityNameToSharedPref(HereGeoCoder geoCoder) {
        String geoCoderAsString = gson.toJson(geoCoder);
        SharedPrefHelper.saveSharedPrefStringValues(fragmentContext, GEO_CODER_DATA, geoCoderAsString);
    }

    private void loadCityNameFromSharedPref() {
        String json = sharedPreferences.getString(GEO_CODER_DATA, null);
        Type type = new TypeToken<HereGeoCoder>() {
        }.getType();
        HereGeoCoder geoCoder = gson.fromJson(json, type);

        if (geoCoder != null) {
            try {
                displayCityNameOnUi(geoCoder);
            } catch (NullPointerException e) {
                LogErrors.logException(e);
            }
        }
    }

    private void saveCurrentWeatherToSharedPref(CurrentWeather currentWeatherData) {
        String currentWeatherDataAsString = gson.toJson(currentWeatherData);
        SharedPrefHelper.saveSharedPrefStringValues(fragmentContext, CURRENT_WEATHER_DATA, currentWeatherDataAsString);
    }

    private void loadCurrentWeatherFromSharedPref() {
        String json = sharedPreferences.getString(CURRENT_WEATHER_DATA, null);
        Type type = new TypeToken<CurrentWeather>() {
        }.getType();
        CurrentWeather currentWeather = gson.fromJson(json, type);

        if (currentWeather != null) {
            try {
                displayCurrentWeatherOnUi(currentWeather);
            } catch (NullPointerException e) {
                LogErrors.logException(e);
            }
        }
    }

    private void saveWeatherListToSharedPref(List<DailyWeatherInfo> dailyWeatherInfoList) {
        String weatherDataList = gson.toJson(dailyWeatherInfoList);
        SharedPrefHelper.saveSharedPrefStringValues(fragmentContext, DAILY_WEATHER_LIST, weatherDataList);
    }

    private void loadWeatherListFromSharedPref() {
        String json = sharedPreferences.getString(DAILY_WEATHER_LIST, null);
        Type type = new TypeToken<List<DailyWeatherInfo>>() {
        }.getType();
        List<DailyWeatherInfo> dailyWeatherInfoList = gson.fromJson(json, type);

        if (dailyWeatherInfoList != null) {
            try {
                displayDailyWeatherOnRecyclerView(dailyWeatherInfoList);
            } catch (NullPointerException e) {
                LogErrors.logException(e);
            }
        }
    }

    private void getWeatherUnitValues() {
        predefinedUnitValue = sharedPreferences.getInt(BEFORE_CHANGES_UNIT_VALUE, 0);
        postUnitValue = sharedPreferences.getInt(AFTER_CHANGES_UNIT_VALUE, 0);
    }

    private void compareWeatherUnitValues() {
        if (predefinedUnitValue != postUnitValue) {
            initialHttpRequest();
        }
    }

    private void saveWeatherUnitValues() {
        if (predefinedUnitValue == 0) {
            SharedPrefHelper.saveSharedPrefIntValues(fragmentContext, AFTER_CHANGES_UNIT_VALUE, 0);
        } else {
            SharedPrefHelper.saveSharedPrefIntValues(fragmentContext, AFTER_CHANGES_UNIT_VALUE, 1);
        }
    }

    private void openSettingsFragment(View settingsView) {
        Navigation.findNavController(settingsView).navigate(R.id.action_currentWeatherFragment_to_settingsFragment);
    }
}
