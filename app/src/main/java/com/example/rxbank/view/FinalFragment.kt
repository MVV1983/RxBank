package com.example.rxbank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.rxbank.R
import kotlinx.android.synthetic.main.fragment_final.*

class FinalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_final, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        creditInformation.text = arguments?.getString("FINAL")

        backHome.setOnClickListener {
            view.findNavController().navigate(R.id.action_finalFragment_to_homeFragment)
        }
    }
}