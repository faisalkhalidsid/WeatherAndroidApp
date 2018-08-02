package faisalkhalid.weatherappandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import faisalkhalid.weatherappandroid.adapters.WeatherListAdaptor;
import faisalkhalid.weatherappandroid.models.LocationCoordinate2D;
import faisalkhalid.weatherappandroid.models.Weather;
import faisalkhalid.weatherappandroid.models.WeatherForecast;
import faisalkhalid.weatherappandroid.networking.WeatherClient;

public class MainActivity extends AppCompatActivity implements WeatherClient.WeatherClientCallback {


    RecyclerView weatherRecyclerView;

    TextView currentLocation;
    TextView currentTemp;
    ImageView currentImg;
    TextView selectedUnit;
    SharedPreferences sharedPref;
    BuildType build;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        build = BuildType.development;

        if(build == BuildType.development) {

            getSupportActionBar().setTitle("Weather App Development Build");
        }
        else if (build == BuildType.production){
            getSupportActionBar().setTitle("Weather App");

        }



        sharedPref = this.getSharedPreferences("Preference",MODE_PRIVATE);

        weatherRecyclerView = findViewById(R.id.weatherRecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        weatherRecyclerView.setLayoutManager(layoutManager);

         currentLocation = (TextView) findViewById(R.id.currentlocation);
         currentTemp = (TextView) findViewById(R.id.detailtemp);
         currentImg = (ImageView) findViewById(R.id.currentimage);
        selectedUnit = (TextView) findViewById(R.id.selectedUnit);

        final WeatherClient client = new WeatherClient();
        client.mCallback = this;
        final Context mContext = this.getApplicationContext();
       final LocationCoordinate2D coordinate = new LocationCoordinate2D(25.2048,55.2708);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                try {
                    client.getWeatherByLatLong(mContext,coordinate);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        },0,60000* 15); // it will update weather every 15 minutes






        String unit = sharedPref.getString("unit","-1");
        if (unit == "-1"){

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("unit","F");
            editor.commit();
        } else {

            Log.d("7874572",unit);
        }


        setUpNotification();


    }
void setUpNotification(){

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 22);
    calendar.set(Calendar.MINUTE, 00);
    calendar.set(Calendar.SECOND, 00);
    Intent intent1 = new Intent(MainActivity.this, NotificationReceiver.class);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
    intent1.setAction("MY_NOTIFICATION_MESSAGE");
    AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
    am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent i = new Intent();
         i.setClass(this,Settings.class);
         startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWeatherCallback(Weather weather) {

        WeatherListAdaptor adapter = new WeatherListAdaptor(this,weather.forecast,weather.location);
        weatherRecyclerView.setAdapter(adapter);

        Glide.with(this)
                .asBitmap()
                .load("http://l.yimg.com/a/i/us/we/52/"+weather.current.code+".gif")
                .into(currentImg);


        currentLocation.setText(weather.location.city+", "+weather.location.country);

        String unit = sharedPref.getString("unit","-1");
        SharedPreferences.Editor editor = sharedPref.edit();

        if (unit.contentEquals("F")){
            currentTemp.setText(weather.current.temperature);
            selectedUnit.setText("o F");
            editor.putString("notification","Expected "+weather.forecast.get(1).temp+".F Tomorrow");

        }
        else {
            currentTemp.setText(WeatherForecast.inCelcius(weather.current.temperature));
            selectedUnit.setText("o C");
            editor.putString("notification","Expected "+WeatherForecast.inCelcius(weather.forecast.get(1).temp)+".C Tomorrow");


        }


        editor.commit();


    }
}
