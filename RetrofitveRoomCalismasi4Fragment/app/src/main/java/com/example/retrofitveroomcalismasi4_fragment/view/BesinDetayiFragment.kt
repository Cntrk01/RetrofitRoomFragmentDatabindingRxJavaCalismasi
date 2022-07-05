package com.example.retrofitveroomcalismasi4_fragment.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitveroomcalismasi4_fragment.R
import com.example.retrofitveroomcalismasi4_fragment.databinding.FragmentBesinDetayiBinding
import com.example.retrofitveroomcalismasi4_fragment.util.gorselIndir
import com.example.retrofitveroomcalismasi4_fragment.util.placeholderYap
import com.example.retrofitveroomcalismasi4_fragment.viewmodel.BesinDetayiViewModel
import com.example.retrofitveroomcalismasi4_fragment.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detayi.*


class BesinDetayiFragment : Fragment() {
    private lateinit var viewModel : BesinDetayiViewModel
    private var besinID=0
    private lateinit var dataBinding:FragmentBesinDetayiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_besin_detayi,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            besinID=BesinDetayiFragmentArgs.fromBundle(it).id
        }
        viewModel=ViewModelProvider(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiAl(besinID)
        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer {besin->
            besin?.let {
                dataBinding.besindetay=it
                /*besinIsim.text=it.besinIsim
                besinKalori.text=it.besinKalori
                besinKarbonhidrat.text=it.besinKarbonhidrat
                besinProtein.text=it.besinProtein
                besinYag.text=it.besinYag
                context?.let {
                    besinImage.gorselIndir(besin.besinGorsel!!, placeholderYap(it))
                }*/
            }
        })
    }
}