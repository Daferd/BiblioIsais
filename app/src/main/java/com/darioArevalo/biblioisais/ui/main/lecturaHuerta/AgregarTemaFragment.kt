package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri //revisar
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Scroller
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource
import com.darioArevalo.biblioisais.databinding.FragmentAgregarTemaBinding
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepoImpl
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModel
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModelFactory
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AgregarTemaFragment : Fragment(R.layout.fragment_agregar_tema) {

    private lateinit var binding: FragmentAgregarTemaBinding
    private lateinit var bitmapGlobal : Bitmap
    private  val REQUEST_IMAGE_CAPTURE = 1
    private var filePath: Uri? = null
    private val viewModel by viewModels<LecturaHuertaViewModel> {
        LecturaHuertaViewModelFactory(LecturaHuertaRepoImpl(LecturaHuertaDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAgregarTemaBinding.bind(view)

        binding.cameraImageViewAddPhotoPost.setOnClickListener {
            showCustomAlert()
        }
        binding.cameraDeletePhotoPost.setOnClickListener {
            binding.imgVwPhotoPost.setImageResource(R.drawable.ic_baseline_insert_photo_500)
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
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), IMAGE_CHOOSE)//intent, IMAGE_CHOOSE)
    }

    private fun newPost(date:String){
        binding.editTextContenido.setScroller(Scroller(context))
        binding.editTextBTN.setOnClickListener {
            val autor = "Autor x"//binding.editTextAutor.text.toString().trim()
            val contenido = binding.editTextContenido.text.toString().trim()
            val titulo = binding.editTextTitulo.text.toString().trim()

            if (TextUtils.isEmpty(contenido) || TextUtils.isEmpty(titulo)){
                Toast.makeText(context,"Por Favor Llene Los Espacios",Toast.LENGTH_SHORT).show()

            }else{


                viewModel.setearNewPost(autor,contenido,titulo,date,bitmapGlobal)
                binding.editTextContenido.setText("")
                binding.editTextTitulo.setText("")
                Toast.makeText(context,"Has Comentado",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_agregarTemaFragment_to_navigation_lecturaHuerta)
            }


        }
    }

    private fun dispachTakePictureIntent(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
        }catch (e:ActivityNotFoundException){

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

            try {
                bitmapGlobal = MediaStore.Images.Media.getBitmap(context?.contentResolver,imgBitmap)
                binding.imgVwPhotoPost.setImageBitmap(bitmapGlobal)

            }catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun showCustomAlert() {
        val dialogView = layoutInflater.inflate(R.layout.alertdialog_custom_row, null)
        val customDialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .show()
        val btDismiss = dialogView.findViewById<ImageView>(R.id.ImgVw_CancelCustomAlertDialog)
        btDismiss.setOnClickListener {
            customDialog.dismiss()
        }
        val btUploadPhoto = dialogView.findViewById<ImageView>(R.id.ImgVw_UploadCustomAlertDialog)
        btUploadPhoto.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (activity?.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)   == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission,PERMISSION_CODE)
                }else{
                    chooseImageGallery()
                    customDialog.dismiss()
                }
            }else{
                chooseImageGallery()
                customDialog.dismiss()
            }

        }

        val btTakePhoto = dialogView.findViewById<ImageView>(R.id.ImgVw_TakePhotoCustomAlertDialog)
        btTakePhoto.setOnClickListener {
            dispachTakePictureIntent()
            customDialog.dismiss()
        }


    }



    companion object {
        private val IMAGE_CHOOSE = 1000;
        private val PERMISSION_CODE = 1001;
    }

}
