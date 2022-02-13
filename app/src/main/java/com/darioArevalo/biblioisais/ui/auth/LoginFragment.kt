package com.darioArevalo.biblioisais.ui.auth

import android.app.ActionBar
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.core.hide
import com.darioArevalo.biblioisais.core.show
import com.darioArevalo.biblioisais.data.remote.auth.AuthDataSource
import com.darioArevalo.biblioisais.databinding.FragmentLoginBinding
import com.darioArevalo.biblioisais.domain.auth.AuthRepoImpl
import com.darioArevalo.biblioisais.presentation.auth.AuthViewModel
import com.darioArevalo.biblioisais.presentation.auth.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel>{AuthViewModelFactory(AuthRepoImpl(AuthDataSource()))}
    //private lateinit var acyionbar : ActionBar
    val args: LoginFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        //isUserLoggedIn()
        doLogin()
        goToSingUpPage()
        recoverPassword()

        //error de navegacio

        /*val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                //activity?.finish()
                val fm = activity?.supportFragmentManager
                fm?.popBackStack();
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)*/
    }

    private fun isUserLoggedIn() {
        val user = FirebaseAuth.getInstance().currentUser

        /*user?.let {
            if (user.isEmailVerified){
                findNavController().navigate(R.id.action_loginFragment_to_navigation_biblioisais)
            }
        }*/
        firebaseAuth.currentUser?.let {

            findNavController().navigate(R.id.action_loginFragment_to_navigation_biblioisais)
        }
    }
    private fun doLogin() {
        binding.btnSignin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(email, password)
            signIn(email, password)
        }
    }
    private fun goToSingUpPage(){
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
    private fun recoverPassword() {

        binding.txtOlvidaste.setOnClickListener {

            val alertOptions = AlertDialog.Builder(context)
                alertOptions.setTitle("¿Olvidaste tu contraseña?")
                alertOptions.setPositiveButton("Enviar correo") { dialogInterface: DialogInterface, i: Int ->
                    val email = binding.editTextEmail.text.toString().trim()
                    if (email.isEmpty()){
                        binding.editTextEmail.error = "Escriba un correo"
                        Toast.makeText(context,"Escriba un correo de recuperación",Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.recoverPassword(email).observe(viewLifecycleOwner,{result->
                            when(result){
                                is Result.Success -> {
                                    Toast.makeText(context,"Se ha enviado un correo de recuperación",Toast.LENGTH_SHORT).show()
                                }
                                is Result.Failure -> {
                                    Toast.makeText(context,"Error al enviar correo",Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                    }
                }

                alertOptions.show()

        }

    }

    private fun validateCredentials(email: String, password: String) {
        if(email.isEmpty()){
            binding.editTextEmail.error = "E-mail is empty"
            return
        }

        if(password.isEmpty()){
            binding.editTextPassword.error = "Password is empty"
            return
        }
    }
    private fun signIn(email: String, password: String) {
        viewModel.signIn(email,password).observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                    binding.btnSignin.isEnabled = false
                }
                is Result.Success -> {
                    binding.progressBar.hide()

                    findNavController().navigate(R.id.action_loginFragment_to_navigation_biblioisais)
                    /*(args.direccion == "agregarTema"){
                        findNavController().navigate(R.id.action_loginFragment_to_agregarTemaFragment)
                        Toast.makeText(
                            requireContext(),
                            "Welcome ${result.data?.displayName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        findNavController().navigate(R.id.action_loginFragment_to_detallesPostFragment)
                        Toast.makeText(
                            requireContext(),
                            "Welcome ${result.data?.displayName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }*/

                    /*if(args.direccion == "comentar"){
                        findNavController().navigate(R.id.action_loginFragment_to_detallesPostFragment)
                        Toast.makeText(
                            requireContext(),
                            "Welcome ${result.data?.displayName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }*/

                    /*if(!result.data?.isEmailVerified!!){
                        Toast.makeText(context,"Correo electrónico no verificado",Toast.LENGTH_SHORT).show()
                        binding.btnSignin.isEnabled = true
                    }else{
                        findNavController().navigate(R.id.action_loginFragment_to_navigation_biblioisais)
                        Toast.makeText(
                            requireContext(),
                            "Welcome ${result.data?.displayName}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }*/

                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(
                            requireContext(),
                            "Error: ${result.exception}",
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}