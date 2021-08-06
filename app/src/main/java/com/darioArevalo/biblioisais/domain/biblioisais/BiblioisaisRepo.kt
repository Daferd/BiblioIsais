package com.darioArevalo.biblioisais.domain.biblioisais

import com.darioArevalo.biblioisais.core.Result
import com.darioArevalo.biblioisais.data.model.CourseServer

interface BiblioisaisRepo {
    suspend fun getCourse1():Result<List<CourseServer>>
    suspend fun getCourse2():Result<List<CourseServer>>
    suspend fun getCourse3():Result<List<CourseServer>>
}