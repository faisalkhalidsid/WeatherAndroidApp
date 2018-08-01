package faisalkhalid.weatherappandroid;

import android.app.ActionBar;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        ImageView img = (ImageView) findViewById(R.id.detailimage);
        TextView temp = (TextView) findViewById(R.id.detailtemp);
        TextView selectedUnit = (TextView) findViewById(R.id.detailselectedunit);
        TextView city = (TextView) findViewById(R.id.detaillocation);
        TextView text = (TextView) findViewById(R.id.detaildescription);


        Glide.with(this)
                .asBitmap()
                .load("http://l.yimg.com/a/i/us/we/52/"+forecast.code+".gif")
                .into(img);

        city.setText(location.city+", "+location.country);
        temp.setText(forecast.temp);
        text.setText(forecast.text+" with a high of "+forecast.high+" and a low of "+forecast.low);


    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }
}
