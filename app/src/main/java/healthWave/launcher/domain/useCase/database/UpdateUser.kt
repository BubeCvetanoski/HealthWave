package healthWave.launcher.domain.useCase.database

import healthWave.data.local.database.entity.User
import healthWave.launcher.domain.repository.UserRepository

class UpdateUser(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) {
        return repository.updateUser(user)
    }
}