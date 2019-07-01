package com.example.project1.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project1.R
import com.example.project1.activities.MainActivity
import kotlinx.android.synthetic.main.home_fragment.view.*


@SuppressLint("ValidFragment")
class HomeFragment(context: Context) : Fragment() {

    private val parentContext: Context = context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.home_fragment, container, false)

        view.b_exercise.setOnClickListener { (activity as MainActivity).showFragment(ExerciseFragment(this.parentContext)) }

        view.b_food.setOnClickListener { (activity as MainActivity).showFragment(FoodFragment(this.parentContext)) }

        return view
    }
}