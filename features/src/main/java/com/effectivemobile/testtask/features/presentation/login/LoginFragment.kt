package com.effectivemobile.testtask.features.presentation.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.effectivemobile.testtask.features.R
import com.effectivemobile.testtask.features.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentLoginBinding.bind(view)

        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()

        binding.etEmail.filters = arrayOf(InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                val char = source[i]
                if (Character.UnicodeBlock.of(char) == Character.UnicodeBlock.CYRILLIC) {
                    return@InputFilter ""
                }
            }
            null
        })

        fun validateInputs() {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            val isEmailValid = email.matches(emailRegex)
            val isPasswordNotEmpty = password.isNotEmpty()

            binding.btnEnter.isEnabled = isEmailValid && isPasswordNotEmpty
        }

        binding.etEmail.doAfterTextChanged { validateInputs() }
        binding.etPassword.doAfterTextChanged { validateInputs() }

        binding.btnEnter.setOnClickListener {
            findNavController().navigate(R.id.coursesFragment)
        }

        binding.btnVk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/"))
            startActivity(intent)
        }

        binding.btnOk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://ok.ru/"))
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}