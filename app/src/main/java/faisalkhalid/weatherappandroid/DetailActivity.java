package faisalkhalid.weatherappandroid;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import faisalkhalid.weatherappandroid.models.Weather;
import faisalkhalid.weatherappandroid.models.WeatherForecast;
import faisalkhalid.weatherappandroid.models.WeatherLocation;

public class DetailActivity extends AppCompatActivity {

    public static WeatherForecast forecast;
    public static WeatherLocation location;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPref = this.getSharedPreferences("Preference",MODE_PRIVATE);

        ImageView img = (ImageView) findViewById(R.id.detailimage);
        TextView temp = (TextView) findViewById(R.id.detailtemp);
        TextView selectedUnit = (TextView) findViewById(R.id.detailselectedunit);
        TextView city = (TextView) findViewById(R.id.detaillocation);
        TextView text = (TextView) findViewById(R.id.detaildescription);


        Glide.with(this)
                .asBitmap()
                .load("http://l.yimg.com/a/i/us/we/52/"+forecast.code+".gif")
                .into(img);


        String unit = sharedPref.getString("unit","-1");
        if (unit.contentEquals("F")){
            temp.setText(forecast.temp);
            selectedUnit.setText("o F");
            text.setText(forecast.text+" with a high of "+forecast.high+".F and a low of "+forecast.low+".F");

        }
        else {
            temp.setText(WeatherForecast.inCelcius(forecast.temp));
            selectedUnit.setText("o C");
            text.setText(forecast.text+" with a high of "+WeatherForecast.inCelcius(forecast.high)+".C and a low of "+WeatherForecast.inCelcius(forecast.low)+".C");

        }


        city.setText(location.city+", "+location.country);



    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
