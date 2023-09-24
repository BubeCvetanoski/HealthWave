package healthWave.launcher.domain.repository

import healthWave.data.local.database.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User>
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun updateUserFirstAndLastName(firstName: String, lastName: String)
}