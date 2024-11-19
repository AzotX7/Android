package com.example.homew1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.homew1.R

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val textThirdScreen = findViewById<TextView>(R.id.textThirdScreen)
        val buttonToFirst = findViewById<Button>(R.id.buttonToFirst)
        val buttonToSecond = findViewById<Button>(R.id.buttonToSecond)

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val inputText = sharedPreferences.getString("inputText", "Текст не найден")
        textThirdScreen.text = inputText  // Отображаем текст


        buttonToFirst.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }

        buttonToSecond.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}
