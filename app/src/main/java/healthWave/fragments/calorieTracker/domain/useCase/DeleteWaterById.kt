package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.repository.CalorieTrackerRepository

class DeleteWaterById(
    val repository: CalorieTrackerRepository
) {
    suspend operator fun invoke(waterId: Int) {
        repository.deleteWaterById(waterId)
    }
}