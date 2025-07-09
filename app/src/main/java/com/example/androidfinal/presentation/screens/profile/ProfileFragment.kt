package com.example.androidfinal.presentation.screens.profile

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.androidfinal.R
import com.example.androidfinal.common.Resource
import com.example.androidfinal.databinding.FragmentProfileBinding
import com.example.androidfinal.presentation.base_fragment.BaseFragment
import com.example.androidfinal.util.showErrorSnackBar
import com.example.androidfinal.util.showSuccessSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val profileViewModel: ProfileViewModel by viewModels()


    override fun setUp() {
        displayUserDetails()
    }

    override fun listeners() {
        navigateBackListener()
        editUserDetails()
        cancelEditing()
        saveEditing()
        logOut()


        languageChange()

    }

    private fun displayUserDetails() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.userState.collectLatest { user ->
                    user?.let {
                        binding.etUsername.setText(it.username)
                        binding.etAge.setText(it.age.toString())
                        binding.etWeight.setText(it.weight.toString())
                        binding.etHeight.setText(it.height.toString())
                    }
                }
            }
        }
    }


    private fun editUserDetails() {
        binding.btnEdit.setOnClickListener {
            editingMode()
        }
    }

    private fun cancelEditing() {
        binding.btnCancel.setOnClickListener {
            exitEditingMode()
            displayUserDetails()
        }
    }

    private fun saveEditing() {
        binding.btnSave.setOnClickListener {
            if (!validateFields()) {
                return@setOnClickListener
            }

            val username = binding.etUsername.text.toString()
            val age = binding.etAge.text.toString().toInt()
            val weight = binding.etWeight.text.toString().toInt()
            val height = binding.etHeight.text.toString().toInt()

            profileViewModel.updateProfile(username, age, weight, height)
            editStateManagement()


        }
    }

    private fun editStateManagement() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                profileViewModel.profileState.collectLatest { state ->
                    when (state) {
                        is Resource.Loading -> {
                            loading()
                        }

                        is Resource.Success -> {
                            loaded()
                            binding.root.showSuccessSnackBar(getString(R.string.edit_successful))
                            exitEditingMode()
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

    private fun languageChange() {
        binding.buttonChangeLanguage.setOnClickListener {
            val currentLanguage = Locale.getDefault().language
            val newLanguage = if (currentLanguage == "en") "ka" else "en"

            profileViewModel.updateLanguage(newLanguage)

            requireActivity().recreate()
        }
    }

    private fun editingMode() {
        binding.btnEdit.visibility = View.GONE
        binding.btnSave.visibility = View.VISIBLE
        binding.btnCancel.visibility = View.VISIBLE

        binding.ageInputLayout.isEnabled = true
        binding.weightInputLayout.isEnabled = true
        binding.heightInputLayout.isEnabled = true
        binding.usernameInputLayout.isEnabled = true
    }

    private fun exitEditingMode() {
        binding.btnEdit.visibility = View.VISIBLE
        binding.btnSave.visibility = View.GONE
        binding.btnCancel.visibility = View.GONE

        binding.ageInputLayout.isEnabled = false
        binding.weightInputLayout.isEnabled = false
        binding.heightInputLayout.isEnabled = false
        binding.usernameInputLayout.isEnabled = false
    }


    private fun logOut() {
        binding.btnLogOut.setOnClickListener {
            lifecycleScope.launch {
                profileViewModel.logOut()
                findNavController().navigate(
                    ProfileFragmentDirections.actionProfileFragmentToLoginFragment(),
                    NavOptions.Builder()
                        .setPopUpTo(R.id.profileFragment, true)
                        .build()
                )
            }
        }

    }

    private fun loading() {
        binding.loading.visibility = View.VISIBLE
        binding.btnEdit.isEnabled = false
        binding.btnLogOut.isEnabled = false
        binding.ivBackIcon.isEnabled = false

    }

    private fun loaded() {
        binding.loading.visibility = View.GONE
        binding.btnEdit.isEnabled = true
        binding.btnLogOut.isEnabled = true
        binding.ivBackIcon.isEnabled = true
    }

    private fun navigateBackListener() {
        binding.ivBackIcon.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun validateFields(): Boolean {
        if (binding.etUsername.text.toString().isEmpty()) {
            binding.root.showErrorSnackBar(getString(R.string.username_should_not_be_empty))
            return false
        }
        if (binding.etUsername.text.toString().length < 3) {
            binding.root.showErrorSnackBar(getString(R.string.username_should_be_at_least_3_characters_long))
            return false
        }
        if (!binding.etUsername.text.toString().matches("^[a-zA-Z0-9_]*$".toRegex())) {
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
        if (binding.etHeight.text.toString().toInt() < 100 || binding.etHeight.text.toString()
                .toInt() > 210
        ) {
            binding.root.showErrorSnackBar(getString(R.string.height_should_be_valid_100_210))
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

        return true
    }


}