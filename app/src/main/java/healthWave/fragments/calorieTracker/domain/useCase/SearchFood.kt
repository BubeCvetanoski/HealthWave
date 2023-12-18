package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.model.FoodNutrimentsInfo
import healthWave.fragments.calorieTracker.domain.repository.CalorieTrackerRepository

class SearchFood(
    private val repository: CalorieTrackerRepository
) {

    suspend operator fun invoke(
        query: String,
        page: Int = 1,
        pageSize: Int = 50
    ): Result<List<FoodNutrimentsInfo>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }
        return repository.searchFood(query.trim(), page, pageSize)
    }
}