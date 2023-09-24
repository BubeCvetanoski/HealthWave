package healthWave.launcher.domain.useCase.datastore

import healthWave.launcher.domain.repository.DataStoreRepository

class SaveApplicationTheme(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(themeValue: String){
        dataStoreRepository.saveApplicationTheme(themeValue)
    }
}