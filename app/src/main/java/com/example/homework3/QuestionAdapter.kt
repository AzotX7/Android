package com.example.homework3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class QuestionAdapter(fa: FragmentActivity, private val questions: List<Question>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = questions.size

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment.newInstance(questions[position])
    }
}