package com.example.simpleuserlistapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class FormActivity : AppCompatActivity() {

    private val repository = UsersRepository.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        setupSaveButton()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> false
        }
    }

    private fun setupSaveButton() {
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
        showNotification(user)
        finish()
    }

    private fun showNotification(user: User) {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_add_24)
            .setContentText("${user.name} was added to the repository")
            .setContentTitle("New user added!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "User addition"
            val descriptionText = "Shown when a new user is added"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getAge(): Int {
        val toString = findViewById<EditText>(R.id.eTAge).text.toString()
        return if (toString.isNotBlank()) toString.toInt() else 0
    }

    companion object {

        private const val CHANNEL_ID = "CHANNEL_ID"
        private const val NOTIFICATION_ID = 123456

        fun newIntent(context: Context): Intent {
            return Intent(context, FormActivity::class.java)
        }
    }
}