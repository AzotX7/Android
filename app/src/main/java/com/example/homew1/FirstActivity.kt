package com.example.homew1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val editText = findViewById<EditText>(R.id.editText)
        val buttonToSecond = findViewById<Button>(R.id.buttonToSecond)
        val buttonToThird = findViewById<Button>(R.id.buttonToThird)

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val savedText = sharedPreferences.getString("inputText", "")
        editText.setText(savedText)  // Устанавливаем текст в поле ввода

        buttonToSecond.setOnClickListener {
            val text = editText.text.toString()
            val editor = sharedPreferences.edit()
            editor.putString("inputText", text)
            editor.apply()

            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        buttonToThird.setOnClickListener {
            val text = editText.text.toString()
            val editor = sharedPreferences.edit()
            editor.putString("inputText", text)
            editor.apply()

            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
    }
}
