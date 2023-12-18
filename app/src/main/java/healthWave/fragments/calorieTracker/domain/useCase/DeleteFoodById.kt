package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.repository.CalorieTrackerRepository

class DeleteFoodById(
    val repository: CalorieTrackerRepository
) {
    suspend operator fun invoke(foodId: Int) {
        repository.deleteFoodById(foodId)
    }
}