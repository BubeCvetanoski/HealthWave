package healthWave.data.mapper

import healthWave.data.local.database.entity.FoodEntity
import healthWave.data.remote.model.Product
import healthWave.fragments.calorieTracker.domain.model.Food
import healthWave.fragments.calorieTracker.domain.model.FoodNutrimentsInfo
import healthWave.fragments.calorieTracker.domain.model.MealType
import kotlin.math.roundToInt

fun FoodEntity.toFood(): Food {
    return Food(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = MealType.fromString(mealType),
        amount = amount,
        date = date,
        calories = calories,
        carbsPer100g = carbsPer100g,
        proteinPer100g = proteinPer100g,
        fatPer100g = fatPer100g,
        caloriesPer100g = caloriesPer100g,
        id = id
    )
}

fun Food.toFoodEntity(): FoodEntity {
    return FoodEntity(
        name = name,
        carbs = carbs,
        protein = protein,
        fat = fat,
        imageUrl = imageUrl,
        mealType = mealType.name,
        amount = amount,
        date = date,
        calories = calories,
        carbsPer100g = carbsPer100g,
        proteinPer100g = proteinPer100g,
        fatPer100g = fatPer100g,
        caloriesPer100g = caloriesPer100g,
        id = id
    )
}

fun Product.toFoodNutrimentsInfo(): FoodNutrimentsInfo? {
    val carbs100g = nutriments.carbohydrates100g.roundToInt()
    val protein100g = nutriments.proteins100g.roundToInt()
    val fat100g = nutriments.fat100g.roundToInt()
    val calories100g = nutriments.energyKcal100g.roundToInt()
    return FoodNutrimentsInfo(
        name = productName ?: return null,
        carbs100g = carbs100g,
        protein100g = protein100g,
        fat100g = fat100g,
        calories100g = calories100g,
        imageUrl = imageFrontThumbUrl
    )
}