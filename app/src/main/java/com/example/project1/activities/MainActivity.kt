package com.example.project1.activities

import android.app.ActionBar
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.project1.R
import com.example.project1.adapters.ExerciseAdapter
import com.example.project1.adapters.FoodAdapter
import com.example.project1.fragments.HomeFragment
import com.example.project1.model.Exercise
import com.example.project1.model.Food
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val fm = this.supportFragmentManager

    private var exerciseList: ArrayList<Exercise> = ArrayList()
    lateinit var exerciseAdapter: ExerciseAdapter

    private var foodList: ArrayList<Food> = ArrayList()
    lateinit var foodAdapter: FoodAdapter

    var exerciseGoal: Int = 0
    var calorieGoal: Int = 0
    var totalCalories: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        getGoals(R.layout.dialog_enter_goals)

        exerciseAdapter = ExerciseAdapter(this, exerciseList)
        foodAdapter = FoodAdapter(this, foodList)

        fm.beginTransaction()
                .replace(R.id.frag_placeholder, HomeFragment(this))
                .commit()


        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun getGoals(layout: Int) {
        val dialog = Dialog(this)
        dialog.setContentView(layout)

        val calgoal: EditText = dialog.findViewById(R.id.et_calorie_goal)
        val exergoal: EditText = dialog.findViewById(R.id.et_exercise_goal)

        val window = dialog.window
        window?.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        dialog.findViewById<Button>(R.id.b_submit).setOnClickListener {

            when {
                calgoal.text.toString() == "" -> Toast.makeText(
                        this,
                        "Please enter calorie value",
                        Toast.LENGTH_SHORT
                ).show()

                exergoal.text.toString() == "" -> Toast.makeText(
                        this,
                        "Please enter exercise time",
                        Toast.LENGTH_SHORT
                ).show()
                else -> {
                    calorieGoal = Integer.parseInt(calgoal.text.toString())
                    exerciseGoal = Integer.parseInt(exergoal.text.toString())

                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun showFragment(fragment: Fragment) {

        fm.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.frag_placeholder, fragment)
                .commit()

    }
}
