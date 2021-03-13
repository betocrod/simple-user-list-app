package com.example.simpleuserlistapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        if (user != null) showUserInfo(user) else finish()
    }

    private fun showUserInfo(user: User) {
        findViewById<TextView>(R.id.tVName).text = "The user's name is ${user.name}."
        findViewById<TextView>(R.id.tVLastName).text = "The user's last name is ${user.lastName}."
        findViewById<TextView>(R.id.tVAge).text = "The user is ${user.age} year(s) old."
        findViewById<TextView>(R.id.tVFavoriteColor).text = "The user's favorite color is ${user.favoriteColor}"
    }

    companion object {

        private const val EXTRA_USER = "user"

        fun newIntent(context: Context, user: User): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            return intent
        }
    }
}