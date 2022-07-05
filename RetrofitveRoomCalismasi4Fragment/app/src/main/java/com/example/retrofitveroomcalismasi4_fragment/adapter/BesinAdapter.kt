package com.example.retrofitveroomcalismasi4_fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitveroomcalismasi4_fragment.R
import com.example.retrofitveroomcalismasi4_fragment.databinding.BesinRecyclerRowBinding
import com.example.retrofitveroomcalismasi4_fragment.model.Besin
import com.example.retrofitveroomcalismasi4_fragment.util.gorselIndir
import com.example.retrofitveroomcalismasi4_fragment.util.placeholderYap
import com.example.retrofitveroomcalismasi4_fragment.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_recycler_row.view.*

class BesinAdapter(val itemList:ArrayList<Besin>) : RecyclerView.Adapter<BesinAdapter.BesinGorunum>(),BesinClickListener {
    class BesinGorunum(var view: BesinRecyclerRowBinding):RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinGorunum {
       val inflate=LayoutInflater.from(parent.context)
           //val view=inflate(R.layout.besin_recycler_row,parent,false)
        val view=DataBindingUtil.inflate<BesinRecyclerRowBinding>(inflate,R.layout.besin_recycler_row,parent,false)
       return BesinGorunum(view)
    }

    override fun onBindViewHolder(holder: BesinGorunum, position: Int) {
        holder.view.besin=itemList[position]
        holder.view.listener=this
       /*
        holder.itemView.isim.text = itemList.get(position).besinIsim
        holder.itemView.kalori.text = itemList.get(position).besinKalori
        holder.itemView.setOnClickListener {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(itemList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        holder.itemView.imageView.gorselIndir(itemList.get(position).besinGorsel, placeholderYap(holder.itemView.context))*/
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun besinListesiniGuncelle(newList:List<Besin>){
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun besinTiklandi(view: View) {//bu fonksiyon tıklama mantıgında calısıyor dırek
        //burda positiona erişemiyoruz view ile gizli bir degisken tanımlayıp ona erişeceğiz...
        val uuid=view.besin_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetayiFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }
}