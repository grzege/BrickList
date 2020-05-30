package com.example.bricklist.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.bricklist.R
import com.example.bricklist.tables.Brick

class BricksAdapter(private val context: Context,
                    private val dataSource: ArrayList<Brick>):BaseAdapter() {

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

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.inventory_part,parent,false)

        val brickName = rowView.findViewById(R.id.brickName) as TextView
        val brickInfo = rowView.findViewById(R.id.brickInfo) as TextView
        val brickAmount = rowView.findViewById(R.id.brickAmount) as TextView

        val brick = getItem(position) as Brick
        brickName.text=brick.name
        val infoText = brick.colorName + "[" + brick.itemID + "]"
        brickInfo.text= infoText
        val amountText = "${brick.quantityInStore} of ${brick.quantityInSet}"
        brickAmount.text = amountText

        return rowView
    }
}