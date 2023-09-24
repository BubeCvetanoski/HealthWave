package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.data.local.database.entity.Water
import healthWave.fragments.calorieTracker.domain.repository.FoodRepository

class InsertWater(
    val repository: FoodRepository
) {
    suspend operator fun invoke(
        water: Water
    ) {
        repository.insertWater(water)
    }
}