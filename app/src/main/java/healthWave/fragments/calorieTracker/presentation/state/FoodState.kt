package healthWave.fragments.calorieTracker.presentation.state

data class FoodState(
    val query: String = "",
    val isLoading: Boolean = false,
    val viewMyMeals: Boolean = false,
    val isEatingOccasionItemCardExpanded: Boolean = false,
    val foodNutrimentsInfo: List<FoodNutrimentsInfoState> = emptyList(),
    val waterIntakeInfo: List<WaterState> = emptyList(),
    val takeWaterIntake: WaterState = WaterState(milliliters = "")
)
