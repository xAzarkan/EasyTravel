package com.example.easytravel_sofiane_azarkan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easytravel_sofiane_azarkan.Adapter.PopularCitiesAdapter;
import com.example.easytravel_sofiane_azarkan.Adapter.WeatherForecastAdapter;
import com.example.easytravel_sofiane_azarkan.Common.Common;
import com.example.easytravel_sofiane_azarkan.Model.Weather;
import com.example.easytravel_sofiane_azarkan.Model.WeatherForecastResult;
import com.example.easytravel_sofiane_azarkan.Model.WeatherResult;
import com.example.easytravel_sofiane_azarkan.Retrofit.IOpenWeatherMap;
import com.example.easytravel_sofiane_azarkan.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.WeakHashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class PopularCitiesFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    //ArrayList<WeatherResult> listWeatherResult = new ArrayList<WeatherResult>();
    WeatherResult[] myWeatherResult;

    String[] popularCitiesArray = {"New York", "Los Angeles", "Brussels", "Paris", "Casablanca", "Barcelona", "Toronto"};

    RecyclerView recycler_popular_cities;


    static PopularCitiesFragment instance;

    public PopularCitiesFragment() {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = RetrofitClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }

    public static PopularCitiesFragment getInstance(){
        if(instance == null)
            instance = new PopularCitiesFragment();
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_popular_cities, container, false);

        recycler_popular_cities = (RecyclerView) itemView.findViewById(R.id.recycler_popular_cities);
        recycler_popular_cities.setHasFixedSize(true);
        recycler_popular_cities.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        for(int i = 0; i < popularCitiesArray.length; i++)
        {
            getCurrentWeather(popularCitiesArray[i]);
        }

        return itemView;
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void getCurrentWeather(String city)
    {
        compositeDisposable.add(mService.getWeatherByCityName(city,
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherResult>() {
                           @Override
                           public void accept(WeatherResult weatherResult) throws Exception {
                               PopularCitiesAdapter.listWeatherResult.add(weatherResult);
                               displayForecastWeather();
                           }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(getActivity(), "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                )
        );
    }

    private void displayForecastWeather() {

        PopularCitiesAdapter adapter = new PopularCitiesAdapter(getContext());
        recycler_popular_cities.setAdapter(adapter);
    }

}