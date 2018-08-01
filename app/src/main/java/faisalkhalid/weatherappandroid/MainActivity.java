package faisalkhalid.weatherappandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import faisalkhalid.weatherappandroid.adapters.WeatherListAdaptor;
import faisalkhalid.weatherappandroid.models.LocationCoordinate2D;
import faisalkhalid.weatherappandroid.models.Weather;
import faisalkhalid.weatherappandroid.networking.WeatherClient;

public class MainActivity extends AppCompatActivity implements WeatherClient.WeatherClientCallback {


    RecyclerView weatherRecyclerView;

    TextView currentLocation;
    TextView currentTemp;
    ImageView currentImg;
    TextView unit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        weatherRecyclerView = findViewById(R.id.weatherRecycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        weatherRecyclerView.setLayoutManager(layoutManager);

         currentLocation = (TextView) findViewById(R.id.currentlocation);
         currentTemp = (TextView) findViewById(R.id.detailtemp);
         currentImg = (ImageView) findViewById(R.id.currentimage);
         unit = (TextView) findViewById(R.id.selectedUnit);



        WeatherClient client = new WeatherClient();
        client.mCallback = this;
//25.2048° N, 55.2708° E
        LocationCoordinate2D coordinate = new LocationCoordinate2D(25.2048,55.2708);
        try {
            client.getWeatherByLatLong(this,coordinate);
        } catch (Exception e) {
            e.printStackTrace();
        }


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
        currentTemp.setText(weather.current.temperature);


    }
}
