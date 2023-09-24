package healthWave.fragments.calorieTracker.domain.useCase

import healthWave.fragments.calorieTracker.domain.model.Food
import healthWave.fragments.calorieTracker.domain.model.FoodNutrimentsInfo
import healthWave.fragments.calorieTracker.domain.model.MealType
import healthWave.fragments.calorieTracker.domain.repository.FoodRepository
import kotlin.math.roundToInt

class InsertFood(
    val repository: FoodRepository
) {
    suspend operator fun invoke(
        food: FoodNutrimentsInfo,
        amount: Int,
        mealType: MealType,
        date: String
    ) {
        repository.insertFood(
            Food(
                name = food.name,
                carbs = ((food.carbs100g / 100f) * amount).roundToInt(),
                protein = ((food.protein100g / 100f) * amount).roundToInt(),
                fat = ((food.fat100g / 100f) * amount).roundToInt(),
                calories = ((food.calories100g / 100f) * amount).roundToInt(),
                imageUrl = food.imageUrl,
                mealType = mealType,
                amount = amount,
                date = date,
                carbsPer100g = food.carbs100g,
                proteinPer100g = food.protein100g,
                fatPer100g = food.fat100g,
                caloriesPer100g = food.calories100g
            )
        )
    }
}