package com.example.retrofitveroomcalismasi4_fragment.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitveroomcalismasi4_fragment.model.Besin
import com.example.retrofitveroomcalismasi4_fragment.service.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application:Application) : BaseViewModel(application) {
    val besinLiveData=MutableLiveData<Besin>()

    fun roomVerisiAl(uuid:Int){
        roomVerisiniGetir(uuid)
    }

    private fun roomVerisiniGetir(uuid:Int){
        launch {
            val besin=BesinDatabase(getApplication()).besinDao().getBesin(uuid)
            besinLiveData.value =besin
        }
    }
}