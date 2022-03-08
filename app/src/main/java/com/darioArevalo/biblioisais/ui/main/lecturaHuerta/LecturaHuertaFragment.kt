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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.core.hide
import com.darioArevalo.biblioisais.core.show
import com.darioArevalo.biblioisais.data.model.ImageBundle
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
import com.google.firebase.auth.FirebaseAuth
import io.grpc.internal.SharedResourceHolder
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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



/*
        viewModel.fetchLatestPosts()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getPost().collect{result->
                    when(result){
                        is Result.Loading->{
                            Log.d("Livedata","Loading...")
                            binding.progressBarLecturaHuerta.show()
                            binding.carousel.hide()
                            binding.descripcionCardview.hide()
                        }
                        is Result.Success->{
                            binding.progressBarLecturaHuerta.hide()
                            binding.carousel.show()
                            binding.descripcionCardview.show()
                            Log.d("Livedata","${result.data}")

                            if(result.data.isEmpty()){
                                binding.emptyContainerLecturaHuerta.show()
                                return@collect
                            }else{
                                binding.emptyContainerLecturaHuerta.hide()
                            }


                            Adapter = LecturaHuertaAdapter(result.data as ArrayList<PostServer>,this@LecturaHuertaFragment)
                            binding.rvPostList.adapter = Adapter
                        }

                        is Result.Failure->{
                            binding.progressBarLecturaHuerta.hide()
                            Log.d("livedata error","{${result.exception}}")
                        }
                    }
                }
            }
        }

*/
        //Beggining

        viewModel.fetchLatestPosts().observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Result.Loading->{
                    Log.d("Livedata","Loading...")
                    binding.progressBarLecturaHuerta.show()
                    binding.carousel.hide()
                    binding.descripcionCardview.hide()
                }
                is Result.Success->{
                    binding.progressBarLecturaHuerta.hide()
                    binding.carousel.show()
                    binding.descripcionCardview.show()
                    Log.d("Livedata","${result.data}")

                    if(result.data.isEmpty()){
                        binding.emptyContainerLecturaHuerta.show()
                        return@Observer
                    }else{
                        binding.emptyContainerLecturaHuerta.hide()
                    }


                    Adapter = LecturaHuertaAdapter(result.data as ArrayList<PostServer>,this)
                    binding.rvPostList.adapter = Adapter
                }

                is Result.Failure->{
                    binding.progressBarLecturaHuerta.hide()
                    binding.emptyContainerLecturaHuerta.show()

                    Log.d("livedata_error","{${result.exception}}")

                }
            }
        })


        //End

        viewModel.fetchIsaisImages().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    imageList.clear()
                    for (image in result.data) {
                        imageList.add(CarouselItem(image.imageUrl, image.review))
                    }
                    binding.carousel.addData(imageList)
                }
                is Result.Failure -> {

                }
            }
        }

        binding.carousel.onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                val bundle = Bundle()
                val imagepass = ImageBundle(bitmap_string = carouselItem.imageUrl.toString())
                bundle.putParcelable("img_view_detalles",imagepass)
                findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_imageviewFragment,bundle)
                //Toast.makeText(context,"Auto: ${carouselItem.caption}",Toast.LENGTH_SHORT).show()
            }
            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                Toast.makeText(context,"Auto: ${dataObject.caption}",Toast.LENGTH_SHORT).show()
                //Revisar******************************
            }
        }

        binding.searchView.imeOptions =EditorInfo.IME_ACTION_DONE
        binding.searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.carousel.hide()
                if (query.isNullOrEmpty() or query.isNullOrBlank()){
                    binding.carousel.show()
                }else{
                    binding.carousel.hide()
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText.isNullOrEmpty() or newText.isNullOrBlank()){
                    binding.carousel.show()
                }else{
                    binding.carousel.hide()
                    Adapter.filter.filter(newText)
                }

                return false
            }
        })

        val fabButton = view.findViewById<ExtendedFloatingActionButton>(R.id.extended_fab)
        fabButton.setOnClickListener{
            val user = FirebaseAuth.getInstance()
            if(user.uid == null){
                val action = LecturaHuertaFragmentDirections.actionNavigationLecturaHuertaToLoginFragment("agregarTema")
                findNavController().navigate(action)
                Toast.makeText(context,"Usuario no registrado",Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_agregarTemaFragment)
            }
        }
    }

    override fun onPostClick(Post: PostServer) {
        val bundle = Bundle()
        bundle.putParcelable("post",Post)
        findNavController().navigate(R.id.action_navigation_lecturaHuerta_to_detallesPostFragment,bundle)
    }
}