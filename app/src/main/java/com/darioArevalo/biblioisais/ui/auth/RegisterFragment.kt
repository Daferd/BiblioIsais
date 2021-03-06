package com.darioArevalo.biblioisais.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.remote.auth.AuthDataSource
import com.darioArevalo.biblioisais.databinding.FragmentRegisterBinding
import com.darioArevalo.biblioisais.domain.auth.AuthRepoImpl
import com.darioArevalo.biblioisais.presentation.auth.AuthViewModel
import com.darioArevalo.biblioisais.presentation.auth.AuthViewModelFactory


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel>{
        AuthViewModelFactory(
            AuthRepoImpl(
                AuthDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
        singUp()
    }

    private fun singUp(){

        binding.btnSignup.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val form = false

            if (validateUserData(username,email,password,confirmPassword)) return@setOnClickListener

            createUser(email,password,username,form)
        }
    }

    private fun createUser(email: String, password: String, username: String, form: Boolean) {
        viewModel.signUp(email,password,username,form).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context,"Actualiza tu foto de perfil",Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_registerFragment_to_navigation_profile)
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(requireContext(),"Error: ${result.exception}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateUserData(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }
        if (email.isEmpty()) {
            binding.editTextEmail.error = "email is empty"
            return true
        }
        if (password.isEmpty()) {
            binding.editTextPassword.error = "Password is empty"
            return true
        }
        if (confirmPassword.isEmpty()) {
            binding.editTextConfirmPassword.error = "Confirm password is empty"
            return true
        }

        if (password != confirmPassword) {
            binding.editTextPassword.error = "Password does not match"
            binding.editTextConfirmPassword.error = "Password does not match"
            return true
        }
        return false
    }
}