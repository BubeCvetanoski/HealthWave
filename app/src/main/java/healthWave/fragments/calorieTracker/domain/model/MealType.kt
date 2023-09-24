package healthWave.fragments.calorieTracker.domain.model

sealed class MealType(val name: String) {
    object AllMeals: MealType("All meals")
    object Breakfast: MealType("Breakfast")
    object Lunch: MealType("Lunch")
    object Dinner: MealType("Dinner")
    object Snack: MealType("Snack")
    object Water: MealType("Water")

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
