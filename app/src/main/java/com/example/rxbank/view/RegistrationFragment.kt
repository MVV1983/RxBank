package com.example.rxbank.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.rxbank.R
import com.example.rxbank.api.RetrofitService
import com.example.rxbank.data.requests.LoginRequest
import com.example.rxbank.data.response.LoginResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_registration.*


class RegistrationFragment : Fragment() {

    var disposable: Disposable? = null
    val bundle = Bundle()

    private var isCorrectNameInput: Boolean = false
    private var isCorrectPasswordInput: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registrationBtn?.setOnClickListener {
            val user = LoginRequest(
                registrationName.text.toString(),
                registrationPass.text.toString()
            )
            postRegistration(user)
        }
        registrationName?.doOnTextChanged { text, _, _, _ ->
            if (text?.length!! < 1) {
                registrationUser?.error = "Введите данные"

                isCorrectNameInput = false
            } else {
                isCorrectNameInput = true
            }

            allowRegistration()
        }
        registrationPass?.doOnTextChanged { text, _, _, _ ->
            if (registrationPass!!.text.isEmpty()) {
                registrationPass?.error = "Введите данные"

                isCorrectPasswordInput = false
            } else {
                isCorrectPasswordInput = true
            }

            allowRegistration()
        }

    }


    // POST new RegistrationData
    private fun postRegistration(user: LoginRequest) {

        val clientR = RetrofitService.create()
        Log.v("test", "$user")
        disposable = clientR.postRequest(user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response -> onResponse(response) },
                { t -> onFailure(t) }
            )
    }

    private fun onResponse(response: LoginResponse) {
        bundle.putString("USER",response.name)
        Toast.makeText(context, response.name + response.role, Toast.LENGTH_LONG).show()
        view?.findNavController()?.navigate(R.id.action_registrationFragment_to_congratulationFragment,bundle)
    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
    }

    private fun allowRegistration() {
        registrationBtn?.isEnabled = isCorrectNameInput && isCorrectPasswordInput
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}