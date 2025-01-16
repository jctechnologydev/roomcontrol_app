package pt.ipca.smartrooms.interfaces

import pt.ipca.smartrooms.model.UCurricular
import pt.ipca.smartrooms.net.ApiResult

interface UcurricularRepositoryInterface {
   suspend fun getUcurriculars() : ApiResult<ArrayList<UCurricular>>
}