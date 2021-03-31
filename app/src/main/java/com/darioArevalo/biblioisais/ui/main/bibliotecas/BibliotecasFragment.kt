package com.darioArevalo.biblioisais.ui.main.bibliotecas

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.databinding.FragmentBibliotecasBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*

class BibliotecasFragment : Fragment(R.layout.fragment_bibliotecas) {

    //private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentBibliotecasBinding
    private val REQUEST_IMAGE_CAPTURE = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBibliotecasBinding.bind(view)

        val db = FirebaseFirestore.getInstance()
        db.collection("ciudades").document("LA").get().addOnSuccessListener { document ->
            document?.let {
                Log.d("Firestore","DocumentSnapShot data:${document.data}" )
            }
        }

        /*binding.btnTakePicture.setOnClickListener{
            dispatchTakePictureIntent()
        }*/

    }

    private fun dispatchTakePictureIntent(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
        }catch (e: ActivityNotFoundException){
            Toast.makeText(context,"No se encontro app para tomar la foto",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap
            //binding.imageView.setImageBitmap(imageBitmap)
            uploadPicture(imageBitmap)
        }
    }

    private fun uploadPicture(bitmap: Bitmap){
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef= storageRef.child("imagenes/${UUID.randomUUID()}.jpg")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos)
        val data = baos.toByteArray()
        val uploadTask = imagesRef.putBytes(data)

        uploadTask.continueWithTask { task ->
            if(!task.isSuccessful){
                task.exception?.let { exception ->
                    throw exception
                }
            }
            imagesRef.downloadUrl
        }.addOnCompleteListener{ task->
            if(task.isSuccessful){
                val downloadUrl = task.result.toString()
                FirebaseFirestore.getInstance().collection("ciudades").document("LA").update(mapOf("imageUrl" to downloadUrl))
                Log.d("Storage","uploadPictureUrl: $downloadUrl")
            }
        }
    }
}

