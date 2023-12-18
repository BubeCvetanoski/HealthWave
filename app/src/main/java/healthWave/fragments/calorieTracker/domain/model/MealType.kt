package healthWave.fragments.calorieTracker.domain.model

sealed class MealType(val name: String) {
    data object AllMeals: MealType("All meals")
    data object Breakfast: MealType("Breakfast")
    data object Lunch: MealType("Lunch")
    data object Dinner: MealType("Dinner")
    data object Snack: MealType("Snack")
    data object Water: MealType("Water")

    companion object {
        fun fromString(name: String): MealType {
            return when(name) {
                "All meals" -> AllMeals
                "Breakfast" -> Breakfast
                "Lunch" -> Lunch
                "Dinner" -> Dinner
                "Snack" -> Snack
                else -> Water
            }
        }
    }
}
