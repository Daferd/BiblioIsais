package com.darioArevalo.biblioisais.domain.profile

import android.graphics.Bitmap
import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.UserServer
import com.darioArevalo.biblioisais.data.remote.profile.ProfileDataSource

class ProfileRepoImpl(private val dataSource: ProfileDataSource):ProfileRepo {
    override suspend fun getUser(): Result<UserServer> = dataSource.getUser()
    override suspend fun updateProfilePicture(imageBitmap: Bitmap) =dataSource.uploadPicture(imageBitmap)
    override suspend fun updateUsername(username: String) = dataSource.uploadUsername(username)
    override suspend fun updateEmail(email: String) = dataSource.uploadEmail(email)
    override suspend fun updatePassword(password: String) = dataSource.uploadPassword(password)
}