 package com.darioArevalo.biblioisais.ui.main.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.UserServer
import com.darioArevalo.biblioisais.data.remote.profile.ProfileDataSource
import com.darioArevalo.biblioisais.databinding.FragmentProfileBinding
import com.darioArevalo.biblioisais.domain.profile.ProfileRepoImpl
import com.darioArevalo.biblioisais.presentation.profile.ProfileViewModel
import com.darioArevalo.biblioisais.presentation.profile.ProfileViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val REQUEST_IMAGE_CAPTURE = 1

    private val viewModel by viewModels<ProfileViewModel>{ ProfileViewModelFactory(
      ProfileRepoImpl(ProfileDataSource())
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = UserServer("","","",false)

        binding = FragmentProfileBinding.bind(view)

        viewModel.fetchUser().observe(viewLifecycleOwner,{  result->
            when(result){
                is Result.Loading -> {

                }

                is Result.Success -> {
                    binding.nameTextView.text = result.data.username
                    binding.emailTextView.text = result.data.email
                    binding.passwordTextView.text = "******"
                    Glide.with(this).load(result.data.photo_url).centerCrop().into(binding.profileImageView)
                    data = result.data
                }
                is Result.Failure -> {

                }
            }
        })

        binding.cameraImageView.setOnClickListener {
            uploadImage()

        }

        binding.logOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_navigation_profile_to_loginFragment)
        }
        binding.nameCardView.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToNewdataFragment(data,1)
            findNavController().navigate(action)
        }
        binding.emailCardView.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToNewdataFragment(data,2)
            findNavController().navigate(action)
        }

        binding.passwordCardView.setOnClickListener {
            val action = ProfileFragmentDirections.actionNavigationProfileToNewdataFragment(data,3)
            findNavController().navigate(action)
        }

    }

    private fun uploadImage() {
        val alertOpciones = AlertDialog.Builder(context)
        alertOpciones.setTitle("Seleccione una opción:")
        alertOpciones.setPositiveButton("Tomar foto") { dialogInterface: DialogInterface, i: Int ->
            dispatchTakePictureIntent()
        }
        alertOpciones.setNegativeButton("Cargar imagen") { dialogInterface: DialogInterface, i: Int ->
            /* val intent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
             intent.setType("image/")
             band = true
             startActivityForResult(intent,10)*/
            Toast.makeText(context, "cargar foto", Toast.LENGTH_SHORT).show()
        }
        alertOpciones.show()
    }

    private fun dispatchTakePictureIntent(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(context,"No se encontro app para tomar la foto", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.profileImageView.setImageBitmap(imageBitmap)
            uploadPicture(imageBitmap)
        }
    }

    private fun uploadPicture(imageBitmap: Bitmap){
            val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Guardando foto...").create()
        imageBitmap?.let {
            viewModel.updatePictureProfile(imageBitmap = it).observe(viewLifecycleOwner,{   result ->
                when(result){
                    is Result.Loading -> {
                        alertDialog.show()
                    }
                    is Result.Success -> {
                        alertDialog.dismiss()
                        Toast.makeText(context,"Foto guardada",Toast.LENGTH_SHORT).show()
                    }
                    is Result.Failure -> {
                        alertDialog.dismiss()
                    }
                }

            })
        }

    }


}