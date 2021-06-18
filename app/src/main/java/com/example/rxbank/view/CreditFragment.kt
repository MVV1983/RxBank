package com.example.rxbank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.findNavController
import com.example.rxbank.R
import com.example.rxbank.api.RetrofitService
import com.example.rxbank.data.requests.NewLoanRequest
import com.example.rxbank.repository.AuthorizationToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_credit.*

class CreditFragment : Fragment() {

    private lateinit var authorizationToken: AuthorizationToken

    var disposable: Disposable? = null
    val bundle = Bundle()


    private var isCorrectFirsNameInput: Boolean = false
    private var isCorrectPhoneInput: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_credit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authorizationToken = AuthorizationToken(requireContext())

        val key = authorizationToken.getAuthorizationToken().toString()

        getCreditButton?.setOnClickListener {
            getNewLoan(key)
        }


        inputName?.doOnTextChanged { text, _, _, _ ->
            if (text?.length!! < 1) {
                inputName?.error = "Введите фамилию"

                isCorrectFirsNameInput = false
            } else {
                isCorrectFirsNameInput = true
            }

            allowDispatch()
        }

        inputPhone?.doOnTextChanged { text, _, _, _ ->
            if (text?.length!! < 7) {
                inputPhone?.error = "Мин 7 цифр"

                isCorrectPhoneInput = false
            } else {
                isCorrectPhoneInput = true
            }

            allowDispatch()
        }
    }

    private fun getNewLoan(key: String) {
        val clientR = RetrofitService.create()

        disposable = clientR.postLoans(
            key,
            NewLoanRequest(
                inputAmount?.text.toString().toInt(),
                inputName?.text.toString(),
                inputLastname?.text.toString(),
                inputPercent?.text.toString().toDouble(),
                inputPeriod?.text.toString().toInt(),
                inputPhone?.text.toString()
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                bundle.putString("FINAL", "${it.id},${it.amount},${it.state}")

                view?.findNavController()
                    ?.navigate(R.id.action_creditFragment_to_finalFragment, bundle)

            }, { t ->
                onFailure(t)
            })
    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(context, "Произошла ошибка при вводе данных на кредит", Toast.LENGTH_SHORT)
            .show()
    }

    private fun allowDispatch() {
        getCreditButton?.isEnabled =
            isCorrectFirsNameInput && isCorrectPhoneInput
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}