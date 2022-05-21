package com.example.easytravel_sofiane_azarkan.Retrofit;

import com.example.easytravel_sofiane_azarkan.Model.WeatherForecastResult;
import com.example.easytravel_sofiane_azarkan.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {
    @GET("weather")
    Observable<WeatherResult> getWeatherByCityName(@Query("q") String q,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);

    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByCityName(@Query("q") String q,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String unit);

}
