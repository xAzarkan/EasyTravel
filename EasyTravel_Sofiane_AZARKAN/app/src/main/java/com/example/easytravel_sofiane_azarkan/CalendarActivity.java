package com.example.easytravel_sofiane_azarkan;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myStartDate;
    String startDate = "";

    static final String SHARED_PREF = "shared";
    static final String TEXT = "text";

    Calendar calendar;

    private final String CHANNEL_ID = "personnal notifications";
    private final int NOTIFICATION_ID = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        createNotificationChannel();

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myStartDate = (TextView) findViewById(R.id.myStartDate);

        /* Récupère la date enregistrée */
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        startDate = sharedPreferences.getString(TEXT, "");
        myStartDate.setText("First day of vacation : " + startDate);

        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                startDate = day + "/" + (month+1) + "/" + year;
                myStartDate.setText("First day of vacation : " +  startDate);
            }
        });

    }

    public void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Personal Notifications";
            String description = "Personal notifications description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void saveDate(View view)
    { //sauvegarder la date dans un fichier txt pour ensuite afficher une notif

        if(startDate != "" && calendar != null)
        {
            Toast.makeText(this, "The date was successfully saved!", Toast.LENGTH_SHORT).show();

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(TEXT, startDate);
            editor.apply();

            Intent landingIntent = new Intent(this, NotificationReceiver.class);
            landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent landingPendingIntent = PendingIntent.getBroadcast(this, 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), landingPendingIntent);

            update();
        }
        else
        {
            Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
        }

    }

    public void update()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        startDate = sharedPreferences.getString(TEXT, "");
        myStartDate.setText("First day of vacation : " + startDate);
    }
}