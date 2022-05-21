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
import com.example.easytravel_sofiane_azarkan.Model.PopularCity;
import com.example.easytravel_sofiane_azarkan.Model.WeatherForecastResult;
import com.example.easytravel_sofiane_azarkan.Model.WeatherResult;
import com.example.easytravel_sofiane_azarkan.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FindDestinationAdapter extends RecyclerView.Adapter<FindDestinationAdapter.MyViewHolder>{

    Context context;
    public WeatherForecastResult weatherForecastResult;
    public PopularCity popularCity;
    public static ArrayList<PopularCity> listPopularCities;

    public static ArrayList<WeatherForecastResult> listWeatherForecastResult = new ArrayList<WeatherForecastResult>();

    public FindDestinationAdapter(Context context, PopularCity popularCity) {
        this.context = context;
        this.popularCity = popularCity;
    }

    @NonNull
    @Override
    public FindDestinationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_city_weather, parent, false);
        return new FindDestinationAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull FindDestinationAdapter.MyViewHolder holder, int position) {

        Picasso.get().load(new StringBuilder(listPopularCities.get(position).getAverageIcon()).toString()).into(holder.img_weather);

        holder.txt_city_name.setText(new StringBuilder(listPopularCities.get(position).getName()));

        holder.txt_description.setText(new StringBuilder(listPopularCities.get(position).getAverageDescription()));

        holder.txt_temperature.setText(new StringBuilder(String.format("%.2f", listPopularCities.get(position).getAverageTemperature())).append("°C"));
    }

    @Override
    public int getItemCount() {
        int numberOfCardsToShow = listPopularCities.size();
        return numberOfCardsToShow;
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
