package faisalkhalid.weatherappandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final Switch unitSwitch = (Switch)  findViewById(R.id.settings_switch);

        final SharedPreferences sharedPref = this.getSharedPreferences("Preference",MODE_PRIVATE);
        final String unit = sharedPref.getString("unit","-1");

        Log.d("7361525",unit);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (unit.contentEquals("C"))
                    unitSwitch.setChecked(true);

                }
            });



        unitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences.Editor editor = sharedPref.edit();

                if (isChecked){
                    editor.putString("unit","C");
                }
                else {
                    editor.putString("unit","F");
                }

                editor.commit();


            }

        });


    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }



}
