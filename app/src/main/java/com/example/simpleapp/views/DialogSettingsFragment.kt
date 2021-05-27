package com.example.simpleapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.simpleapp.R
import com.example.simpleapp.contracts.DialogSettingsContract
import com.example.simpleapp.databinding.FragmentDialogSettingsBinding
import com.example.simpleapp.presenters.DialogSettingsPresenter
import java.lang.IllegalStateException

class DialogSettingsFragment : DialogFragment(), DialogSettingsContract.View {
    private var binding: FragmentDialogSettingsBinding? = null
    private var currentApplicationTheme: Int = 0
    private lateinit var presenter: DialogSettingsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentApplicationTheme = arguments?.getInt(ARG_APP_THEME) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        setupButtons()
        initListeners()
    }

    private fun initPresenter() {
        presenter = DialogSettingsPresenter(this)
    }

    private fun initListeners() {
        binding?.apply {
            btnManager.setOnCheckedChangeListener{_, selectedButton ->
                when(selectedButton) {
                    R.id.btnDarkTheme -> presenter.onChangeThemeButtonClicked(DARK)
                    R.id.btnLightTheme -> presenter.onChangeThemeButtonClicked(LIGHT)
                    R.id.btnSystemTheme -> presenter.onChangeThemeButtonClicked(SYSTEM)
                }
            }
        }
    }

    private fun setupButtons() {
        when (currentApplicationTheme) {
            SYSTEM -> binding?.btnSystemTheme?.toggle()
            DARK -> binding?.btnLightTheme?.toggle()
            LIGHT -> binding?.btnDarkTheme?.toggle()
            else -> throw IllegalStateException("Invalid index")
        }
    }

    override fun applyLightTheme() {
        TODO("Not yet implemented")
    }

    override fun applyDarkTheme() {
        TODO("Not yet implemented")
    }

    override fun applyDefaultSystemTheme() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val ARG_APP_THEME = "APP_THEME"
        const val SYSTEM = 0
        const val DARK = 1
        const val LIGHT = 2

        fun newInstance(applicationThemeStatus: Int): DialogSettingsFragment {
            val args = Bundle().apply { putInt(ARG_APP_THEME, applicationThemeStatus) }
            return DialogSettingsFragment().apply { arguments = args }
        }
    }
}