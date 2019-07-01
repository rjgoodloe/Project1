package com.example.project1.fragments

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.project1.R
import com.example.project1.activities.MainActivity
import com.example.project1.model.Food
import kotlinx.android.synthetic.main.food_fragment.*
import kotlinx.android.synthetic.main.food_fragment.view.*
import java.lang.Integer.parseInt


@SuppressLint("ValidFragment")
class FoodFragment(context: Context) : Fragment() {

    private val parentContext: Context = context
    private var calorieGoal: Int = 0
    private var totalCalories: Int = 0
    private lateinit var listView: ListView


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.food_fragment, container, false)


        calorieGoal = (activity as MainActivity).calorieGoal

        when {
            calorieGoal < 0 -> view.tv_calories_remaining.setTextColor(0xffff0000.toInt())
        }

        view.tv_calories_remaining.text = "Calories Remaining: $calorieGoal"

        this.listView = view.findViewById(R.id.lv_items)
        listView.adapter = (activity as MainActivity).foodAdapter

        val fab: View = view.findViewById(R.id.fab)
        fab.setOnClickListener { displayDialog(R.layout.dialog_enter_food) }

        return view

    }


    @SuppressLint("SetTextI18n")
    private fun displayDialog(layout: Int) {
        val dialog = Dialog(this.parentContext)
        dialog.setContentView(layout)

        val actv: AutoCompleteTextView = dialog.findViewById(R.id.et_food_name) as AutoCompleteTextView
        // Get the string array
        val foods: Array<out String> = resources.getStringArray(R.array.foods_array)
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter(this.parentContext, android.R.layout.simple_list_item_1, foods).also { adapter ->
            actv.setAdapter(adapter)
        }
        val caltv: EditText = dialog.findViewById(R.id.et_calories)

        val window = dialog.window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)



        dialog.findViewById<Button>(R.id.b_done).setOnClickListener {
            when {
                actv.text.toString() == "" -> Toast.makeText(this.parentContext, "Please enter food name", Toast.LENGTH_SHORT).show()
                caltv.text.toString() == "" -> Toast.makeText(this.parentContext, "Please enter number of calories", Toast.LENGTH_SHORT).show()
                else -> {
                    val calories: Int = parseInt(caltv.text.toString())
                    val name: String = actv.text.toString()

                    (activity as MainActivity).calorieGoal -= calories
                    calorieGoal = (activity as MainActivity).calorieGoal
                    this.tv_calories_remaining.text = "Calories Remaining: $calorieGoal"

                    (activity as MainActivity).totalCalories += calories
                    totalCalories = (activity as MainActivity).totalCalories
                    this.tv_total_calories.text = "Total Calories: $totalCalories"

                    when {
                        calorieGoal < 0 -> this.tv_calories_remaining.setTextColor(0xffff0000.toInt())
                    }


                    val newEntry = Food(name, calories)
                    (activity as MainActivity).foodAdapter.add(newEntry)

                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }
}