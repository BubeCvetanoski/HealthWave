package healthWave.launcher.domain.useCase.datastore

import healthWave.launcher.domain.repository.DataStoreRepository

class SaveNotificationsChoice(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(notificationsChoice: String){
        dataStoreRepository.saveNotificationsChoice(notificationsChoice)
    }
}