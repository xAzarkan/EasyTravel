package com.example.easytravel_sofiane_azarkan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easytravel_sofiane_azarkan.Adapter.WeatherForecastAdapter;
import com.example.easytravel_sofiane_azarkan.Common.Common;
import com.example.easytravel_sofiane_azarkan.Model.WeatherForecastResult;
import com.example.easytravel_sofiane_azarkan.Retrofit.IOpenWeatherMap;
import com.example.easytravel_sofiane_azarkan.Retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ForecastFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView txt_city_name;
    SearchView txt_city_name_search;
    RecyclerView recycler_forecast;

    String cityName = "";

    static ForecastFragment instance;

    public static ForecastFragment getInstance(){
        if(instance == null)
            instance = new ForecastFragment();
        return instance;
    }

    public ForecastFragment() {
        // Required empty public constructor
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_forecast, container, false);

        txt_city_name = (TextView) itemView.findViewById(R.id.txt_city_name);

        recycler_forecast = (RecyclerView) itemView.findViewById(R.id.recycler_forecast);
        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        txt_city_name_search = (SearchView) itemView.findViewById(R.id.txt_city_name_search);

        txt_city_name_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                cityName = text;
                getForecastWeather(); //affiches les nouvelles informations
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                return false;
            }
        });

        getForecastWeather();

        return itemView;
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void getForecastWeather() {
        if(cityName.equals("")) //si rien écrit
            cityName = "Brussels"; //Brussels par défaut

        compositeDisposable.add(mService.getForecastWeatherByCityName(cityName,
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherForecastResult>() {
                   @Override
                   public void accept(WeatherForecastResult weatherForecastResult) throws Exception {
                        displayForecastWeather(weatherForecastResult);
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

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult) {
        txt_city_name.setText(new StringBuilder("Weather forecasts for " + weatherForecastResult.getCity().getName()));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherForecastResult);
        recycler_forecast.setAdapter(adapter);
    }
}