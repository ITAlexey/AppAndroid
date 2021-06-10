package com.example.simpleapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.commit
import com.example.simpleapp.BaseApp
import com.example.simpleapp.R
import com.example.simpleapp.contracts.MainActivityContract
import com.example.simpleapp.databinding.ActivityMainBinding
import com.example.simpleapp.models.themes.ThemeApp
import com.example.simpleapp.presenters.MainActivityPresenter
import com.example.simpleapp.views.fragments.DialogAppThemesFragment
import com.example.simpleapp.views.fragments.PinCodeFragment

class MainActivity : AppCompatActivity(), MainActivityContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainActivityContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val themeModel = (applicationContext as BaseApp).themeModel
        presenter = MainActivityPresenter(this, themeModel)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            showPinCodeFragment()
        }
        presenter.onViewCreated()
    }

    override fun onStart() {
        super.onStart()

        binding.imgBtnSettings.setOnClickListener { presenter.onSettingsButtonClicked() }
    }

    private fun showPinCodeFragment() {
        val pinCodeFragment = PinCodeFragment.newInstance()
        supportFragmentManager.commit {
            add(R.id.fragment_container, pinCodeFragment)
        }
    }

    override fun showAppThemesDialog() =
        DialogAppThemesFragment
            .newInstance()
            .show(supportFragmentManager, "hello")

    override fun applyAppTheme(themeType: ThemeApp) =
        AppCompatDelegate.setDefaultNightMode(themeType.themeMode)
}