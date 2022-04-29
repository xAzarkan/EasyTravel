package com.example.easytravel_sofiane_azarkan.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easytravel_sofiane_azarkan.Common.Common;
import com.example.easytravel_sofiane_azarkan.Model.WeatherForecastResult;
import com.example.easytravel_sofiane_azarkan.Model.WeatherResult;
import com.example.easytravel_sofiane_azarkan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FindDestinationAdapter extends RecyclerView.Adapter<FindDestinationAdapter.MyViewHolder>{

    Context context;
    public WeatherForecastResult weatherForecastResult;
    double averageTemp = 0;
    String city = "";
    String icon = "";

    int size = 0;

    public static ArrayList<WeatherForecastResult> listWeatherForecastResult = new ArrayList<WeatherForecastResult>();

    public FindDestinationAdapter(Context context) {
        this.context = context;
        //this.weatherForecastResult = weatherForecastResult;
    }
/*
    public FindDestinationAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    } */

    public void addWeatherForecastResult(WeatherForecastResult wfr)
    {
        weatherForecastResult = wfr;
        //size++;
        for(int i = 0; i < weatherForecastResult.getList().size(); i++)
        {
            averageTemp = averageTemp + weatherForecastResult.getList().get(i).getMain().getTemp();
        }



        city = weatherForecastResult.getCity().getName();
        icon = weatherForecastResult.getList().get(0).getWeather().get(0).getIcon();
    }

    @NonNull
    @Override
    public FindDestinationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_city_weather, parent, false);
        return new FindDestinationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FindDestinationAdapter.MyViewHolder holder, int position) {
        //Load icon
        String description = "wow il fait beau";

        averageTemp = averageTemp/getItemCount();
        

        Picasso.get().load(new StringBuilder("http://openweathermap.org/img/wn/")
                .append(new StringBuilder(icon))
                .append("@2x.png").toString()).into(holder.img_weather);

        holder.txt_city_name.setText(new StringBuilder(city));

        holder.txt_description.setText(new StringBuilder(description));

        holder.txt_temperature.setText(new StringBuilder(String.valueOf(averageTemp)).append("°C"));

            //Toast.makeText(context, String.valueOf(averageTemp), Toast.LENGTH_SHORT).show();
            //averageTemp = averageTemp/getItemCount();
            //Toast.makeText(context, String.valueOf(averageTemp), Toast.LENGTH_SHORT).show();
/*
            Picasso.get().load(new StringBuilder("http://openweathermap.org/img/wn/")
                    .append(weatherForecastResult.getList().get(position).getWeather().get(0).getIcon())
                    .append("@2x.png").toString()).into(holder.img_weather);

            holder.txt_city_name.setText(new StringBuilder(weatherForecastResult.getCity().getName()));

            holder.txt_description.setText(new StringBuilder(weatherForecastResult.getList().get(position)
                    .getWeather().get(0).getDescription()));

            //holder.txt_temperature.setText(new StringBuilder(String.valueOf(weatherForecastResult.getList().get(position)
                  //  .getMain().getTemp())).append("°C"));

            holder.txt_temperature.setText(new StringBuilder(String.valueOf(averageTemp)));*/



    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.getList().size();
        //return size;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_city_name, txt_description, txt_temperature;
        ImageView img_weather;

        public MyViewHolder(View itemView){
            super(itemView);

            img_weather = (ImageView) itemView.findViewById(R.id.img_weather);
            txt_city_name = (TextView) itemView.findViewById(R.id.txt_city_name);
            txt_description = (TextView) itemView.findViewById(R.id.txt_description);
            txt_temperature = (TextView) itemView.findViewById(R.id.txt_temperature);

        }

    }
}
