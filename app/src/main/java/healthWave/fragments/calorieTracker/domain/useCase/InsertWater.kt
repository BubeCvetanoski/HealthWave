package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.data.local.database.entity.Water
import healthWave.fragments.calorieTracker.domain.repository.CalorieTrackerRepository

class InsertWater(
    val repository: CalorieTrackerRepository
) {
    suspend operator fun invoke(
        water: Water
    ) {
        repository.insertWater(water)
    }
}