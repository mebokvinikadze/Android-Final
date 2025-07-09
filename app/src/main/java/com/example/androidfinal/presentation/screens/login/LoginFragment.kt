package com.example.androidfinal.presentation.screens.login

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.androidfinal.R
import com.example.androidfinal.common.Resource
import com.example.androidfinal.databinding.FragmentLoginBinding
import com.example.androidfinal.presentation.base_fragment.BaseFragment
import com.example.androidfinal.util.showErrorSnackBar
import com.example.androidfinal.util.showSuccessSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModels()
    private var hasNavigatedToHome = false


    override fun setUp() {
        navigateToHomeIfRememberMeIsChecked()
    }

    override fun listeners() {
        loginListener()
        navigateToRegisterListener()
    }


    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val rememberMe = binding.chkBoxRememberMe.isChecked

        loginViewModel.login(email, password, rememberMe)
        loginStateManagement()

    }


    private fun loginStateManagement() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collectLatest { state ->
                    when (state) {
                        is Resource.Loading -> {
                            loading()
                        }

                        is Resource.Success -> {
                            if (!hasNavigatedToHome) {
                                hasNavigatedToHome = true
                                loaded()

                                binding.root.showSuccessSnackBar(getString(R.string.login_successful))
                                Log.d("test", "test")
                                navigateToHome()
                            }
                        }

                        is Resource.Error -> {
                            loaded()
                            binding.root.showErrorSnackBar(state.errorMessage)
                        }
                    }
                }
            }
        }
    }

    private fun loginListener() {
        binding.btnLogin.setOnClickListener {
            if (!validateFields()) {
                return@setOnClickListener
            }
            login()
        }
    }

    private fun loading() {
        binding.loading.visibility = View.VISIBLE
        binding.chkBoxRememberMe.isEnabled = false
        binding.btnRegister.isEnabled = false
        binding.btnLogin.isEnabled = false

    }

    private fun loaded() {
        binding.loading.visibility = View.GONE
        binding.chkBoxRememberMe.isEnabled = true
        binding.btnRegister.isEnabled = true
        binding.btnLogin.isEnabled = true
    }

    private fun validateFields(): Boolean {
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.root.showErrorSnackBar(getString(R.string.email_should_not_be_empty))
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                .matches()
        ) {
            binding.root.showErrorSnackBar(getString(R.string.invalid_email_address))
            return false
        }

        if (binding.etPassword.text.toString().isEmpty()) {
            binding.root.showErrorSnackBar(getString(R.string.password_should_not_be_empty))
            return false
        }

        return true
    }

    private fun navigateToRegisterListener() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(
            LoginFragmentDirections.actionLoginFragmentToHomeFragment(),
            NavOptions.Builder()
                .setPopUpTo(R.id.loginFragment, true)
                .build()
        )
    }

    private fun navigateToHomeIfRememberMeIsChecked() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (loginViewModel.checkSavedUser()) {
                    navigateToHome()
                }
            }
        }
    }
}