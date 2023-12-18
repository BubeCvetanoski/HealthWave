package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.model.Food
import healthWave.fragments.calorieTracker.domain.repository.CalorieTrackerRepository
import kotlinx.coroutines.flow.Flow

class  GetFoodByDate(
    val repository: CalorieTrackerRepository
) {
    operator fun invoke(date: String): Flow<List<Food>> {
        return repository.getFoodByDate(date)
    }
}