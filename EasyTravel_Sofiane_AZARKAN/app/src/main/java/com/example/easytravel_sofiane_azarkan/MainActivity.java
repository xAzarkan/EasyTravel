package com.example.easytravel_sofiane_azarkan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToWeatherActivity(View view) {
        startActivity(new Intent(this, WeatherActivity.class));
    }

    public void goToCalendarWidget(View view) {
        startActivity(new Intent(this, CalendarActivity.class));
    }

    public void goToFindADestination(View view){
        startActivity(new Intent(this, FindDestinationActivity.class));
    }
}