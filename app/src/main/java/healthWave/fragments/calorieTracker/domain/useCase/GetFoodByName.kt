package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.model.Food
import healthWave.fragments.calorieTracker.domain.repository.CalorieTrackerRepository
import kotlinx.coroutines.flow.Flow

class GetFoodByName(
    val repository: CalorieTrackerRepository
) {
    operator fun invoke(name: String): Flow<List<Food>> {
        return repository.getFoodByName(name)
    }
}