package com.example.rxbank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.rxbank.R
import com.example.rxbank.repository.AuthorizationToken
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var authorizationToken: AuthorizationToken

    var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authorizationToken = AuthorizationToken(requireContext())

        if (authorizationToken.getAuthorizationToken() == "") {
            view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        backBtn.setOnClickListener {
            authorizationToken.saveAuthorizationToken("")
            view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        addLoan.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_loanInfoFragment)
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}