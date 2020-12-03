package com.eliseylobanov.cloudnotes.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.auth.AuthResultContract
import com.eliseylobanov.cloudnotes.errors.NoAuthException
import com.eliseylobanov.cloudnotes.viewmodels.SplashViewModel
import com.eliseylobanov.cloudnotes.viewmodels.SplashViewState
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val RC_SIGN_IN = 458

class SplashFragment : Fragment() {

    private val viewModel by viewModel<SplashViewModel>()
    lateinit var navController: NavController

    private val layoutRes: Int = R.layout.splash_fragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        viewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is SplashViewState.Error -> renderError(it.error)
                SplashViewState.Auth -> navController
                    .navigate(SplashFragmentDirections.actionSplashFragmentToNotesMainFragment())
            }
        }
    }

    private val authResultLauncher =
        registerForActivityResult(AuthResultContract()) { idpResponse ->
            handleAuthResponse(idpResponse)
        }

    private fun handleAuthResponse(idpResponse: IdpResponse?) {
        when {
            (idpResponse == null || idpResponse.error != null) -> {
                Toast.makeText(requireContext(), idpResponse?.error.toString(), Toast.LENGTH_LONG).show()
            }
            else -> {
                navController.navigate(SplashFragmentDirections.actionSplashFragmentToNotesMainFragment())
            }
        }
    }

    private fun renderError(error: Throwable) {
        when (error) {
            is NoAuthException -> authResultLauncher.launch(RC_SIGN_IN)
            else -> error.message?.let { Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show() }
        }
    }

}