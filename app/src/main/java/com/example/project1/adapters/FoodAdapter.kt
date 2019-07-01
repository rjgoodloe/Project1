package com.example.project1.adapters

import android.content.Context
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.project1.R
import com.example.project1.model.Food


@Suppress("NAME_SHADOWING")
class FoodAdapter(context: Context, foods: ArrayList<Food>) : ArrayAdapter<Food>(context, 0, foods) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        // Get the data item for this position
        val food = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false)
        }
        // Lookup view for data population
        val tvName = convertView!!.findViewById(R.id.tv_food_name) as TextView
        val tvCalories = convertView.findViewById(R.id.tv_calories) as TextView
        // Populate the data into the template view using the data object
        tvName.text = food!!.name
        tvCalories.text = food.calories.toString()
        // Return the completed view to render on screen
        return convertView
    }
}