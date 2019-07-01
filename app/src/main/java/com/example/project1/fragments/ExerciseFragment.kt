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
import com.example.project1.model.Exercise
import kotlinx.android.synthetic.main.exercise_fragment.*
import kotlinx.android.synthetic.main.exercise_fragment.view.*
import java.lang.Integer.parseInt
import java.lang.Math.abs


@SuppressLint("ValidFragment")
class ExerciseFragment(context: Context) : Fragment() {

    private val parentContext: Context = context
    private var exerciseGoal: Int = 0
    private lateinit var listView: ListView


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.exercise_fragment, container, false)


        exerciseGoal = (activity as MainActivity).exerciseGoal

        when {
            exerciseGoal > 0 -> view.tv_exercise_time.text = "Today's Goal: $exerciseGoal minutes"
            exerciseGoal == 0 -> view.tv_exercise_time.setTextColor(0xff00fa00.toInt())
            exerciseGoal < 0 -> {
                view.tv_exercise_time.setTextColor(0xff00fa00.toInt())
                exerciseGoal = abs((activity as MainActivity).exerciseGoal)
                view.tv_exercise_time.text = "Goal succeeded by: $exerciseGoal minutes"
            }
        }


        this.listView = view.findViewById(R.id.lv_items)
        listView.adapter = (activity as MainActivity).exerciseAdapter

        val fab: View = view.findViewById(R.id.fab)
        fab.setOnClickListener { displayDialog(R.layout.dialog_enter_exercise) }


        return view

    }

    @SuppressLint("SetTextI18n")
    private fun displayDialog(layout: Int) {
        val dialog = Dialog(this.parentContext)
        dialog.setContentView(layout)

        val actv: AutoCompleteTextView = dialog.findViewById(R.id.et_exercise_name) as AutoCompleteTextView
        // Get the string array
        val exercises: Array<out String> = resources.getStringArray(R.array.exercises_array)
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String>(this.parentContext, android.R.layout.simple_list_item_1, exercises).also { adapter ->
            actv.setAdapter(adapter)
        }
        val timetv: EditText = dialog.findViewById(R.id.et_time)

        val window = dialog.window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)



        dialog.findViewById<Button>(R.id.b_done).setOnClickListener {
            when {
                actv.text.toString() == "" -> Toast.makeText(this.parentContext, "Please enter exercise name", Toast.LENGTH_SHORT).show()
                timetv.text.toString() == "" -> Toast.makeText(this.parentContext, "Please enter time spent", Toast.LENGTH_SHORT).show()
                else -> {
                    val time: Int = parseInt(timetv.text.toString())
                    val name: String = actv.text.toString()
                    (activity as MainActivity).exerciseGoal -= time
                    exerciseGoal = (activity as MainActivity).exerciseGoal
                    this.tv_exercise_time.text = "Time to Goal: $exerciseGoal minutes"


                    when {
                        (activity as MainActivity).exerciseGoal == 0 -> this.tv_exercise_time.setTextColor(0xff00fa00.toInt())
                        (activity as MainActivity).exerciseGoal < 0 -> {
                            this.tv_exercise_time.setTextColor(0xff00fa00.toInt())
                            exerciseGoal = abs((activity as MainActivity).exerciseGoal)
                            this.tv_exercise_time.text = "Goal succeeded by: $exerciseGoal minutes"
                        }
                    }


                    val newEntry = Exercise(name, time)
                    (activity as MainActivity).exerciseAdapter.add(newEntry)

                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }


}
