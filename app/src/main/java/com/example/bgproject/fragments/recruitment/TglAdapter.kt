package com.example.bgproject.fragments.recruitment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bgproject.R
import com.example.bgproject.model.Tgl

class TglAdapter() : RecyclerView.Adapter<TglAdapter.TglViewHolder>() {

    private var tglList= emptyList<Tgl>()

    class TglViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val fullName: TextView = view.findViewById(R.id.tvFullName)
        val sex: TextView = view.findViewById(R.id.tvSex)
        val state: TextView = view.findViewById(R.id.tvState)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TglViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.tgl_list,parent,false)
        return TglViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return tglList.size
    }

    override fun onBindViewHolder(holder: TglViewHolder, position: Int) {
        val currentItem = tglList[position]
        holder.fullName.text = currentItem.fullName
        holder.sex.text = currentItem.sex
        holder.state.text = currentItem.state

    }

    fun setData(tgl:List<Tgl>){
        this.tglList = tgl
        notifyDataSetChanged()
    }


}



