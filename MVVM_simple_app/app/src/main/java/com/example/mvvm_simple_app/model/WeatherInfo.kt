package com.example.mvvm_simple_app.model

import com.google.gson.annotations.SerializedName

data class WeatherInfo(

    @field:SerializedName("base") var base: String,
    @field:SerializedName("cod") var code: Int,
    @field:SerializedName("clouds") var clouds: Clouds,
    @field:SerializedName("coord") var coord: Coord,
    @field:SerializedName("main") var main: Main,
    @field:SerializedName("weather") var weather: ArrayList<Weather>,
    @field:SerializedName("wind") var wind: Wind
) {
    inner class Clouds(
        @field:SerializedName("all") val all: Int
    )

    inner class Coord(
        @field:SerializedName("lat") val lat: Double,
        @field:SerializedName("lon") val lon: Double
    )

    inner class Main(
        @field:SerializedName("feels_like") val feelsLike: Double,
        @field:SerializedName("grnd_level") val grndLevel: Int,
        @field:SerializedName("humidity") val humidity: Int,
        @field:SerializedName("pressure") val pressure: Int,
        @field:SerializedName("temp") val temp: Double,
        @field:SerializedName("temp_max") val tempMax: Double,
        @field:SerializedName("temp_min") val tempMin: Double
    )

    inner class Weather(
        @field:SerializedName("description") val description: String,
        @field:SerializedName("icon") val icon: String,
        @field:SerializedName("id") val id: Int,
        @field:SerializedName("main") val main: String
    )

    inner class Wind(
        @field:SerializedName("deg") val deg: Int,
        @field:SerializedName("gust") val gust: Double,
        @field:SerializedName("speed") val speed: Double
    )
}