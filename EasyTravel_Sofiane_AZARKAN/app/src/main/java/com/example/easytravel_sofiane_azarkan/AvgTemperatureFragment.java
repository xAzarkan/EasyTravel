package com.example.easytravel_sofiane_azarkan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easytravel_sofiane_azarkan.Adapter.FindDestinationAdapter;
import com.example.easytravel_sofiane_azarkan.Adapter.PopularCitiesAdapter;
import com.example.easytravel_sofiane_azarkan.Common.Common;
import com.example.easytravel_sofiane_azarkan.Model.PopularCity;
import com.example.easytravel_sofiane_azarkan.Model.WeatherForecastResult;
import com.example.easytravel_sofiane_azarkan.Model.WeatherResult;
import com.example.easytravel_sofiane_azarkan.Retrofit.IOpenWeatherMap;
import com.example.easytravel_sofiane_azarkan.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AvgTemperatureFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView txt_headline;

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

        txt_headline = (TextView)itemView.findViewById(R.id.txt_headline);

        FindDestinationAdapter.listPopularCities = new ArrayList<PopularCity>();

        for(int i = 0; i < popularCitiesArray.length; i++)
        {
            getAvgTemperature(popularCitiesArray[i]);
        }

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



        ArrayList<Double> listOfTemp = new ArrayList<Double>();
        ArrayList<String> listOfIcons = new ArrayList<String>();

        double avgTemperature = 0;
        String avgIcon = "";
        String soleil = "01";
        String soleilNuage = "02";
        String nuage = "03";
        String nuageNuage = "04";
        String pluieForte = "09";
        String pluie = "10";
        String orage = "11";
        String neige = "13";
        String brouillard = "50";

        int iconSoleilValue = 0;
        int iconSoleilNuageValue = 0;
        int iconNuageValue = 0;
        int iconNuageNuageValue = 0;
        int iconPluieForteValue = 0;
        int iconPluieValue = 0;
        int iconOrageValue = 0;
        int iconNeigeValue = 0;
        int iconBrouillardValue = 0;

        String description = "";

        for(int i = 0; i < weatherForecastResult.getList().size(); i++)
        {
            listOfTemp.add(weatherForecastResult.getList().get(i).getMain().getTemp());
            listOfIcons.add(weatherForecastResult.getList().get(i).getWeather().get(0).getIcon());
        }

        for(Double temp : listOfTemp){
            avgTemperature += temp;
        }

        for(String icon : listOfIcons)
        { //BIEN DOCUMENTER CECI
            if(icon.contains(soleil))
                iconSoleilValue += 3;
            else if(icon.contains(soleilNuage))
                iconSoleilNuageValue += 2;
            else if(icon.contains(nuage))
                iconNuageValue += 1;
            else if(icon.contains(nuageNuage))
                iconNuageNuageValue += 1;
            else if(icon.contains(pluieForte))
                iconPluieForteValue += 3;
            else if(icon.contains(pluie))
                iconPluieValue += 2;
            else if(icon.contains(orage))
                iconOrageValue += 3;
            else if(icon.contains(neige))
                iconNeigeValue += 4;
            else if(icon.contains(brouillard))
                iconBrouillardValue += 1;
        }

        Integer[] iconValues = {iconSoleilValue, iconSoleilNuageValue, iconNuageValue, iconNuageNuageValue, iconPluieForteValue, iconPluieValue, iconOrageValue, iconNeigeValue, iconBrouillardValue};

        int biggestIconValue = Collections.max(Arrays.asList(iconValues)); //récupère la valeure maximal du tableau

        if(biggestIconValue == iconSoleilValue) {
            avgIcon = soleil;
            description = "Clear sky";
        }
        else if(biggestIconValue == iconSoleilNuageValue) {
            avgIcon = soleilNuage;
            description = "Few clouds";
        }
        else if(biggestIconValue == iconNuageValue){
            avgIcon = nuage;
            description = "Scattered clouds";
        }
        else if(biggestIconValue == iconNuageNuageValue){
            avgIcon = nuageNuage;
            description = "Broken clouds";
        }
        else if(biggestIconValue == iconPluieForteValue){
            avgIcon = pluieForte;
            description = "Heavy rain";
        }
        else if(biggestIconValue == iconPluieValue){
            avgIcon = pluie;
            description = "Rainy weather";
        }
        else if(biggestIconValue == iconOrageValue){
            avgIcon = orage;
            description = "Thunderstormy weather";
        }
        else if(biggestIconValue == iconNeigeValue){
            avgIcon = neige;
            description = "Snowy";
        }
        else if(biggestIconValue == iconBrouillardValue){
            avgIcon = brouillard;
            description = "Mist";
        }

        avgTemperature = avgTemperature/listOfTemp.size();

        PopularCity popularCity = new PopularCity();

        popularCity.setName(weatherForecastResult.getCity().getName());
        popularCity.setAverageTemperature(avgTemperature);
        popularCity.setAverageIcon(new StringBuilder("http://openweathermap.org/img/wn/")
                .append(avgIcon + "d")
                .append("@2x.png").toString());
        popularCity.setAverageDescription(description);

        FindDestinationAdapter.listPopularCities.add(popularCity);

        FindDestinationAdapter adapter;
        adapter = new FindDestinationAdapter(getContext(), popularCity);

        recycler_avg_temp.setAdapter(adapter);
    }
}