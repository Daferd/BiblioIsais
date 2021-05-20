package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Scroller
import android.widget.Toast
import androidx.core.graphics.createBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource
import com.darioArevalo.biblioisais.databinding.FragmentAgregarTemaBinding
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepoImpl
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModel
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModelFactory
import java.io.File
import java.text.SimpleDateFormat

import java.util.*
import java.util.jar.Manifest


class AgregarTemaFragment : Fragment(R.layout.fragment_agregar_tema) {

    private lateinit var binding: FragmentAgregarTemaBinding
    private lateinit var filePhoto: File
    private lateinit var bitmapGlobal : Bitmap
    private  val REQUEST_IMAGE_CAPTURE = 1

    private val viewModel by viewModels<LecturaHuertaViewModel> {
        LecturaHuertaViewModelFactory(LecturaHuertaRepoImpl(LecturaHuertaDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAgregarTemaBinding.bind(view)



        binding.btnTakePhotoNewPost.setOnClickListener {
            dispachTakePictureIntent()
        }

        binding.btnUploadPhotoNewPost.setOnClickListener {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (activity?.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)   == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission,PERMISSION_CODE)
                }else{
                    chooseImageGallery()
                }
            }else{
                chooseImageGallery()
            }
        }

        val dateNow = Calendar.getInstance().time
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val date = sdf.format(dateNow)
        binding.postTimeAgregarTema.text = date.toString()
        newPost(dateNow.toString())

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
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    private fun newPost(date:String){
        binding.editTextContenido.setScroller(Scroller(context))
        binding.editTextBTN.setOnClickListener {
            val autor = binding.editTextAutor.text.toString().trim()
            val contenido = binding.editTextContenido.text.toString().trim()
            val titulo = binding.editTextTitulo.text.toString().trim()


            viewModel.setearNewPost(autor,contenido,titulo,date,bitmapGlobal)

            findNavController().navigate(R.id.action_agregarTemaFragment_to_navigation_lecturaHuerta)
        }
    }

    private fun dispachTakePictureIntent(){
        binding.btnTakePhotoNewPost.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
            }catch (e:ActivityNotFoundException){

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK ){
            val imageBitmap= data?.extras?.get("data") as Bitmap
            binding.imgVwPhotoPost.setImageBitmap(imageBitmap)

            bitmapGlobal = imageBitmap

        }

        if(requestCode == IMAGE_CHOOSE && resultCode == Activity.RESULT_OK){
            binding.imgVwPhotoPost.setImageURI(data?.data)
            val imgBitmap = data?.data
           // Log.d("entro","$data?.data")
            //bitmapGlobal = imgBitmap
        }
    }



    companion object {
        private const val FILE_NAME = "photo.jpg"
        private const val REQUEST_CODE = 13
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }

}
