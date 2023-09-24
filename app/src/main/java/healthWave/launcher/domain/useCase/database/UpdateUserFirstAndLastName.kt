package healthWave.launcher.domain.useCase.database

import healthWave.launcher.domain.repository.UserRepository

class UpdateUserFirstAndLastName(
    private val repository: UserRepository
) {
    suspend operator fun invoke(firstName: String, lastName: String) {
        return repository.updateUserFirstAndLastName(firstName, lastName)
    }
}