package com.example.project1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.project1.R
import com.example.project1.model.Exercise


class ExerciseAdapter(context: Context, exercises: ArrayList<Exercise>) : ArrayAdapter<Exercise>(context, 0, exercises) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        @Suppress("NAME_SHADOWING") var convertView = convertView
        // Get the data item for this position
        val exercise = getItem(position)
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent, false)
        }
        // Lookup view for data population
        val tvName = convertView!!.findViewById(R.id.tv_exercise_name) as TextView
        val tvTime = convertView.findViewById(R.id.tv_time) as TextView
        // Populate the data into the template view using the data object
        tvName.text = exercise!!.name
        tvTime.text = exercise.time.toString()
        // Return the completed view to render on screen
        return convertView
    }
}