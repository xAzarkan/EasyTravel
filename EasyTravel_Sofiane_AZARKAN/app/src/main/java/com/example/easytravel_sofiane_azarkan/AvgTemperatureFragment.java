package com.example.easytravel_sofiane_azarkan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easytravel_sofiane_azarkan.Adapter.FindDestinationAdapter;
import com.example.easytravel_sofiane_azarkan.Adapter.PopularCitiesAdapter;
import com.example.easytravel_sofiane_azarkan.Common.Common;
import com.example.easytravel_sofiane_azarkan.Model.WeatherForecastResult;
import com.example.easytravel_sofiane_azarkan.Model.WeatherResult;
import com.example.easytravel_sofiane_azarkan.Retrofit.IOpenWeatherMap;
import com.example.easytravel_sofiane_azarkan.Retrofit.RetrofitClient;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AvgTemperatureFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    WeatherForecastResult[] myWeatherForecastResult;
    FindDestinationAdapter adapter;

    int cpt = 0;


    String[] popularCitiesArray = {"New York", "Los Angeles", "Brussels", "Paris", "Casablanca", "Barcelona", "Toronto"};

    static AvgTemperatureFragment instance;

    RecyclerView recycler_avg_temp;

    public AvgTemperatureFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }

    public static AvgTemperatureFragment getInstance(){
        if(instance == null)
            instance = new AvgTemperatureFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View itemView = inflater.inflate(R.layout.fragment_avg_temperature, container, false);
        recycler_avg_temp = (RecyclerView) itemView.findViewById(R.id.recycler_avg_temp);
        recycler_avg_temp.setHasFixedSize(true);
        recycler_avg_temp.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        /*
        for(int i = 0; i < popularCitiesArray.length; i++)
        {
            getAvgTemperature(popularCitiesArray[i]);
        } */


        adapter = new FindDestinationAdapter(getContext());

        getAvgTemperature(popularCitiesArray[1]);

        return itemView;
    }

    private void getAvgTemperature(String city)
    {
        compositeDisposable.add(mService.getForecastWeatherByCityName(city,
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                               @Override
                               public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                                   //FindDestinationAdapter.listWeatherForecastResult.add(weatherForecastResult);
                                   displayAvgTemperature(weatherForecastResult);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Log.d("ERROR", "" + throwable.getMessage());
                               }
                           }
                )
        );
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void displayAvgTemperature(WeatherForecastResult weatherForecastResult) {

        adapter.addWeatherForecastResult(weatherForecastResult);

        if (adapter.getItemCount() == 40)
        {
            recycler_avg_temp.setAdapter(adapter);
        }

    }
}