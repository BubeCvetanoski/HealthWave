package healthWave.launcher.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveApplicationTheme(themeValue: String)
    fun readApplicationTheme(): Flow<String>
    suspend fun saveNotificationsChoice(notificationsChoice: String)
    fun readNotificationsChoice(): Flow<String>
}