package com.darioArevalo.biblioisais.domain.courses

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CourseServer

interface CoursesRepo {
    suspend fun getCourse0():Result<List<CourseServer>>
    suspend fun getCourse1():Result<List<CourseServer>>
    suspend fun getCourse2():Result<List<CourseServer>>
}