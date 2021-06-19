package com.darioArevalo.biblioisais.ui.main.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.ui.main.biblioisais.BiblioisaisFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.concurrent.timerTask


class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timer = Timer()
        timer.schedule(
            timerTask {
                val auth = FirebaseAuth.getInstance()
                if (auth.uid == null) {
                    goToLoginFragment()
                } else {
                    goToMainActivity()
                }
                //goToLoginActivity()
            }, 1000
        )
    }

    private fun goToLoginFragment() {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }

    private fun goToMainActivity() {
        findNavController().navigate(R.id.action_splashFragment_to_navigation_bibliomundo)
    }

}