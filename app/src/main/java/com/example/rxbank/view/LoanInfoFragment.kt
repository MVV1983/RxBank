package com.example.rxbank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rxbank.api.RetrofitService
import com.example.rxbank.repository.AuthorizationToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import com.example.rxbank.R
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_loan_info.*

class LoanInfoFragment : Fragment() {

    private lateinit var authorizationToken: AuthorizationToken

    var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loan_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authorizationToken = AuthorizationToken(requireContext())

        val key = authorizationToken.getAuthorizationToken().toString()

        getLoanCondition(key)

        submitToReview?.setOnClickListener {

        }
    }

    private fun getLoanCondition(token: String) {

        val clientR = RetrofitService.create()

        disposable = clientR.getLoanConditions(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                maxAmount.text = it.maxAmount.toString()
                loanPercent.text = it.percent.toString()
                loanPeriod.text = it.period.toString()
            },
                { t -> onFailure(t) })
    }

    private fun onFailure(t: Throwable) {
        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}