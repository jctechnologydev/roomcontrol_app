package pt.ipca.smartrooms.repository

import pt.ipca.smartrooms.BASE_URL
import pt.ipca.smartrooms.ServiceLocator
import pt.ipca.smartrooms.interfaces.UcurricularRepositoryInterface
import pt.ipca.smartrooms.model.Room
import pt.ipca.smartrooms.model.State
import pt.ipca.smartrooms.model.UCurricular
import pt.ipca.smartrooms.net.ApiResult
import pt.ipca.smartrooms.services.UCurricularAPI
import retrofit2.Retrofit.Builder

class UcurricularRepository(private val retrofitBuilder: Builder) : UcurricularRepositoryInterface {

   private val webservice = retrofitBuilder
      .baseUrl("${BASE_URL}UCurricular/")
      .build()
      .create(UCurricularAPI::class.java)

   override suspend fun getUcurriculars(): ApiResult<ArrayList<UCurricular>> {
      return webservice.getUcurricularRooms()
   }

}