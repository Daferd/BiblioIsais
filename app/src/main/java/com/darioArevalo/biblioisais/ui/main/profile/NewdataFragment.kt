package com.darioArevalo.biblioisais.ui.main.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.remote.profile.ProfileDataSource
import com.darioArevalo.biblioisais.databinding.FragmentNewdataBinding
import com.darioArevalo.biblioisais.domain.profile.ProfileRepoImpl
import com.darioArevalo.biblioisais.presentation.profile.ProfileViewModel
import com.darioArevalo.biblioisais.presentation.profile.ProfileViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth


class NewdataFragment : DialogFragment() {

    private lateinit var binding: FragmentNewdataBinding
    private val args: NewdataFragmentArgs by navArgs()

    private val viewModel by viewModels<ProfileViewModel>{ ProfileViewModelFactory(
            ProfileRepoImpl(ProfileDataSource())
    ) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_newdata, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentNewdataBinding.bind(view)

        when (args.bandData) {
            1 -> {
                binding.dataTextInputLayout.hint = "Nombre actual"
                binding.dataEditText.setText(args.userData?.username)
                binding.dataEditText.isEnabled = false
                binding.newdataTextInputLayout.hint = "Nuevo nombre"
                binding.acceptButton.setOnClickListener {
                    uploadName()
                }

            }
            2 -> {
                binding.dataTextInputLayout.hint = "Correo actual"
                binding.dataEditText.setText(args.userData?.email)
                binding.dataEditText.isEnabled = false
                binding.newdataTextInputLayout.hint = "Nuevo email"
                binding.acceptButton.setOnClickListener {
                    uploadEmail()
                }

            }
            else -> {
                binding.dataTextInputLayout.hint = "Nueva Contraseña"
                binding.newdataTextInputLayout.hint = "Repita nueva contraseña"
                binding.dataTextInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                binding.newdataTextInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
                binding.dataEditText.inputType = 128
                binding.newdataEditText.inputType = 128
                binding.acceptButton.setOnClickListener {
                    val password = binding.dataEditText.text.toString().trim()
                    val confirmPassword = binding.newdataEditText.text.toString().trim()
                    if(verifyPassword(password,confirmPassword)) return@setOnClickListener

                    uploadPassword()

                    //uploadPassword()
                }
            }
        }

        binding.cancelButton.setOnClickListener{
            dismiss()
        }
    }

    private fun verifyPassword(password: String, confirmPassword: String): Boolean {
        if (password.isEmpty()) {
            binding.dataEditText.error = "Password is empty"
            return true
        }
        if (confirmPassword.isEmpty()) {
            binding.newdataEditText.error = "Confirm password is empty"
            return true
        }
        if(password.length < 6){
            binding.dataEditText.error = "La contraseña debe contener minimo 6 caracteres"
            return true
        }
        if(password!=confirmPassword){
            binding.dataEditText.error="Las contraseñas no coinciden"
            binding.newdataEditText.error="Las contraseñas no coinciden"
            return true
        }
        return false
    }
    private fun uploadPassword() {
        val newPassword = binding.newdataEditText.text.toString().trim()
        val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Guardando nueva contraseña...").create()
        //val user = FirebaseAuth.getInstance().currentUser
        viewModel.updatePassword(newPassword).observe(viewLifecycleOwner,{  result ->
            when(result){
                is Result.Loading -> { alertDialog.show()}
                is Result.Success -> {
                    alertDialog.dismiss()
                    dismiss()
                    Toast.makeText(context,"Contraseña actualizada",Toast.LENGTH_SHORT).show()
                }
                is Result.Failure -> {
                    alertDialog.dismiss()
                    dismiss()
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun uploadEmail() {
        val newEmail = binding.newdataEditText.text.toString().trim()
        val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Guardando nuevo correo...").create()
        viewModel.updateEmail(newEmail).observe(viewLifecycleOwner,{ result ->
            when(result){
                is Result.Loading -> {alertDialog.show()}
                is Result.Success -> {
                    alertDialog.dismiss()
                    dismiss()
                    FirebaseAuth.getInstance().signOut()
                    findNavController().navigate(R.id.action_newdataFragment_to_loginFragment)
                    Toast.makeText(context,"Nuevo correo guardado, verificar en el correo",Toast.LENGTH_SHORT).show()
                }
                is Result.Failure -> {
                    alertDialog.dismiss()
                    dismiss()
                    Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun uploadName() {
        val newUsername = binding.newdataEditText.text.toString().trim()
        val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Guardando nuevo nombre...").create()
        viewModel.updateUsername(newUsername).observe(viewLifecycleOwner,{ result ->
            when(result){
                is Result.Loading -> {alertDialog.show()}
                is Result.Success -> {
                    alertDialog.dismiss()
                    dismiss()
                    Toast.makeText(context,"Nuevo nombre guardado",Toast.LENGTH_SHORT).show()
                }
                is Result.Failure -> {
                    alertDialog.dismiss()
                }
            }
        })
    }

}