package healthWave.fragments.calorieTracker.domain.model

data class Food(
    val name: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val imageUrl: String?,
    val mealType: MealType,
    val amount: Int,
    val date: String,
    val calories: Int,
    val carbsPer100g: Int,
    val proteinPer100g: Int,
    val fatPer100g: Int,
    val caloriesPer100g: Int,
    val id: Int? = null
)
