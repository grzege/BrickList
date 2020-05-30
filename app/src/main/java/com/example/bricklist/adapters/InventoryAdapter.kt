package com.example.bricklist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.bricklist.R
import com.example.bricklist.tables.Inventory

class InventoryAdapter(private val context: Context,
                       private val dataSource: ArrayList<Inventory>):BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as
                LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.inventory_list,parent,false)
        val nameTextView = rowView.findViewById(R.id.InventoryName) as TextView
        val inventory = getItem(position) as Inventory
        nameTextView.text=inventory.name
        return rowView
    }
}