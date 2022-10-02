package com.example.registrationscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class WelcomeScreenActivity : AppCompatActivity() {
    private lateinit var greetingButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        title = "Главный экран"

        val user = getIntent().getSerializableExtra("user") as User
        greetingButton = findViewById(R.id.greeting_button)
        greetingButton.setOnClickListener { showGreetingForm(user) }
    }

    private fun showGreetingForm(user: User?) {
        AlertDialog.Builder(this)
            .setTitle("Приветствие")
            .setMessage("Привет, ${user?.firstName}!")
            .setPositiveButton("OK") { _, _ ->
                //do nothing
            }
            .show()
    }
}

