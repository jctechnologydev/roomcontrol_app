package pt.ipca.smartrooms.interfaces

import pt.ipca.smartrooms.model.School
import pt.ipca.smartrooms.net.ApiResult

interface SchoolRepositoryInterface {
    suspend fun getSchools() : ApiResult<ArrayList<School>>
}