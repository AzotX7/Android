package com.example.homework3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment

class QuestionFragment : Fragment() {

    private lateinit var questionText: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var radioButton4: RadioButton

    companion object {
        private const val ARG_QUESTION = "question"

        fun newInstance(question: Question): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putParcelable(ARG_QUESTION, question)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.question_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionText = view.findViewById(R.id.questionText)
        radioGroup = view.findViewById(R.id.radioGroup)
        radioButton1 = view.findViewById(R.id.radioButton1)
        radioButton2 = view.findViewById(R.id.radioButton2)
        radioButton3 = view.findViewById(R.id.radioButton3)
        radioButton4 = view.findViewById(R.id.radioButton4)

        val question = arguments?.getParcelable<Question>(ARG_QUESTION)
        question?.let { setQuestion(it) }

        setupListeners()
    }

    private fun setQuestion(question: Question) {
        questionText.text = question.text
        radioButton1.text = question.options.getOrNull(0)
        radioButton2.text = question.options.getOrNull(1)
        radioButton3.text = question.options.getOrNull(2)
        radioButton4.text = question.options.getOrNull(3)
    }

    private fun setupListeners() {
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            resetRadioButtonBackgrounds()

            val selectedButton = group.findViewById<RadioButton>(checkedId)
            selectedButton?.setBackgroundColor(resources.getColor(R.color.selected_answer_background))
        }
    }

    private fun resetRadioButtonBackgrounds() {
        radioButton1.setBackgroundColor(resources.getColor(android.R.color.transparent))
        radioButton2.setBackgroundColor(resources.getColor(android.R.color.transparent))
        radioButton3.setBackgroundColor(resources.getColor(android.R.color.transparent))
        radioButton4.setBackgroundColor(resources.getColor(android.R.color.transparent))
    }
}
