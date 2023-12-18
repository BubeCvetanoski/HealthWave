package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.data.local.database.entity.Water
import healthWave.fragments.calorieTracker.domain.repository.CalorieTrackerRepository
import kotlinx.coroutines.flow.Flow

class GetWaterByDate (
    val repository: CalorieTrackerRepository
) {
    operator fun invoke(date: String): Flow<List<Water>> {
        return repository.getWaterByDate(date)
    }
}