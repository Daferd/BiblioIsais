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
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
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
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class BibliotecasFragment : Fragment(R.layout.fragment_bibliotecas), PdfsAdapter.OnPdfClickListener {

    //private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentBibliotecasBinding


    private val viewModel by viewModels<ProductsViewModel>{ ProductsViewModelFactory(
            ProductsRepoImpl(ProductsDataSource())
    ) }

    private val imageList = mutableListOf<CarouselItem>()

    private lateinit var storageReference: StorageReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBibliotecasBinding.bind(view)

        viewModel.fetchIsaisImages().observe(viewLifecycleOwner,{ result->
            when(result){
                is Result.Loading -> {

                }
                is Result.Success -> {

                    for (image in result.data){
                        imageList.add(CarouselItem(image.imageUrl,image.review))
                    }

                }
                is Result.Failure -> {

                }
            }

        })

        viewModel.fetchPdf().observe(viewLifecycleOwner,{ result->
            when(result){
                is Result.Loading ->{

                }
                is Result.Success -> {
                    binding.pdfRecyclerView.adapter = PdfsAdapter(result.data,this)
                }
                is Result.Failure -> {

                }
            }
        })



        binding.carousel.onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                Toast.makeText(context,"Auto: ${carouselItem.caption}",Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
            }

        }



    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.carousel.addData(imageList)

        val db = FirebaseFirestore.getInstance()
        db.collection("ciudades").document("LA").get().addOnSuccessListener { document ->
            document?.let {
                Log.d("Firestore","DocumentSnapShot data:${document.data}" )
            }
        }

        binding.btnTakePicture.setOnClickListener{
            dispatchTakePictureIntent()
        }

    }*/
/*
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_bibliotecas, container,false)
        val carousel = view.findViewById<ImageCarousel>(R.id.carousel)
        val btn_pdf = view.findViewById<Button>(R.id.pdf_button)
        val recyclerPdf = view.findViewById<RecyclerView>(R.id.pdf_recycler_view)

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

        btn_pdf.setOnClickListener {

            var islandRef = storageReference.child("isaisImages/20210316_143909.jpg")

            val ONE_MEGABYTE: Long = 1024 * 1024
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {
                // Data for "images/island.jpg" is returned, use this as needed
            }.addOnFailureListener {
                // Handle any errors
            }

            /*val localFile = File.createTempFile("PDFs","pdf")

            islandRef.getFile(localFile).addOnSuccessListener {
                Toast.makeText(context,"yes",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
            }*/
        }

        return view
    }*/


    override  fun onPdfClick(pdf: PdfServer) {

        Log.d("pdf_listener_url","${pdf.pdfUrl}")
        viewModel.fetchDownloadPDF(pdf.pdfUrl)

        /*val httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(pdf.pdfUrl)


        val localFile = File.createTempFile("document","pdf")

        httpsReference.downloadUrl.addOnSuccessListener {
            Log.d("DescargaOk","pdf ok")


        }.addOnFailureListener{
            Log.d("descargaOFF","failure pdf")
        }*/

/*
        val islandRef = storageReference.child("PDFs/Cedula.pdf")

        val localFile = File.createTempFile("PDFs","pdf")

        islandRef.getFile(localFile).addOnSuccessListener {
            Toast.makeText(context,"yes",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
        }*/



    }

    companion object{
        private lateinit var url: URL
        private lateinit var urlx: URL
        private lateinit var httpURLConnection: HttpURLConnection
        private lateinit var inputStream: InputStream
    }
}

