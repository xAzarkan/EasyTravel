package com.example.easytravel_sofiane_azarkan;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myStartDate;
    TextView myEndDate;
    String startDate, endDate;

    int cpt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myStartDate = (TextView) findViewById(R.id.myStartDate);
        myEndDate = (TextView) findViewById(R.id.myEndDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                startDate = "";
                endDate = "";

                if(cpt == 0)
                {
                    startDate = day + "/" + (month+1) + "/" + year;
                    myStartDate.setText("Start : " + startDate);
                    cpt += 1;
                }
                else
                {
                    endDate = day + "/" + (month+1) + "/" + year;
                    myEndDate.setText("End : " + endDate);
                    cpt = 0;
                }
            }
        });
    }
}