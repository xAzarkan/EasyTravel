package com.example.easytravel_sofiane_azarkan;

import android.support.annotation.NonNull;
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

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myStartDate;
    String startDate = "";

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

    public void saveDate(View view)
    { //sauvegarder la date dans un fichier txt pour ensuite afficher une notif
        if(startDate != "")
        {
            try
            {
                File myDateFile = new File(getFilesDir(), "dateOfVacation.txt");
                myDateFile.setWritable(true);
                myDateFile.createNewFile(); // if file already exists will do nothing

                FileWriter myFileWriter = new FileWriter(myDateFile); //j'ouvre le fichier.txt en écriture

                myFileWriter.write(startDate); //écriture dans le fichier

                myFileWriter.close();

                Toast.makeText(this, "The date was successfully saved!", Toast.LENGTH_SHORT).show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
        }

    }
}