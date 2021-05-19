package com.example.simpleapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.simpleapp.BaseApp
import com.example.simpleapp.contracts.LoggedInContract
import com.example.simpleapp.databinding.FragmentLoggedInBinding
import com.example.simpleapp.presenters.LoggedInPresenter

class LoggedInFragment : Fragment(), LoggedInContract.View {
    private var binding: FragmentLoggedInBinding? = null
    private lateinit var presenter: LoggedInContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoggedInBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initListeners()
    }

    override fun showSumResult(sumResult: String) {
        binding?.tvSumResult?.text = sumResult
    }

    override fun closeActivity() {
    }

    private fun initPresenter() {
        val app = requireActivity().applicationContext as BaseApp
        val model = app.pinModel
        presenter = LoggedInPresenter(this, model)
    }

    private fun initListeners() {
        binding?.imgLogOut?.setOnClickListener{ presenter.onLogOutButtonClicked() }
    }

    companion object {
        fun newInstance() =
            LoggedInFragment()
    }
}