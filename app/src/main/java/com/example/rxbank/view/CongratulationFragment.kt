package com.example.rxbank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.rxbank.R
import kotlinx.android.synthetic.main.fragment_congratulation.*

class CongratulationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_congratulation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user.text = arguments?.getString("USER")
        back_to_Login?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_congratulationFragment_to_loginFragment)
        )
    }
}