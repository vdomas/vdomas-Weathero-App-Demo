# Weathero app
A simple application to display live weather forecast from open weather API.


![](new_demo.gif)

**App Features**

  - Weather forecast for the current day and upcoming 7 days.
  - Wind speed and direction.
  - Feeling temperature.
  - Temperature based on GPS location.
  - Temperature for different parts of the day: morning, evening and night.
  - Temperature units: Celsius or Fahrenheit.



**App Packages**

  - adapter - contains recyclerview adapter and glide module.
  - model - contains data classes.
  - network - contains the api classes to make api calls to open weather API.
  - services - contains a class for location service to receive coordinates.
  - ui - contains classes to display Activity, Dialog Fragment and Fragment.
  - utils - contains helper classes for the project.
  - views - contains custom views.
  


**App Specs**

  - Minimum SDK 21.
  - Android Architecture Components (Navigation Component, ConstraintLayout).
  - RxJava2 for Retrofit 2 adapter.
  - Retrofit 2 for API integration.
  - Gson for serialisation.
  - Okhhtp3 for implementing network logging interceptor.
  - Glide for image loading.
  - Firebase for analytics.
  - Crashlytics for logging crash reports.
  - Google play service location for getting coordinates from gps.
  - Proguard rules for shrinking resources and obfuscating the code.
