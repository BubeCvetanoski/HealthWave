package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.model.Food
import healthWave.fragments.calorieTracker.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow

class GetFoodByName(
    val repository: FoodRepository
) {
    operator fun invoke(name: String): Flow<List<Food>> {
        return repository.getFoodByName(name)
    }
}