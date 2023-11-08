package healthWave.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import healthWave.core.util.Constants
import healthWave.data.local.database.HealthWaveDatabase
import healthWave.data.local.datastore.HealthWaveDataStore
import healthWave.data.remote.HealthWaveApi
import healthWave.data.repository.HealthWaveRepositoryImplemantation
import healthWave.fragments.calorieTracker.domain.repository.FoodRepository
import healthWave.fragments.calorieTracker.domain.useCase.DeleteFoodById
import healthWave.fragments.calorieTracker.domain.useCase.DeleteWaterById
import healthWave.fragments.calorieTracker.domain.useCase.FoodUseCases
import healthWave.fragments.calorieTracker.domain.useCase.GetFoodByDate
import healthWave.fragments.calorieTracker.domain.useCase.GetFoodByName
import healthWave.fragments.calorieTracker.domain.useCase.GetWaterByDate
import healthWave.fragments.calorieTracker.domain.useCase.InsertFood
import healthWave.fragments.calorieTracker.domain.useCase.InsertWater
import healthWave.fragments.calorieTracker.domain.useCase.SearchFood
import healthWave.fragments.trainingTracker.domain.repository.ExerciseRepository
import healthWave.fragments.trainingTracker.domain.useCase.AddExercise
import healthWave.fragments.trainingTracker.domain.useCase.DeleteAllExercisesByDate
import healthWave.fragments.trainingTracker.domain.useCase.ExerciseUseCases
import healthWave.fragments.trainingTracker.domain.useCase.GetExercisesByDate
import healthWave.fragments.trainingTracker.domain.useCase.UpdateExerciseByNumberAndDate
import healthWave.launcher.domain.repository.DataStoreRepository
import healthWave.launcher.domain.repository.UserRepository
import healthWave.launcher.domain.useCase.database.AddUser
import healthWave.launcher.domain.useCase.database.GetUser
import healthWave.launcher.domain.useCase.database.UpdateUser
import healthWave.launcher.domain.useCase.database.UpdateUserFirstAndLastName
import healthWave.launcher.domain.useCase.database.UserUseCases
import healthWave.launcher.domain.useCase.datastore.DataStoreUseCases
import healthWave.launcher.domain.useCase.datastore.ReadApplicationTheme
import healthWave.launcher.domain.useCase.datastore.ReadNotificationsChoice
import healthWave.launcher.domain.useCase.datastore.SaveApplicationTheme
import healthWave.launcher.domain.useCase.datastore.SaveNotificationsChoice
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideHealthWaveApi(client: OkHttpClient): HealthWaveApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideHealthWaveDatabase(app: Application): HealthWaveDatabase {
        return Room.databaseBuilder(
            app,
            HealthWaveDatabase::class.java,
            HealthWaveDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideHealthWaveDataStore(
        app: Application
    ): DataStoreRepository = HealthWaveDataStore(context = app)

    @Provides
    @Singleton
    fun provideDataStoreUseCases(
        dataStoreRepository: DataStoreRepository
    ): DataStoreUseCases = DataStoreUseCases(
        saveApplicationTheme = SaveApplicationTheme(dataStoreRepository),
        readApplicationTheme = ReadApplicationTheme(dataStoreRepository),
        saveNotificationsChoice = SaveNotificationsChoice(dataStoreRepository),
        readNotificationsChoice = ReadNotificationsChoice(dataStoreRepository)
    )

    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            getUser = GetUser(repository),
            addUser = AddUser(repository),
            updateUser = UpdateUser(repository),
            updateUserFirstAndLastName = UpdateUserFirstAndLastName(repository)
        )
    }

    @Provides
    @Singleton
    fun provideExerciseUseCases(repository: ExerciseRepository): ExerciseUseCases {
        return ExerciseUseCases(
            addExercise = AddExercise(repository),
            getExercisesByDate = GetExercisesByDate(repository),
            updateExerciseByNumberAndDate = UpdateExerciseByNumberAndDate(repository),
            deleteAllExercisesByDate = DeleteAllExercisesByDate(repository)
        )
    }

    @Provides
    @Singleton
    fun provideFoodUseCases(repository: FoodRepository): FoodUseCases {
        return FoodUseCases(
            searchFood = SearchFood(repository),
            insertFood = InsertFood(repository),
            getFoodByDate = GetFoodByDate(repository),
            getFoodByName = GetFoodByName(repository),
            deleteFoodById = DeleteFoodById(repository),
            insertWater = InsertWater(repository),
            getWaterByDate = GetWaterByDate(repository),
            deleteWaterById = DeleteWaterById(repository)
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(database: HealthWaveDatabase, api: HealthWaveApi): UserRepository {
        return HealthWaveRepositoryImplemantation(database.healthWaveDao, api)
    }

    @Provides
    @Singleton
    fun provideExerciseRepository(database: HealthWaveDatabase, api: HealthWaveApi): ExerciseRepository {
        return HealthWaveRepositoryImplemantation(database.healthWaveDao, api)
    }

    @Provides
    @Singleton
    fun provideFoodRepository(database: HealthWaveDatabase, api: HealthWaveApi): FoodRepository {
        return HealthWaveRepositoryImplemantation(database.healthWaveDao, api)
    }
}