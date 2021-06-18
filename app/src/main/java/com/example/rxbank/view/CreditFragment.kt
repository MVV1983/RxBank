package com.example.rxbank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.rxbank.R
import com.example.rxbank.api.RetrofitService
import com.example.rxbank.repository.AuthorizationToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_credit.*
import kotlinx.android.synthetic.main.fragment_loan_info.*

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
            // getNewLoan(key)
        }
    }

    //private fun getNewLoan(token: String) {

    //   val clientR = RetrofitService.create()

    //  disposable = clientR.postLoans(token)
    //      .subscribeOn(Schedulers.io())
    //     .observeOn(AndroidSchedulers.mainThread())
    //    .subscribe({

    //   },
    //       {

    //     })
    //  }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}