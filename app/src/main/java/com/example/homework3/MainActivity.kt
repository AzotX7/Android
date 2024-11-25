package com.example.homework3

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnBack: Button
    private lateinit var btnNext: Button
    private lateinit var btnFinish: Button
    private lateinit var rootLayout: RelativeLayout
    private lateinit var questionCounterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        btnBack = findViewById(R.id.btnBack)
        btnNext = findViewById(R.id.btnNext)
        btnFinish = findViewById(R.id.btnFinish)
        rootLayout = findViewById(R.id.rootLayout)
        questionCounterTextView = findViewById(R.id.questionCounterTextView)

        val questions = listOf(
            Question("Что такое Java?", listOf("Язык программирования", "Бренд кофе", "Тип чая", "Операционная система")),
            Question("Что такое ООП?", listOf("Объектно-ориентированное программирование", "Объектно-ориентированный протокол", "Объектно-ориентированный процесс", "Объектно-ориентированная база данных")),
            Question("Что такое JVM?", listOf("Java Virtual Machine", "Java Visual Machine", "Java Virtual Memory", "Java Virtual Environment")),
            Question("Что такое JDK?", listOf("Java Development Kit", "Java Debugging Kit", "Java Deployment Kit", "Java Design Kit")),
            Question("Что такое JRE?", listOf("Java Runtime Environment", "Java Remote Environment", "Java Runtime Engine", "Java Runtime Executor")),
            Question("Что такое интерфейс в Java?", listOf("Способ определения методов без реализации", "Способ определения классов", "Способ определения переменных", "Способ определения констант")),
            Question("Что такое абстрактный класс в Java?", listOf("Класс, который не может быть инстанцирован", "Класс, который может быть инстанцирован", "Класс, который не может иметь методов", "Класс, который не может иметь переменных")),
            Question("Что такое наследование в Java?", listOf("Механизм, позволяющий одному классу наследовать свойства другого класса", "Механизм, позволяющий одному классу наследовать методы другого класса", "Механизм, позволяющий одному классу наследовать переменные другого класса", "Механизм, позволяющий одному классу наследовать константы другого класса")),
            Question("Что такое полиморфизм в Java?", listOf("Способ использования одного интерфейса для разных типов данных", "Способ использования одного метода для разных типов данных", "Способ использования одного класса для разных типов данных", "Способ использования одной переменной для разных типов данных")),
            Question("Что такое исключение в Java?", listOf("Объект, который сигнализирует об ошибке", "Объект, который сигнализирует о предупреждении", "Объект, который сигнализирует о событии", "Объект, который сигнализирует о завершении")),
            Question("Что такое коллекции в Java?", listOf("Фреймворк, который предоставляет архитектуру для хранения и манипуляции группами объектов", "Фреймворк, который предоставляет архитектуру для хранения и манипуляции данными", "Фреймворк, который предоставляет архитектуру для хранения и манипуляции строками", "Фреймворк, который предоставляет архитектуру для хранения и манипуляции числами")),
            Question("Что такое многопоточность в Java?", listOf("Способ выполнения нескольких потоков одновременно", "Способ выполнения одного потока одновременно", "Способ выполнения нескольких процессов одновременно", "Способ выполнения одного процесса одновременно")),
            Question("Что такое лямбда-выражения в Java?", listOf("Способ определения анонимных функций", "Способ определения анонимных классов", "Способ определения анонимных переменных", "Способ определения анонимных констант")),
            Question("Что такое Stream API в Java?", listOf("API для работы с потоками данных", "API для работы с потоками ввода-вывода", "API для работы с потоками событий", "API для работы с потоками процессов")),
            Question("Что такое аннотации в Java?", listOf("Метаданные, которые могут быть добавлены к коду", "Метаданные, которые могут быть добавлены к классам", "Метаданные, которые могут быть добавлены к методам", "Метаданные, которые могут быть добавлены к переменным"))
        )

        val adapter = QuestionAdapter(this, questions)
        viewPager.adapter = adapter

        btnBack.setOnClickListener {
            if (viewPager.currentItem > 0) {
                viewPager.currentItem = viewPager.currentItem - 1
            }
        }

        btnNext.setOnClickListener {
            if (viewPager.currentItem < questions.size - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
            }
        }

        btnFinish.setOnClickListener {
            Snackbar.make(rootLayout, "Опрос завершен!", Snackbar.LENGTH_LONG).show()
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                btnBack.isEnabled = position > 0
                btnNext.isEnabled = position < questions.size - 1
                btnFinish.visibility = if (position == questions.size - 1) View.VISIBLE else View.GONE

                updateQuestionCounter(position)
            }
        })

        updateQuestionCounter(0)
    }

    private fun updateQuestionCounter(position: Int) {
        val totalQuestions = (viewPager.adapter as QuestionAdapter).itemCount
        questionCounterTextView.text = "Вопрос ${position + 1} из $totalQuestions"
    }
}