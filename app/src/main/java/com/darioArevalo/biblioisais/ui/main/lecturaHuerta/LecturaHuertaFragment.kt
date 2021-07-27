package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.data.remote.lecturahuerta.LecturaHuertaDataSource
import com.darioArevalo.biblioisais.databinding.ActivityMainBinding
import com.darioArevalo.biblioisais.databinding.FragmentLecturaHuertaBinding
import com.darioArevalo.biblioisais.domain.lecturahuerta.LecturaHuertaRepoImpl
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModel
import com.darioArevalo.biblioisais.presentation.LecturaHuertaViewModelFactory
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.LecturaHuertaAdapter
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.grpc.internal.SharedResourceHolder
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener


class LecturaHuertaFragment : Fragment(), LecturaHuertaAdapter.OnPostClickListener {
    private lateinit var Adapter: LecturaHuertaAdapter
    private lateinit var binding: FragmentLecturaHuertaBinding
    private val viewModel by viewModels<LecturaHuertaViewModel> { LecturaHuertaViewModelFactory(
        LecturaHuertaRepoImpl(LecturaHuertaDataSource())
    ) }

    private  var imageList = mutableListOf<CarouselItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Adapter = LecturaHuertaAdapter(ArrayList(),this)

    return inflater.inflate(R.layout.fragment_lectura_huerta, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLecturaHuertaBinding.bind(view)
        binding.carousel.visibility = View.VISIBLE

        binding.rvPostList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPostList.addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Result.Loading->{
                    Log.d("Livedata","Loading...")
                    binding.progressBarLecturaHuerta.visibility = View.VISIBLE
                }
                is Result.Success->{
                    binding.progressBarLecturaHuerta.visibility = View.GONE
                    Log.d("Livedata","${result.data}")

                    if(result.data.isEmpty()){
                        binding.emptyContainerLecturaHuerta.visibility = View.VISIBLE
                        return@Observer
                    }else{
                        binding.emptyContainerLecturaHuerta.visibility = View.GONE
                    }

                    Adapter = LecturaHuertaAdapter(result.data as ArrayList<PostServer>,this)
                    binding.rvPostList.adapter = Adapter
                }

                is Result.Failure->{
                    binding.progressBarLecturaHuerta.visibility = View.GONE
                    Log.d("livedata error","{${result.exception}}")
                }
            }

        })


        viewModel.fetchIsaisImages().observe(viewLifecycleOwner,{ result ->
            when(result){
                is Result.Loading -> {

                }
                is Result.Success -> {
                    for (image in result.data){
                        imageList.add(CarouselItem(image.imageUrl,image.review))
                    }
                    binding.carousel.addData(imageList)
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
                TODO("Not yet implemented")
            }
        }




        binding.searchView.imeOptions =EditorInfo.IME_ACTION_DONE
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.carousel.visibility = View.GONE
                if (query.isNullOrEmpty() or query.isNullOrBlank()){
                    binding.carousel.visibility = View.VISIBLE
                }else{
                    binding.carousel.visibility = View.GONE
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText.isNullOrEmpty() or newText.isNullOrBlank()){
                    binding.carousel.visibility = View.VISIBLE
                }else{
                    binding.carousel.visibility = View.GONE
                    Adapter.filter.filter(newText)
                }

                return false
            }
        })

        val fabButton = view.findViewById<ExtendedFloatingActionButton>(R.id.extended_fab)
        fabButton.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_agregarTemaFragment)
        }
    }

    override fun onPostClick(Post: PostServer) {
        val bundle = Bundle()
        bundle.putParcelable("post",Post)
        findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_detallesPostFragment,bundle)
    }
}