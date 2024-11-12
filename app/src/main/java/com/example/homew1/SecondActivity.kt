package com.example.homew1


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.homew1.R

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textSecondScreen = findViewById<TextView>(R.id.textSecondScreen)
        val buttonToFirst = findViewById<Button>(R.id.buttonToFirst)
        val buttonToThird = findViewById<Button>(R.id.buttonToThird)

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val inputText = sharedPreferences.getString("inputText", "Текст не найден")
        textSecondScreen.text = inputText
        // Получаем текст из первого экрана

        buttonToFirst.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }

        buttonToThird.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}

