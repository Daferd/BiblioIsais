package com.darioArevalo.biblioisais.ui.main.biblioteca_isais

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.core.hide
import com.darioArevalo.biblioisais.core.show
import com.darioArevalo.biblioisais.data.model.ImageBundle
import com.darioArevalo.biblioisais.data.model.PdfServer
import com.darioArevalo.biblioisais.data.remote.biblioteca_isais.BibliotecaIsaisDataSource
import com.darioArevalo.biblioisais.databinding.FragmentBibliotecasBinding
import com.darioArevalo.biblioisais.domain.biblioteca_isais.BibliotecaIsaisRepoImpl
import com.darioArevalo.biblioisais.presentation.biblioteca_isais.BibliotecaIsaisViewModel
import com.darioArevalo.biblioisais.presentation.biblioteca_isais.BibliotecaIsaisViewModelFactory
import com.darioArevalo.biblioisais.ui.main.biblioteca_isais.adapters.PdfsAdapter
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener


class BibliotecaIsaisFragment : Fragment(R.layout.fragment_bibliotecas), PdfsAdapter.OnPdfClickListener {

    private lateinit var binding: FragmentBibliotecasBinding

    private val viewModel by viewModels<BibliotecaIsaisViewModel>{ BibliotecaIsaisViewModelFactory(
            BibliotecaIsaisRepoImpl(BibliotecaIsaisDataSource())
    ) }

    private val imageList = mutableListOf<CarouselItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_bibliotecas, container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentBibliotecasBinding.bind(view)
        binding.pdfRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.pdfRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))


        viewModel.fetchIsaisImages().observe(viewLifecycleOwner,{ result ->
            when(result){
                is Result.Loading -> {

                }
                is Result.Success -> {
                    imageList.clear()
                    for (image in result.data){
                        imageList.add(CarouselItem(image.imageUrl,image.review))
                    }
                    binding.carousel.addData(imageList)
                }
                is Result.Failure -> {

                }
            }
        })

        viewModel.fetchPdf().observe(viewLifecycleOwner,{ result->
            when(result){
                is Result.Loading ->{
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    binding.pdfRecyclerView.adapter = PdfsAdapter(result.data,this)
                }
                is Result.Failure -> {

                }
            }
        })

        binding.carousel.onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                val bundle = Bundle()
                val imagepass = ImageBundle(bitmap_string = carouselItem.imageUrl.toString())
                bundle.putParcelable("img_view_detalles",imagepass)
                findNavController().navigate(R.id.action_navigation_biblioteca_to_imageviewFragment,bundle)
            }
            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                Log.d("pdf_caption","${dataObject.caption}")
            }
        }
    }

    override fun onPdfClick(pdf: PdfServer) {
        viewModel.fetchDownloadPDF(pdf.pdfUrl, requireContext())
    }

}

