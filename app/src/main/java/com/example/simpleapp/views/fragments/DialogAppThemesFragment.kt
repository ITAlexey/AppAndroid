package com.example.simpleapp.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import com.example.simpleapp.BaseApp
import com.example.simpleapp.R
import com.example.simpleapp.contracts.DialogAppThemeContract
import com.example.simpleapp.databinding.FragmentDialogAppThemesBinding
import com.example.simpleapp.presenters.DialogAppThemesPresenter
import com.example.simpleapp.views.MainActivity

class DialogAppThemesFragment : DialogFragment(), DialogAppThemeContract.View {
    private var binding: FragmentDialogAppThemesBinding? = null
    private lateinit var presenter: DialogAppThemeContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogAppThemesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPresenter()
        initListeners()
        presenter.onViewCreated()
    }

    private fun initPresenter() {
        val themeModel = (requireActivity().applicationContext as BaseApp).themeModel
        presenter = DialogAppThemesPresenter(this, themeModel)
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

    override fun changeAppTheme(themeType: Int) {
        (requireActivity() as MainActivity).applyAppTheme(themeType)
    }

    override fun turnOnLightThemeButton() {
        binding?.btnLightTheme?.isChecked = true
    }

    override fun turnOnDarkThemeButton() {
        binding?.btnDarkTheme?.isChecked = true
    }

    override fun turnOnDefaultSystemButton() {
        binding?.btnSystemTheme?.isChecked = true
    }

    companion object {
        private const val DARK = AppCompatDelegate.MODE_NIGHT_YES
        private const val LIGHT = AppCompatDelegate.MODE_NIGHT_NO
        private const val SYSTEM = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

        fun newInstance(): DialogAppThemesFragment =
            DialogAppThemesFragment()
    }
}