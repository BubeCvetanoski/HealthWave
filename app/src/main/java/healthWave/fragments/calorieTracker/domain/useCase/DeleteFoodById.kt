package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.repository.FoodRepository

class DeleteFoodById(
    val repository: FoodRepository
) {
    suspend operator fun invoke(foodId: Int) {
        repository.deleteFoodById(foodId)
    }
}