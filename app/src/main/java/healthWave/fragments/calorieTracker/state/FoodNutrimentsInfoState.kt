package healthWave.fragments.calorieTracker.state

import healthWave.fragments.calorieTracker.domain.model.FoodNutrimentsInfo

data class FoodNutrimentsInfoState(
    val food: FoodNutrimentsInfo,
    val amount: String = "",
    val isFoodItemHeaderExpanded: Boolean = false
)
