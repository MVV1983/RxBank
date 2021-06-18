package com.example.rxbank.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    }

    private fun getNewLoan(key:String) {
        val clientR = RetrofitService.create()

        disposable = clientR.postLoans(key,
            NewLoanRequest(
                enter_Amount?.text.toString().toInt(),
                enter_Name?.text.toString(),
                enter_lastname?.text.toString(),
                enter_percent?.text.toString().toDouble(),
                enter_period?.text.toString().toInt(),
                enterphone_num?.text.toString()
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.findNavController()?.navigate(R.id.action_creditFragment_to_finalFragment)

            }, {
                Toast.makeText(
                    context,
                    "Ошибка оформления займа",
                    Toast.LENGTH_LONG
                ).show()
            })
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}