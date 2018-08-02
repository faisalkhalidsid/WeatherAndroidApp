package faisalkhalid.weatherappandroid.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import faisalkhalid.weatherappandroid.DetailActivity;
import faisalkhalid.weatherappandroid.MainActivity;
import faisalkhalid.weatherappandroid.R;
import faisalkhalid.weatherappandroid.models.Weather;
import faisalkhalid.weatherappandroid.models.WeatherForecast;
import faisalkhalid.weatherappandroid.models.WeatherLocation;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by User on 2/12/2018.
 */

public class WeatherListAdaptor extends RecyclerView.Adapter<WeatherListAdaptor.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    //vars
    private ArrayList<WeatherForecast> forecast = new ArrayList<>();
    private Context mContext;

    private WeatherLocation location;
    ViewHolder selectedHolder;


    public WeatherListAdaptor(Context context, ArrayList<WeatherForecast> forecast, WeatherLocation location) {
        this.forecast = forecast;
        mContext = context;
        this.location = location;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitemvertical, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(mContext)
                .asBitmap()
                .load("http://l.yimg.com/a/i/us/we/52/"+forecast.get(position).code+".gif")
                .into(holder.image);


        SharedPreferences sharedPref = mContext.getSharedPreferences("Preference",MODE_PRIVATE);
        String unit = sharedPref.getString("unit","-1");
        if (unit.contentEquals("F")){
            holder.temp.setText(forecast.get(position).temp+".F");
        }
        else {
            holder.temp.setText(WeatherForecast.inCelcius(forecast.get(position).temp)+".C");

        }


        holder.day.setText(forecast.get(position).day+", "+forecast.get(position).date);
        holder.text.setText(forecast.get(position).text);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                DetailActivity.forecast = forecast.get(position);
                DetailActivity.location = location;
                i.setClass(mContext, DetailActivity.class);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return forecast.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView day;
        ImageView image;
        TextView text;
        TextView temp;
        ConstraintLayout layout;



        public ViewHolder(View itemView) {

            super(itemView);
            itemView.setAlpha(0.9f);


            day = (TextView) itemView.findViewById(R.id.weatherlist_day);
            image = (ImageView) itemView.findViewById(R.id.weatherlist_img);
            text = (TextView) itemView.findViewById(R.id.weatherlist_text);
            temp = (TextView) itemView.findViewById(R.id.weatherlist_temp);



        }



    }
}