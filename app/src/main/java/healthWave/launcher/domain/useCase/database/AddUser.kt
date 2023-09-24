package healthWave.launcher.domain.useCase.database

import healthWave.data.local.database.entity.User
import healthWave.launcher.domain.repository.UserRepository

class AddUser(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        repository.insertUser(user)
    }
}