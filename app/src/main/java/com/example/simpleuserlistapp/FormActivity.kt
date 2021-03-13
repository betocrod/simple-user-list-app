package com.example.simpleuserlistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {

    private val repository = UsersRepository.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        findViewById<Button>(R.id.bSave).setOnClickListener { save() }
    }

    private fun save() {
        val user = User(
            name = findViewById<EditText>(R.id.eTName).text.toString(),
            lastName = findViewById<EditText>(R.id.eTLastName).text.toString(),
            age = getAge(),
            favoriteColor = findViewById<EditText>(R.id.eTFavoriteColor).text.toString()
        )
        repository.add(user)
        finish()
    }

    private fun getAge(): Int {
        val toString = findViewById<EditText>(R.id.eTAge).text.toString()
        return if (toString.isNotBlank()) toString.toInt() else 0
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, FormActivity::class.java)
        }
    }
}