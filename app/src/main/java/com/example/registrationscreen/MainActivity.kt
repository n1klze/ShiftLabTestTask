package com.example.registrationscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.registrationscreen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.birthDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.firstName.addTextChangedListener(firstNameTextWatcher)
        binding.lastName.addTextChangedListener(lastNameTextWatcher)
        binding.birthDate.addTextChangedListener(birthDateTextWatcher)
        binding.password.addTextChangedListener(passwordTextWatcher)
        binding.confirmPassword.addTextChangedListener(confirmPasswordTextWatcher)

        binding.registerButton.setEnabled(false)

        binding.registerButton.setOnClickListener { checkForm() }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    private fun checkForm() {
        if (validateFirstName() == null &&
            validateLastName() == null &&
            validatePassword() == null &&
            validateConfirmPassword() == null
        ) {
            val intent = Intent(this, WelcomeScreenActivity::class.java)
            val user = User(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.birthDate.text.toString(),
                binding.password.text.toString()
            )
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }

    private val firstNameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            validateRegisterButton()
            binding.firstNameContainer.helperText = validateFirstName()
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    private fun validateFirstName(): String? {
        val firstNameText = binding.firstName.text.toString()
        if (firstNameText.isEmpty()) {
            return "*Обязательное поле"
        }
        if (firstNameText.matches(".*[0-9].*".toRegex())) {
            return "Имя не может содержать цифры"
        }
        return null
    }

    private val lastNameTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            validateRegisterButton()
            binding.lastNameContainer.helperText = validateLastName()
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    private fun validateLastName(): String? {
        val lastNameText = binding.lastName.text.toString()
        if (lastNameText.length < 2) {
            return "Фамилия не может быть менее 2-х букв"
        }
        if (lastNameText.matches(".*[0-9].*".toRegex())) {
            return "Фамилия не может содержать цифры"
        }
        return null
    }

    private val birthDateTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            validateRegisterButton()
        }

        override fun afterTextChanged(p0: Editable?) {
            binding.birthDateContainer.helperText = validateBirthDate()
        }
    }

    private fun validateBirthDate(): String? {
        val birthDateText = binding.birthDate.text.toString()
        if (birthDateText.isEmpty()) {
            return "*Обязательное поле"
        }
        return null
    }

    private val passwordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            validateRegisterButton()
            binding.passwordContainer.helperText = validatePassword()
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    private fun validatePassword(): String? {
        val passwordText = binding.password.text.toString()
        if (passwordText.length < 8) {
            return "Пароль должен содержать не менее 8-ми знаков"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Пароль должен содержать как минимум одну букву верхнего регистра"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Пароль должен содержать как минимум одну букву нижнего регистра"
        }
        if (!passwordText.matches(".*[0-9].*".toRegex())) {
            return "Пароль должен содержать как минимум одну цифру"
        }
        return null
    }

    private val confirmPasswordTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            validateRegisterButton()
            binding.confirmPasswordContainer.helperText = validateConfirmPassword()
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    private fun validateConfirmPassword(): String? {
        val passwordText = binding.password.text.toString()
        val confirmPasswordText = binding.confirmPassword.text.toString()
        if (passwordText != confirmPasswordText) {
            return "Пароли должны совпадать"
        }
        return null
    }

    private fun validateRegisterButton() {
        if (binding.firstName.text.toString().isNotEmpty() &&
            binding.lastName.text.toString().isNotEmpty() &&
            binding.birthDate.text.toString().isNotEmpty() &&
            binding.password.text.toString().isNotEmpty() &&
            binding.confirmPassword.text.toString().isNotEmpty()
        ) {
            binding.registerButton.setEnabled(true)
        } else {
            binding.registerButton.setEnabled(false)
        }
    }
}
