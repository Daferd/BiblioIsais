package com.darioArevalo.biblioisais.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageBundle(
    val bitmap: String = ""
):Parcelable