package faisalkhalid.weatherappandroid.networking;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import faisalkhalid.weatherappandroid.models.LocationCoordinate2D;
import faisalkhalid.weatherappandroid.models.Weather;
import faisalkhalid.weatherappandroid.models.WeatherCurrent;
import faisalkhalid.weatherappandroid.models.WeatherForecast;
import faisalkhalid.weatherappandroid.models.WeatherLocation;
import faisalkhalid.weatherappandroid.models.WeatherUnit;

public class WeatherClient {

    String baseURL = "https://query.yahooapis.com/v1/public/yql?";

    public WeatherClientCallback mCallback;

    public void getWeatherByLatLong(Context context, LocationCoordinate2D cordinate) throws Exception {


         String url = baseURL+"q=select+%2A+from+weather.forecast+where+woeid+in+%28SELECT+woeid+FROM+geo.places+WHERE+text%3D%22%28"+cordinate.latitude+"%2C"+cordinate.longitude+"%29%22%29%0D%0A&format=json";


        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest postRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                       // Log.d("Response", response);

                        //setProducPrice(response,"323");
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject query =  jsonObject.getJSONObject("query");
                            if (query != null) {
                               // if let count = query["count"] as? Int {
                                  int count = query.getInt("count");
                                  if(count == 1){
                                      JSONObject results =  query.getJSONObject("results");
                                      JSONObject channel = results.getJSONObject("channel");
                                      JSONObject location = channel.getJSONObject("location");

                                      WeatherLocation weatherLocation = new WeatherLocation();
                                      weatherLocation.city = location.getString("city");
                                      weatherLocation.country = location.getString("country");
                                      weatherLocation.region = location.getString("region");


                                      JSONObject item = channel.getJSONObject("item");
                                      JSONObject condition = item.getJSONObject("condition");

                                      WeatherCurrent current = new WeatherCurrent(condition.getString("code"),condition.getString("date"),condition.getString("temp"),condition.getString("text"), WeatherUnit.FAHRENHEIT);

                                      JSONArray forecastList = item.getJSONArray("forecast");


                                      ArrayList<WeatherForecast> forecasts = new ArrayList<>();


                                      for (int i=0; i < forecastList.length(); i++) {
                                         JSONObject forecast =  forecastList.getJSONObject(i);
                                          Log.d("Response",forecast.getString("date"));
                                          forecasts.add(new WeatherForecast(forecast.getString("code"),forecast.getString("date"),forecast.getString("day"),forecast.getString("high"),forecast.getString("low"),forecast.getString("text"),WeatherUnit.FAHRENHEIT));




                                      }


                                      Weather weather = new Weather(weatherLocation,current,forecasts);

                                      mCallback.onWeatherCallback(weather);









                                  }

                                }

                            /*
                            if(result){

                                String description = jsonObject.getString("DESCRIPTION");
                                String descriptionar = jsonObject.getString("DESCRIPTIONAR");


                                String price = jsonObject.getString("PRICE");





                            }
                            else {


                            }
*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {


        };
        queue.add(postRequest);

    }

    public interface WeatherClientCallback{
        void onWeatherCallback(Weather weather);
    }


}
