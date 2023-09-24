package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.data.local.database.entity.Water
import healthWave.fragments.calorieTracker.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow

class GetWaterByDate (
    val repository: FoodRepository
) {
    operator fun invoke(date: String): Flow<List<Water>> {
        return repository.getWaterByDate(date)
    }
}