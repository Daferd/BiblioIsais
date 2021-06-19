package com.darioArevalo.biblioisais.domain.courses

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CourseServer
import com.darioArevalo.biblioisais.data.remote.courses.CoursesDataSource

class CoursesRepoImpl(private val dataSource: CoursesDataSource):CoursesRepo {

    override suspend fun getCourse0(): Result<List<CourseServer>> = dataSource.getCourse1()
    override suspend fun getCourse1(): Result<List<CourseServer>> = dataSource.getCourse2()
    override suspend fun getCourse2(): Result<List<CourseServer>> = dataSource.getCourse3()
}