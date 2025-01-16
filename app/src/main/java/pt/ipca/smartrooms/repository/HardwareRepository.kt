package pt.ipca.smartrooms.repository

import androidx.lifecycle.MutableLiveData
import pt.ipca.smartrooms.BASE_URL
import pt.ipca.smartrooms.interfaces.HardwareRepositoryInterface
import pt.ipca.smartrooms.model.*
import pt.ipca.smartrooms.model.DTO.HardwareDTO
import pt.ipca.smartrooms.model.DTO.HardwareDataDTO
import pt.ipca.smartrooms.net.ApiResult
import pt.ipca.smartrooms.services.HardwareAPI
import retrofit2.Retrofit

class HardwareRepository(private val retrofitBuilder: Retrofit.Builder) : HardwareRepositoryInterface  {

    private val webservice = retrofitBuilder
        .baseUrl("${BASE_URL}Hardware/")
        .build()
        .create(HardwareAPI::class.java)

    override suspend fun getHardware(idRoom : Int) : ApiResult<List<HardwareDataDTO>>{
        return webservice.getHardwareFromRoom(idRoom)
    }
}