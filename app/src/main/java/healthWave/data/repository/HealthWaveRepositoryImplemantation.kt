package healthWave.data.repository

import healthWave.data.local.database.HealthWaveDao
import healthWave.data.local.database.entity.Exercise
import healthWave.data.local.database.entity.User
import healthWave.data.local.database.entity.Water
import healthWave.data.mapper.toFood
import healthWave.data.mapper.toFoodEntity
import healthWave.data.mapper.toFoodNutrimentsInfo
import healthWave.data.remote.HealthWaveApi
import healthWave.fragments.calorieTracker.domain.model.Food
import healthWave.fragments.calorieTracker.domain.model.FoodNutrimentsInfo
import healthWave.fragments.calorieTracker.domain.repository.FoodRepository
import healthWave.fragments.trainingTracker.domain.repository.ExerciseRepository
import healthWave.launcher.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthWaveRepositoryImplemantation @Inject constructor(
    private val dao: HealthWaveDao,
    private val api: HealthWaveApi
) : UserRepository, ExerciseRepository, FoodRepository {

    //Functions for User table

    override fun getUser(): Flow<User> {
        return dao.getUser()
    }

    override suspend fun insertUser(user: User) {
        return dao.insertUser(user)
    }

    override suspend fun updateUser(user: User) {
        return dao.updateUser(user)
    }

    override suspend fun updateUserFirstAndLastName(firstName: String, lastName: String) {
        return dao.updateUserFirstAndLastName(firstName, lastName)
    }

    //Functions for Exercise table

    override suspend fun insertExercise(exercise: Exercise) {
        return dao.insertExercise(exercise)
    }

    override fun getExercisesByDate(date: String): Flow<List<Exercise>> {
        return dao.getExercisesByDate(date)
    }

    override suspend fun updateExerciseByNumberAndDate(
        name: String,
        sets: String,
        reps: String,
        load: String,
        number: String,
        date: String
    ) {
        return dao.updateExerciseByNumberAndDate(
            name,
            sets,
            reps,
            load,
            number,
            date
        )
    }

    override suspend fun deleteAllExercisesByDate(date: String) {
        return dao.deleteAllExercisesByDate(date)
    }

    //Functions for Food table

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<FoodNutrimentsInfo>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(
                searchDto.products
                    .mapNotNull { it.toFoodNutrimentsInfo() }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertFood(food: Food) {
        return dao.insertFood(food.toFoodEntity())
    }

    override fun getFoodByDate(date: String): Flow<List<Food>> {
        return dao.getFoodByDate(date).map { entities ->
            entities.map { it.toFood() }
        }
    }

    override fun getFoodByName(name: String): Flow<List<Food>> {
        return dao.getFoodByName("%$name%").map { entities ->
            entities.map { it.toFood() }
        }
    }

    override suspend fun deleteFoodById(foodId: Int) {
        return dao.deleteFoodById(foodId)
    }

    //Functions for Water table

    override suspend fun insertWater(water: Water) {
        return dao.insertWater(water)
    }

    override fun getWaterByDate(date: String): Flow<List<Water>> {
        return dao.getWaterByDate(date)
    }

    override suspend fun deleteWaterById(waterId: Int) {
        return dao.deleteWaterById(waterId)
    }
}
