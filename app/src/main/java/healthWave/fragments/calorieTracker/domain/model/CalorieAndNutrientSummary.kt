package healthWave.fragments.calorieTracker.domain.model

data class CalorieAndNutrientSummary(
    val calorieMap: Map<MealType, Int>,
    val nutrientMap: Map<String, Int>
)
