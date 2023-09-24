package healthWave.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import healthWave.core.util.Constants
import healthWave.launcher.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HealthWaveDataStore(
    private val context: Context
) : DataStoreRepository {

    companion object {
        const val DATASTORE_NAME = Constants.DATASTORE_NAME
    }

    object PreferenceKeys {
        val THEME = stringPreferencesKey(Constants.THEME)
        val NOTIFICATIONS = stringPreferencesKey(Constants.NOTIFICATIONS)
    }

    private val readOnlyProperty = preferencesDataStore(name = DATASTORE_NAME)
    private val Context.dataStore: DataStore<Preferences> by readOnlyProperty

    override suspend fun saveApplicationTheme(themeValue: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.THEME] = themeValue
        }
    }

    override fun readApplicationTheme(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.THEME] ?: "LIGHT MODE"
        }
    }

    override suspend fun saveNotificationsChoice(notificationsChoice: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.NOTIFICATIONS] = notificationsChoice
        }
    }

    override fun readNotificationsChoice(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferenceKeys.NOTIFICATIONS] ?: "OFF"
        }
    }
}

