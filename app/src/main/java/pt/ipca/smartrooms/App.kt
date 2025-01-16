package pt.ipca.smartrooms

import android.app.Application
import com.ruialves.dtt_assessment.util.AuthInterceptor
import okhttp3.OkHttpClient
import pt.ipca.smartrooms.net.ApiResultCallAdapterFactory
import pt.ipca.smartrooms.repository.*
import pt.ipca.smartrooms.sharedPreferences.SP
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResultCallAdapterFactory())
            .client(OkHttpClient().newBuilder().addInterceptor(AuthInterceptor()).build())

        ServiceLocator.apply {
            roomRepository = RoomRepository(retrofit)
            ucurricularRepository = UcurricularRepository(retrofit)
            authRepository = AuthRepository(retrofit)
            schoolRepository = SchoolRepository(retrofit)
            hardwareRepository = HardwareRepository(retrofit)
            actuatorsRepository = ActuatorsRepository(retrofit)
            tokenSP = SP(applicationContext)
            userRepository = UserRepository()
        }
    }
}