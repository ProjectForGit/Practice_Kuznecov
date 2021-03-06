package com.example.practice

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import com.example.practice.models.Weather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private val request = "https://api.openweathermap.org"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_first, container, false)
        val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        if (!sharedPref.contains("city")) {
            parentFragmentManager.beginTransaction().replace(R.id.fragment, SeconfFragment()).commit()
            Toast.makeText(context, "Город", Toast.LENGTH_LONG).show()
            return root
        }

        val Text = root.findViewById<TextView>(R.id.textView)

        val retrofit = Retrofit.Builder()
            .baseUrl(request)
            .addConverterFactory(GsonConverterFactory.create())
            .build().also {

                it.create(Interface::class.java)
                    .getWeather("Москва", BuildConfig.API_KEY).enqueue(object :
                        retrofit2.Callback<Weather> {


                        override fun onFailure(call: retrofit2.Call<Weather>, t: Throwable) {
                            Toast.makeText(context, "Произошла ошибка.", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: retrofit2.Call<Weather>,
                            response: retrofit2.Response<Weather>
                        ) {
                            val weather = response.body()!!
                            val zero = 273.16
                            val message = "Погода в городе ${sharedPref.getString("city", "Москва")}:\n\n" +
                                        "Температура: ${(weather.main.temp - zero).roundToInt()}°C\n\n" +
                                        "Ощущается как: ${(weather.main.feels_like - zero).roundToInt()}\n\n" +
                                        "Погода: ${weather.weather[0].description}\n\n" +
                                        when (weather.weather[0].main) {
                                            "Snow" -> "Пора слепить снеговика!"
                                            "Broken clouds" -> "Советуем одеться потеплее"
                                            "Clear" -> "Лучшее время, чтобы выйти на прогулку"
                                            "Clouds" -> "Можно ненадолго выйти погулять"
                                            else -> "Советуем остаться дома"
                                        }
                            (context as Activity).runOnUiThread {
                                Text.text = message
                            }
                        }
                    })
            }
        Text.startAnimation(AnimationUtils.loadAnimation(context, R.anim.blure))

        return root
    }
}