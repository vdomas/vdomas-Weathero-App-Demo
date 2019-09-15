package com.gmail.vdomasapp.weathero.utils;

public final class WindDirection {

    private static String windDirectionText;

    public static String getWeatherDirection(int windDirection) {
        if (windDirection <= 89){
            windDirectionText = "NE";
        }
        else if (windDirection == 90){
            windDirectionText = "East";
        }
        else if (between(windDirection,91,179)){
            windDirectionText = "SE";
        }
        else if (windDirection == 180){
            windDirectionText = "South";
        }
        else if (between(windDirection,181,269)){
            windDirectionText = "SW";
        }
        else if (windDirection == 270){
            windDirectionText = "West";
        }
        else if (between(windDirection,271,359)){
            windDirectionText = "NW";
        }
        else if (windDirection == 360){
            windDirectionText = "North";
        }
        return windDirectionText;
    }

    private static boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        return i >= minValueInclusive && i <= maxValueInclusive;
    }
}
