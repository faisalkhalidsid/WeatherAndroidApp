package faisalkhalid.weatherappandroid.models;

public class WeatherCurrent {


    public    String code;
    public   String date;
    public   String temperature;
    public   String text;
    public   WeatherUnit unit;

   public WeatherCurrent(String code,String date,String temperature,String text,WeatherUnit unit) {
        this.code = code;
        this.date = date;
        this.temperature = temperature;
        this.text = text;
        this.unit = unit;
        }
    }





