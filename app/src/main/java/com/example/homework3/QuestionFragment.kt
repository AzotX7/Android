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
    private lateinit var checkbox1: CheckBox
    private lateinit var checkbox2: CheckBox
    private lateinit var checkbox3: CheckBox
    private lateinit var checkbox4: CheckBox

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
        checkbox1 = view.findViewById(R.id.checkbox1)
        checkbox2 = view.findViewById(R.id.checkbox2)
        checkbox3 = view.findViewById(R.id.checkbox3)
        checkbox4 = view.findViewById(R.id.checkbox4)

        val question = arguments?.getParcelable<Question>(ARG_QUESTION)
        question?.let { setQuestion(it) }
    }

    private fun setQuestion(question: Question) {
        questionText.text = question.text
        checkbox1.text = question.options.getOrNull(0)
        checkbox2.text = question.options.getOrNull(1)
        checkbox3.text = question.options.getOrNull(2)
        checkbox4.text = question.options.getOrNull(3)

        checkbox1.setOnCheckedChangeListener { _, isChecked ->
            checkbox1.setBackgroundColor(if (isChecked) resources.getColor(R.color.selected_answer_background) else resources.getColor(android.R.color.transparent))
        }
        checkbox2.setOnCheckedChangeListener { _, isChecked ->
            checkbox2.setBackgroundColor(if (isChecked) resources.getColor(R.color.selected_answer_background) else resources.getColor(android.R.color.transparent))
        }
        checkbox3.setOnCheckedChangeListener { _, isChecked ->
            checkbox3.setBackgroundColor(if (isChecked) resources.getColor(R.color.selected_answer_background) else resources.getColor(android.R.color.transparent))
        }
        checkbox4.setOnCheckedChangeListener { _, isChecked ->
            checkbox4.setBackgroundColor(if (isChecked) resources.getColor(R.color.selected_answer_background) else resources.getColor(android.R.color.transparent))
        }
    }
}