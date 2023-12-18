package healthWave.fragments.calorieTracker.domain.repository

import healthWave.data.local.database.entity.Water
import healthWave.fragments.calorieTracker.domain.model.Food
import healthWave.fragments.calorieTracker.domain.model.FoodNutrimentsInfo
import kotlinx.coroutines.flow.Flow

interface CalorieTrackerRepository {
    suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<FoodNutrimentsInfo>>

    suspend fun insertFood(food: Food)

    fun getFoodByDate(date: String): Flow<List<Food>>

    fun getFoodByName(name: String): Flow<List<Food>>

    suspend fun deleteFoodById(foodId: Int)

    suspend fun insertWater(water: Water)

    fun getWaterByDate(date: String): Flow<List<Water>>

    suspend fun deleteWaterById(waterId: Int)
}