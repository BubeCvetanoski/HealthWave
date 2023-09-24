package healthWave.launcher.domain.useCase.datastore

import healthWave.launcher.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow

class ReadNotificationsChoice(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke(): Flow<String> {
        return dataStoreRepository.readNotificationsChoice()
    }
}