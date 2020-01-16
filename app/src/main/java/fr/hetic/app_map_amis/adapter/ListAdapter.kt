package fr.hetic.app_map_amis.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import fr.hetic.app_map_amis.R
import fr.hetic.app_map_amis.trip

class ListAdapter(val mCtx: Context,val layoutResId: Int,val TripList: List<trip>) : ArrayAdapter<trip>(mCtx, layoutResId, TripList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View{
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName)

        val trip = TripList[position]

        textViewName.text = trip.id

        return view
        //return super.getView(position, convertView, parent)
    }
}