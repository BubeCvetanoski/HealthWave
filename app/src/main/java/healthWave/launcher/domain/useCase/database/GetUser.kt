package healthWave.launcher.domain.useCase.database

import healthWave.data.local.database.entity.User
import healthWave.launcher.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUser(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<User> {
        return repository.getUser()
    }
}