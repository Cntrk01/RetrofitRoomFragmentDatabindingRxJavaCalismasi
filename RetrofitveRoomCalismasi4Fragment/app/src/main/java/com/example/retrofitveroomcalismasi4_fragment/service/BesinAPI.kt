package com.example.retrofitveroomcalismasi4_fragment.service

import com.example.retrofitveroomcalismasi4_fragment.model.Besin
import io.reactivex.Single
import retrofit2.http.GET

interface BesinAPI {
    //https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json
    @GET("atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesinServiceData():Single<List<Besin>>
}