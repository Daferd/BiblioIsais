package com.darioArevalo.biblioisais.domain.profile

import android.graphics.Bitmap
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.UserServer

interface ProfileRepo {
    suspend fun getUser():Result<UserServer>
    suspend fun updateProfilePicture(imageBitmap: Bitmap)
    suspend fun updateUsername(username: String)
    suspend fun updateEmail(email: String)
    suspend fun updatePassword(password: String)
}