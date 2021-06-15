package com.darioArevalo.biblioisais.ui.main.bibliotecas

import android.Manifest
import android.app.Activity.DOWNLOAD_SERVICE
import android.app.Activity.RESULT_OK
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PdfServer
import com.darioArevalo.biblioisais.data.remote.products.ProductsDataSource
import com.darioArevalo.biblioisais.databinding.FragmentBibliotecasBinding
import com.darioArevalo.biblioisais.domain.products.ProductsRepoImpl
import com.darioArevalo.biblioisais.presentation.products.ProductsViewModel
import com.darioArevalo.biblioisais.presentation.products.ProductsViewModelFactory
import com.darioArevalo.biblioisais.ui.main.bibliotecas.adapters.PdfsAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*


class BibliotecasFragment : Fragment(R.layout.fragment_bibliotecas), PdfsAdapter.OnPdfClickListener {

    //private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentBibliotecasBinding

    private val viewModel by viewModels<ProductsViewModel>{ ProductsViewModelFactory(
            ProductsRepoImpl(ProductsDataSource())
    ) }

    private val imageList = mutableListOf<CarouselItem>()

    private lateinit var storageReference: StorageReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_bibliotecas, container,false)
        val carousel = view.findViewById<ImageCarousel>(R.id.carousel)
        val recyclerPdf = view.findViewById<RecyclerView>(R.id.pdf_recycler_view)
        val mToolbar = view.findViewById<Toolbar>(R.id.isais_toolbar)

        mToolbar.visibility
        mToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_biblioteca_to_navigation_bibliomundo)
        }

        activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        activity?.actionBar?.hide()

        viewModel.fetchIsaisImages().observe(viewLifecycleOwner,{ result ->
            when(result){
                is Result.Loading -> {

                }
                is Result.Success -> {
                    for (image in result.data){
                        imageList.add(CarouselItem(image.imageUrl,image.review))
                    }
                    carousel.addData(imageList)
                    //imageList.add(CarouselItem(result.data[0].imageUrl,result.data[0].name))
                    //Log.d("imagenes","info : ${result.data[0].imageUrl}")

                }
                is Result.Failure -> {

                }
            }
        })

        carousel.onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                Toast.makeText(context,"Auto: ${carouselItem.caption}",Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                TODO("Not yet implemented")
            }

        }

        viewModel.fetchPdf().observe(viewLifecycleOwner,{result->
            when(result){
                is Result.Loading -> {

                }
                is Result.Success -> {
                    recyclerPdf.adapter = PdfsAdapter(result.data,this@BibliotecasFragment)
                }
                is Result.Failure -> {
                    Toast.makeText(context,"error",Toast.LENGTH_SHORT).show()
                }
            }
        })

        return view
    }

    override fun onPdfClick(pdf: PdfServer) {
        val islandRef = storageReference.child("PDFs/Cedula.pdf")
        val localFile = File.createTempFile("PDFs","pdf")

        islandRef.getFile(localFile).addOnSuccessListener {
            Toast.makeText(context,"yes",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
        }
    }
}

