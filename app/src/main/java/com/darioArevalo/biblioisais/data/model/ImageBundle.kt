package com.darioArevalo.biblioisais.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageBundle(
    val bitmap_string: String = ""
):Parcelable