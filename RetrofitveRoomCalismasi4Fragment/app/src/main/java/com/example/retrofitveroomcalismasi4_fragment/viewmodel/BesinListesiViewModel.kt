package com.example.retrofitveroomcalismasi4_fragment.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.retrofitveroomcalismasi4_fragment.model.Besin
import com.example.retrofitveroomcalismasi4_fragment.service.BesinDatabase
import com.example.retrofitveroomcalismasi4_fragment.service.BesinService
import com.example.retrofitveroomcalismasi4_fragment.util.OzelSharedPrefences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private val besinApiServis = BesinService()
    private val disposable = CompositeDisposable()
    private val ozelSharedPrefences = OzelSharedPrefences(getApplication())
    private var guncellemeZamani=10*60*1000*1000*1000L

    fun refreshData(){
        val kaydedilmeZamani=ozelSharedPrefences.zamaniAl()
        if(kaydedilmeZamani!=null && kaydedilmeZamani!=0L && System.nanoTime()-kaydedilmeZamani<guncellemeZamani){
            verileriSqLitedanAl()
        }else{
            getData()
        }

    }

     fun verileriInternettenAlSwipe(){
        getData()
    }

    private fun getData() {
        besinYukleniyor.value=true
        disposable.add(
            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        sqLiteSakla(t)
                        Toast.makeText(getApplication(),"Veriler Internetten Alındı",Toast.LENGTH_LONG).show()
                    }
                    override fun onError(e: Throwable) {
                        besinHataMesaji.value=true
                        besinYukleniyor.value=false
                        e.printStackTrace()
                    }

                })
        )
    }
    private fun besinleriGoster(besinlerListesi:List<Besin>){
        besinler.value=besinlerListesi
        besinHataMesaji.value=false
        besinYukleniyor.value=false
    }
    private fun sqLiteSakla(besinlerListesi:List<Besin>){
        launch {
            val dao=BesinDatabase(getApplication()).besinDao()
            dao.deleteBesin()
            val uuidListesi=dao.insertAll(*besinlerListesi.toTypedArray()) //insertte long dönüyor
            var i=0
            while (i<besinlerListesi.size){
                besinlerListesi[i].uuid=uuidListesi[i].toInt()
                i+=1
            }
            besinleriGoster(besinlerListesi)
        }
        ozelSharedPrefences.zamaniKaydet(System.nanoTime())
    }
    private fun verileriSqLitedanAl(){

        launch {
            val besinListesi=BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Veriler Roomdan Alındı",Toast.LENGTH_LONG).show()
        }
    }


}