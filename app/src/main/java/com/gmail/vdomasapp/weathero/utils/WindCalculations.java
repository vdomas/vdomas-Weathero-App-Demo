package com.gmail.vdomasapp.weathero.utils;

public final class WindCalculations {

    //Get feeling temperature in metric unit
    public static double getFeelingTemperatureInMetricUnit(double weatherTemperatureInCelsius, double windSpeedInMs) {
        double weatherTemperatureInFahrenheit = (9.0/5.0) * weatherTemperatureInCelsius + 32;
        double windSpeedInMph = 2.23694 * windSpeedInMs;
        double windChillInFahrenheit = 35.74 + 0.6215 * weatherTemperatureInFahrenheit - 35.75 * Math.pow(windSpeedInMph, 0.16) + 0.4275 * weatherTemperatureInFahrenheit * Math.pow(windSpeedInMph, 0.16);
        return ((windChillInFahrenheit-32)/(9.0/5.0));
    }

    //Get feeling temperature in imperial unit
    public static double getFeelingTemperatureInImperialUnit(double weatherTemperatureInFahrenheit, double windSpeedInMph) {
        return 35.74 + 0.6215 * weatherTemperatureInFahrenheit - 35.75 * Math.pow(windSpeedInMph, 0.16) + 0.4275 * weatherTemperatureInFahrenheit * Math.pow(windSpeedInMph, 0.16);
    }

}
