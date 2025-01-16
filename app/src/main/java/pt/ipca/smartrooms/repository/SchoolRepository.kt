package pt.ipca.smartrooms.repository

import pt.ipca.smartrooms.BASE_URL
import pt.ipca.smartrooms.interfaces.SchoolRepositoryInterface
import pt.ipca.smartrooms.model.School
import pt.ipca.smartrooms.net.ApiResult
import pt.ipca.smartrooms.services.BuildingAPI
import retrofit2.Retrofit

class SchoolRepository(private val retrofitBuilder: Retrofit.Builder) : SchoolRepositoryInterface {

    private val webservice = retrofitBuilder
        .baseUrl("${BASE_URL}School/")
        .build()
        .create(BuildingAPI::class.java)

    override suspend fun getSchools(): ApiResult<ArrayList<School>> {
        return webservice.getAll()
    }

}