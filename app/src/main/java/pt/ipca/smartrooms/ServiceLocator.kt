package pt.ipca.smartrooms

import pt.ipca.smartrooms.interfaces.*
import pt.ipca.smartrooms.repository.UserRepository
import pt.ipca.smartrooms.sharedPreferences.SP

object ServiceLocator {
    lateinit var roomRepository: RoomRepositoryInterface
    lateinit var ucurricularRepository: UcurricularRepositoryInterface
    lateinit var authRepository: AuthRepositoryInterface
    lateinit var schoolRepository : SchoolRepositoryInterface
    lateinit var actuatorsRepository: ActuatorsRepositoryInterface
    lateinit var hardwareRepository: HardwareRepositoryInterface
    lateinit var tokenSP: TokenSP
    lateinit var userRepository: UserRepositoryInterface
}