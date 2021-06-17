package com.example.rxbank.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.rxbank.R
import com.example.rxbank.api.RetrofitService
import com.example.rxbank.data.requests.LoginRequest
import com.example.rxbank.repository.AuthorizationToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var authorizationToken: AuthorizationToken

    var disposable: Disposable? = null

    private var isCorrectNameInput: Boolean = false
    private var isCorrectPasswordInput: Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registrationButton?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registrationFragment)
        )

        button_sign_in?.setOnClickListener {
            val user = LoginRequest(
                textLogin.text.toString(),
                textPass.text.toString()
            )
            sigIn(user)
        }
        textLogin?.doOnTextChanged { text, _, _, _ ->
            if (text?.length!! < 1) {
                textLogin?.error = "Введите данные"

                isCorrectNameInput = false
            } else {
                isCorrectNameInput = true
            }

            allowRegistration()
        }
        textPass?.doOnTextChanged { text, _, _, _ ->
            if (textPass!!.text.isEmpty()) {
                textPass?.error = "Введите данные"

                isCorrectPasswordInput = false
            } else {
                isCorrectPasswordInput = true
            }

            allowRegistration()
        }
    }

    // POST to Sign_In user
    private fun sigIn(user: LoginRequest) {

        val clientR = RetrofitService.create()
        Log.v("test", "$user")
        disposable = clientR.postLogin(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    onResponse(response)
                    authorizationToken.saveAuthorizationToken(response)
                    view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
                },
                { t -> onFailure(t) }
            )
    }

    private fun onResponse(response: String) {
        Toast.makeText(context, response, Toast.LENGTH_LONG).show()
    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
    }

    private fun allowRegistration() {
        button_sign_in?.isEnabled = isCorrectNameInput && isCorrectPasswordInput
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}