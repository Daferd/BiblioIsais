 package com.darioArevalo.biblioisais.ui.main.profile

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.core.hide
import com.darioArevalo.biblioisais.core.show
import com.darioArevalo.biblioisais.data.model.ImageBundle
import com.darioArevalo.biblioisais.data.model.UserServer
import com.darioArevalo.biblioisais.data.remote.profile.ProfileDataSource
import com.darioArevalo.biblioisais.databinding.FragmentProfileBinding
import com.darioArevalo.biblioisais.domain.profile.ProfileRepoImpl
import com.darioArevalo.biblioisais.presentation.profile.ProfileViewModel
import com.darioArevalo.biblioisais.presentation.profile.ProfileViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException



class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var bitmapGlobal : Bitmap
    private val REQUEST_IMAGE_CAPTURE = 1

    private val viewModel by viewModels<ProfileViewModel>{ ProfileViewModelFactory(
      ProfileRepoImpl(ProfileDataSource())
    ) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var data = UserServer("","","",false)

        binding = FragmentProfileBinding.bind(view)

        val user = FirebaseAuth.getInstance()

        if(user.uid != null){
            viewModel.fetchUser().observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.show()
                        binding.profileImageView.hide()
                        binding.cameraImageView.hide()
                        binding.emailCardView.hide()
                        binding.nameCardView.hide()
                        binding.passwordCardView.hide()
                        binding.logOutButton.hide()

                    }

                    is Result.Success -> {
                        binding.progressBar.hide()
                        binding.profileImageView.show()
                        binding.cameraImageView.show()
                        binding.emailCardView.show()
                        binding.nameCardView.show()
                        binding.passwordCardView.show()
                        binding.logOutButton.show()
                        binding.nameTextView.text = result.data.username
                        binding.emailTextView.text = result.data.email
                        binding.passwordTextView.text = "******"
                        Glide.with(this).load(result.data.photo_url).centerCrop()
                            .into(binding.profileImageView)
                        data = result.data
                    }
                    is Result.Failure -> {

                    }
                }
            }

            binding.cameraImageView.setOnClickListener {
                uploadImage()
            }

            binding.logOutButton.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                findNavController().navigate(R.id.action_navigation_profile_to_navigation_biblioisais)
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
            binding.profileImageView.setOnClickListener {
                val bundle = Bundle()
                val imagepass = ImageBundle(bitmap_string = data.photo_url)
                bundle.putParcelable("img_view_detalles",imagepass)
                findNavController().navigate(R.id.action_navigation_profile_to_imageviewFragment,bundle)
            }

        } else {
            /*binding.imageContainer.hide()
            binding.cameraImageView.hide()
            binding.nameCardView.hide()
            binding.emailCardView.hide()
            binding.passwordCardView.hide()*/

            binding.passwordTextView.text = ""

            binding.logOutButton.text = "Iniciar Sesión"
            binding.logOutButton.setOnClickListener{
                findNavController().navigate(R.id.action_navigation_profile_to_loginFragment)
            }

        }



    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImageGallery()
                }else{
                    Toast.makeText(context,"Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun chooseImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),
            IMAGE_CHOOSE
        )//intent, IMAGE_CHOOSE)
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

        if(requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK){
            binding.profileImageView.setImageURI(data?.data)
            val imgBitmap = data?.data

            try {
                @Suppress("DEPRECATION")
                bitmapGlobal = MediaStore.Images.Media.getBitmap(context?.contentResolver,imgBitmap)
                binding.profileImageView.setImageBitmap(bitmapGlobal)
                uploadPicture(bitmapGlobal)

            }catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }

    private fun uploadPicture(imageBitmap: Bitmap){
            val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Guardando foto...").create()
        imageBitmap.let {
            viewModel.updatePictureProfile(imageBitmap = it).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        alertDialog.show()
                    }
                    is Result.Success -> {
                        alertDialog.dismiss()
                        Toast.makeText(context, "Foto guardada", Toast.LENGTH_SHORT).show()
                    }
                    is Result.Failure -> {
                        alertDialog.dismiss()
                    }
                }

            }
        }

    }

    private fun uploadImage() {
        val alertOpciones = AlertDialog.Builder(context)
        alertOpciones.setTitle("Seleccione una opción:")
        alertOpciones.setPositiveButton("Tomar foto") { dialogInterface: DialogInterface, i: Int ->
            dispatchTakePictureIntent()
        }
        alertOpciones.setNegativeButton("Cargar imagen") { dialogInterface: DialogInterface, i: Int ->
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (activity?.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)   == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                }else{
                    chooseImageGallery()

                }
            }else{
                chooseImageGallery()
            }
        }
        alertOpciones.show()

    }

    companion object {
        private const val IMAGE_CHOOSE = 1000;
        private const val PERMISSION_CODE = 1001;
    }


}