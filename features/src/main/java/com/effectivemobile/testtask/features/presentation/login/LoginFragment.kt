package com.effectivemobile.testtask.features.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.presentation.R
import com.google.android.material.button.MaterialButton

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEnter = view.findViewById<MaterialButton>(R.id.btnEnter)

        btnEnter.setOnClickListener {
            findNavController().navigate(R.id.coursesFragment)
        }
    }
}