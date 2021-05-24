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
    private var pinSumResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pinSumResult = arguments?.getInt(ARG_PIN_SUM) ?: 0
    }
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

    private fun initPresenter() {
        val app = requireActivity().applicationContext as BaseApp
        val model = app.pinModel
        presenter = LoggedInPresenter(this, model)
    }

    private fun initListeners() {
        binding?.imgLogOut?.setOnClickListener{ presenter.onLogOutButtonClicked() }
    }

    override fun moveToPinCodeFragment() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun showPinSumResult() {
        binding?.tvSumResult?.text = pinSumResult.toString()
    }

    companion object {
        private const val  ARG_PIN_SUM = "com.example.simpleapp.pin_sum"

        fun newInstance(pinSumResult: Int): LoggedInFragment {
            val args = Bundle().apply { putInt(ARG_PIN_SUM, pinSumResult) }
            return LoggedInFragment().apply { arguments =  args}
        }
    }
}