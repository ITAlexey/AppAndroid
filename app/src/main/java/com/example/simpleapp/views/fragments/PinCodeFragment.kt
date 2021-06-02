package com.example.simpleapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isInvisible
import androidx.fragment.app.commit
import com.example.simpleapp.BaseApp
import com.example.simpleapp.R
import com.example.simpleapp.adapter.PinAdapter
import com.example.simpleapp.contracts.PinCodeFragmentContract
import com.example.simpleapp.databinding.FragmentPinCodeBinding
import com.example.simpleapp.presenters.PinCodePresenter

class PinCodeFragment : Fragment(), PinCodeFragmentContract.View {
    private var binding: FragmentPinCodeBinding? = null
    private lateinit var presenter: PinCodeFragmentContract.Presenter
    private lateinit var pinAdapter: PinAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPinCodeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
        initPresenter()
    }

    override fun showPopupMessage(@StringRes popupTextResId: Int) =
        Toast.makeText(requireContext(), popupTextResId, Toast.LENGTH_SHORT).show()

    override fun updatePinField(pinLen: Int) {
        pinAdapter.updateState(pinLen)
    }

    override fun updateVisibilityBackspaceButton(isVisible: Boolean) {
        updateViewVisibility(binding?.conLNumbers?.imgBackSpace, isVisible)
    }

    override fun updateVisibilityResetButton(isVisible: Boolean) {
        updateViewVisibility(binding?.btnReset, isVisible)
    }

    override fun moveToLoggedInFragment(pinSumResult: Int) {
        val loggedInFragment = LoggedInFragment.newInstance(pinSumResult)
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fragment_container, loggedInFragment)
            addToBackStack(null)
        }
    }

    override fun setTitleText(@StringRes titleTextResId: Int) {
        binding?.tvTitle?.text = resources.getString(titleTextResId)
    }

    private fun initPresenter() {
        val app = requireActivity().applicationContext as BaseApp
        val model = app.pinModel
        presenter = PinCodePresenter(this, model)
    }

    private fun initAdapter() {
        pinAdapter = PinAdapter()
        binding?.rvPinCode?.apply {
            adapter = pinAdapter
            setHasFixedSize(true)
        }
    }

    private fun initListeners() {
        val keyboardBinding = binding?.conLNumbers
        keyboardBinding?.apply {
            tvNumber0.setOnClickListener { onKeyboardButtonClicked(0, it) }
            tvNumber1.setOnClickListener { onKeyboardButtonClicked(1, it) }
            tvNumber2.setOnClickListener { onKeyboardButtonClicked(2, it) }
            tvNumber3.setOnClickListener { onKeyboardButtonClicked(3, it) }
            tvNumber4.setOnClickListener { onKeyboardButtonClicked(4, it) }
            tvNumber5.setOnClickListener { onKeyboardButtonClicked(5, it) }
            tvNumber6.setOnClickListener { onKeyboardButtonClicked(6, it) }
            tvNumber7.setOnClickListener { onKeyboardButtonClicked(7, it) }
            tvNumber8.setOnClickListener { onKeyboardButtonClicked(8, it) }
            tvNumber9.setOnClickListener { onKeyboardButtonClicked(9, it) }
            imgBackSpace.setOnClickListener { onKeyboardButtonClicked(item = it) }
        }
        binding?.btnReset?.setOnClickListener { presenter.onResetButtonClicked() }
    }

    private fun updateViewVisibility(view: View?, isVisible: Boolean) {
        view?.isInvisible = !isVisible
    }

    private fun animateKeyboardButton(item: View) =
        item.startAnimation(AnimationUtils.loadAnimation(item.context, R.anim.btn_clicked))

    private fun onKeyboardButtonClicked(number: Int = -1, item: View) {
        animateKeyboardButton(item)
        when {
            number < 0 -> presenter.onBackspaceButtonClicked()
            else -> presenter.onNumberButtonClicked(number)
        }
    }

    companion object {
        fun newInstance() =
            PinCodeFragment()
    }
}