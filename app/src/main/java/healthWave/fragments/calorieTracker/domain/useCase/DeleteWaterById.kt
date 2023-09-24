package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.repository.FoodRepository

class DeleteWaterById(
    val repository: FoodRepository
) {
    suspend operator fun invoke(waterId: Int) {
        repository.deleteWaterById(waterId)
    }
}