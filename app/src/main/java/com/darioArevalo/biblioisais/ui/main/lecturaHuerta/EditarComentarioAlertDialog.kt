package com.darioArevalo.biblioisais.ui.main.lecturaHuerta

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.darioArevalo.biblioisais.R
import com.darioArevalo.biblioisais.data.model.CommentPost
import com.darioArevalo.biblioisais.data.model.PostServer
import com.darioArevalo.biblioisais.databinding.FragmentLecturaHuertaBinding
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.LecturaHuertaAdapter
import com.darioArevalo.biblioisais.ui.main.lecturaHuerta.adapter.commentAdapter
import com.google.android.material.textfield.TextInputEditText

class EditarComentarioAlertDialog: DialogFragment(), commentAdapter.onCommentClickListener{
    private lateinit var Adapter : commentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Adapter = commentAdapter(ArrayList(),this)
        return inflater.inflate(R.layout.comment_edit_row,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }

    private fun setupView(view: View) {
        //view.tvTitle.text = arguments?.getString(KEY_TITLE)
        //view.tvSubTitle.text = arguments?.getString(KEY_SUBTITLE)
        //val comment_to_set = view.findViewById<TextInputEditText>(R.id.txt_setTxt_commentEdit)

    }

    private fun setupClickListeners(view: View) {
        /*view.btnPositive.setOnClickListener {
            dismiss()
        }
        view.btnNegative.setOnClickListener {
            dismiss()
        }*/
    }

    override fun onCommentClick(commentEditText: String) {
        //showDialogFragment(commentEditText)
        //val comment_to_set = view?.findViewById<TextInputEditText>(R.id.txt_setTxt_commentEdit)
        //comment_to_set?.setText(commentEditText)
        //Log.d("coment_to",commentEditText)

    }





}

