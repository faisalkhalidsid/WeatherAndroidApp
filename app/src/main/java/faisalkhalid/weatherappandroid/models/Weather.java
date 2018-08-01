package faisalkhalid.weatherappandroid.models;

import java.util.ArrayList;

public class Weather {
    public WeatherLocation location;
    public WeatherCurrent current;
    public ArrayList<WeatherForecast> forecast;

    public Weather(WeatherLocation location, WeatherCurrent current, ArrayList<WeatherForecast> forecast) {
        this.location = location;
        this.current = current;
        this.forecast = forecast;
    }
}