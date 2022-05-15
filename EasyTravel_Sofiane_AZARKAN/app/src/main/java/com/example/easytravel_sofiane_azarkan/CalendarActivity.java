package com.example.easytravel_sofiane_azarkan;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myStartDate;
    String startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myStartDate = (TextView) findViewById(R.id.myStartDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                startDate = "";

                startDate = day + "/" + (month+1) + "/" + year;
                myStartDate.setText("Start : " + startDate);

            }
        });
    }

    public void saveDate()
    { //sauvegarder la date dans un fichier txt pour ensuite afficher une notif

    }
}