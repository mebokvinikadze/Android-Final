package com.example.androidfinal.presentation.screens.register

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.androidfinal.R
import com.example.androidfinal.common.Resource
import com.example.androidfinal.databinding.FragmentRegisterBinding
import com.example.androidfinal.presentation.base_fragment.BaseFragment
import com.example.androidfinal.util.showErrorSnackBar
import com.example.androidfinal.util.showSuccessSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {
    private val registerViewModel: RegisterViewModel by viewModels()
    private var hasNavigatedToLogin = false

    override fun setUp() {
    }

    override fun listeners() {
        registerListener()
        navigateBackListener()
    }

    private fun registration() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val username = binding.etUserName.text.toString()
        val age = binding.etAge.text.toString().toInt()
        val weight = binding.etWeight.text.toString().toInt()
        val height = binding.etHeight.text.toString().toInt()

        registerViewModel.register(email, password, username, weight, age, height)
        registerStateManagement()
    }

    private fun registerStateManagement() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                registerViewModel.registerState.collectLatest { state ->
                    when (state) {
                        is Resource.Loading -> {
                            loading()
                        }

                        is Resource.Success -> {
                            if (!hasNavigatedToLogin) {
                                hasNavigatedToLogin = true
                                loaded()

                                binding.root.showSuccessSnackBar(getString(R.string.register_successful))
                                navigateToLoginListener()

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


    private fun registerListener() {
        binding.btnRegister.setOnClickListener {
            if (!validateFields()) {
                return@setOnClickListener
            }
            registration()
        }
    }

    private fun navigateToLoginListener() {
        findNavController().navigate(
            RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(),
            NavOptions.Builder()
                .setPopUpTo(R.id.registerFragment, true)
                .setLaunchSingleTop(true)
                .build()
        )
    }

    private fun navigateBackListener() {
        binding.ivBackIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun loading() {
        binding.loading.visibility = View.VISIBLE
        binding.ivBackIcon.isEnabled = true
        binding.btnRegister.isEnabled = false

    }

    private fun loaded() {
        binding.loading.visibility = View.GONE
        binding.ivBackIcon.isEnabled = true
        binding.btnRegister.isEnabled = true
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
        if (binding.etUserName.text.toString().isEmpty()) {
            binding.root.showErrorSnackBar(getString(R.string.username_should_not_be_empty))
            return false
        }
        if (binding.etUserName.text.toString().length < 3) {
            binding.root.showErrorSnackBar(getString(R.string.username_should_be_at_least_3_characters_long))
            return false
        }
        if (!binding.etUserName.text.toString().matches("^[a-zA-Z0-9_]*$".toRegex())) {
            binding.root.showErrorSnackBar(getString(R.string.username_can_only_contain_letters_numbers_and_underscores))
            return false
        }
        if (binding.etAge.text.toString().isEmpty()) {
            binding.root.showErrorSnackBar(getString(R.string.age_should_not_be_empty))
            return false
        }
        if (binding.etAge.text.toString().toInt() < 12 || binding.etAge.text.toString()
                .toInt() > 100
        ) {
            binding.root.showErrorSnackBar(getString(R.string.enter_valid_age_12_100))
            return false
        }
        if (binding.etWeight.text.toString().isEmpty()) {
            binding.root.showErrorSnackBar(getString(R.string.weight_should_not_be_empty))
            return false
        }
        if (binding.etWeight.text.toString().toInt() < 20 || binding.etWeight.text.toString()
                .toInt() > 635
        ) {
            binding.root.showErrorSnackBar(getString(R.string.weight_should_be_valid_20_635))
            return false
        }
        if (binding.etHeight.text.toString().isEmpty()) {
            binding.root.showErrorSnackBar(getString(R.string.height_should_not_be_empty))
            return false
        }
        if (binding.etHeight.text.toString().toInt() < 100 || binding.etHeight.text.toString()
                .toInt() > 210
        ) {
            binding.root.showErrorSnackBar(getString(R.string.height_should_be_valid_100_210))
            return false
        }
        if (binding.etPassword.text.toString().isEmpty()) {
            binding.root.showErrorSnackBar(getString(R.string.password_should_not_be_empty))
            return false
        }

        if (binding.etConfirmPassword.text.toString().isEmpty()) {
            binding.root.showErrorSnackBar(getString(R.string.password_should_not_be_empty))
            return false
        }
        if (binding.etConfirmPassword.text.toString() != binding.etPassword.text.toString()) {
            binding.root.showErrorSnackBar(getString(R.string.passwords_should_be_equal))
            return false
        }

        return true
    }
}