package healthWave.launcher.domain.useCase.datastore

data class DataStoreUseCases(
    val saveApplicationTheme: SaveApplicationTheme,
    val readApplicationTheme: ReadApplicationTheme,
    val saveNotificationsChoice: SaveNotificationsChoice,
    val readNotificationsChoice: ReadNotificationsChoice
)
