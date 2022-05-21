package com.example.easytravel_sofiane_azarkan.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easytravel_sofiane_azarkan.Model.WeatherResult;
import com.example.easytravel_sofiane_azarkan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularCitiesAdapter extends RecyclerView.Adapter<PopularCitiesAdapter.MyViewHolder>{
    Context context;
    WeatherResult weatherResult;
    public static ArrayList<WeatherResult> listWeatherResult;

    public PopularCitiesAdapter(Context context)
    {
        this.context = context;
        //this.weatherResult = weatherResult;
    }

    public PopularCitiesAdapter(Context context, ArrayList<WeatherResult> listWeatherResult)
    {
        this.context = context;
        this.listWeatherResult = listWeatherResult;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_city_weather, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Picasso.get().load(new StringBuilder("http://openweathermap.org/img/wn/")
                .append(listWeatherResult.get(position).getWeather().get(0).getIcon())
                .append("@2x.png").toString()).into(holder.img_weather);

        holder.txt_city_name.setText(new StringBuilder(listWeatherResult.get(position).getName()));

        holder.txt_description.setText(new StringBuilder(listWeatherResult.get(position).getWeather().get(0).getDescription()));

        holder.txt_temperature.setText(new StringBuilder(String.valueOf(listWeatherResult.get(position).getMain().getTemp())).append("°C"));
    }

    @Override
    public int getItemCount() {
        return listWeatherResult.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

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
