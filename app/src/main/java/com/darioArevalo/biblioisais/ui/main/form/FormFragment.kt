package com.darioArevalo.biblioisais.ui.main.form

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.remote.form.FormDataSource
import com.darioArevalo.biblioisais.databinding.FragmentFormBinding
import com.darioArevalo.biblioisais.domain.form.FormRepoImpl
import com.darioArevalo.biblioisais.presentation.form.FormViewModel
import com.darioArevalo.biblioisais.presentation.form.FormViewModelFactory

class FormFragment : Fragment(R.layout.fragment_form) {

    private lateinit var binding: FragmentFormBinding
    val args: FormFragmentArgs by navArgs()

    private val viewModel by viewModels<FormViewModel>{
        FormViewModelFactory(
            FormRepoImpl(
                FormDataSource()
            )
        )
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFormBinding.bind(view)

        sendForm()
    }

    private fun sendForm() {
        binding.btnSendForm.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val age = binding.editTextEdad.text.toString().trim()
            val phoneNumber = binding.editTextPhoneNumber.text.toString().trim()

            validateFormData(username,email,age,phoneNumber)

            createForm(username,email,age,phoneNumber)
        }
    }

    private fun createForm(username: String, email: String, age: String, phoneNumber: String) {
        viewModel.sendForm(username,email,age,phoneNumber).observe(viewLifecycleOwner,{ result ->
            when(result){
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSendForm.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(),"Se guardo formulario",Toast.LENGTH_SHORT).show()
                    val webIntent : Intent = Uri.parse(args.selectedCourse.courseUrl).let { webpage ->
                        Intent(Intent.ACTION_VIEW,webpage)
                    }
                    startActivity(webIntent)
                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSendForm.isEnabled = true
                    Toast.makeText(requireContext(),"Error: ${result.exception}",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateFormData(
        username: String,
        email: String,
        age: String,
        phoneNumber: String
    ): Boolean {
        if (username.isEmpty()) {
            binding.editTextUsername.error = "Username is empty"
            return true
        }
        if (email.isEmpty()) {
            binding.editTextEmail.error = "email is empty"
            return true
        }
        if (age.isEmpty()) {
            binding.editTextEdad.error = "Password is empty"
            return true
        }
        if (phoneNumber.isEmpty()) {
            binding.editTextPhoneNumber.error = "Confirm password is empty"
            return true
        }

        return false
    }
}