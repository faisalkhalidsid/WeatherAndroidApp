package faisalkhalid.weatherappandroid.models;

public class WeatherForecast {

    public String code;
    public String date;
    public String day;
    public String temp;
    public String high;
    public String low;
    public String text;
    public WeatherUnit unit;

    public WeatherForecast (String code,String date, String day, String high, String low, String text,WeatherUnit unit) {
        this.code = code;
        this.date = date;
        this.day = day;
        this.high = high;
        this.low = low;
        this.text = text;
        this.unit = unit;
        this.temp = String.valueOf((Integer.parseInt(high) + Integer.parseInt(low))/2);


    }

  public   static String  inCelcius(String temp) {
        return String.valueOf( (int)(( Double.parseDouble(temp) - 31) * (0.5556)));


    }

}
