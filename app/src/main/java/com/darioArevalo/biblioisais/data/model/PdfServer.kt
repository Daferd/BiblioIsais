package com.darioArevalo.biblioisais.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PdfServer (
    val name: String = "",
    val author: String = "",
    val pdfUrl: String = ""
):Parcelable