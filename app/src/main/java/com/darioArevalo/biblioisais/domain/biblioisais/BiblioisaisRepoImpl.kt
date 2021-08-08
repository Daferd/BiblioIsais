package com.darioArevalo.biblioisais.domain.biblioisais

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CourseServer
import com.darioArevalo.biblioisais.data.remote.biblioisais.BiblioisaisDataSource

class BiblioisaisRepoImpl(private val dataSource: BiblioisaisDataSource):BiblioisaisRepo {
    override suspend fun getCourse1(): Result<List<CourseServer>> = dataSource.getCourse1()
    override suspend fun getCourse2(): Result<List<CourseServer>> = dataSource.getCourse2()
    override suspend fun getCourse3(): Result<List<CourseServer>> = dataSource.getCourse3()
    override suspend fun getCourse4(): Result<List<CourseServer>> = dataSource.getCourse4()
}