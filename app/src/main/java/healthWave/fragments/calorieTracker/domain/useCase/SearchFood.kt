package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.model.FoodNutrimentsInfo
import healthWave.fragments.calorieTracker.domain.repository.FoodRepository

class SearchFood(
    private val repository: FoodRepository
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