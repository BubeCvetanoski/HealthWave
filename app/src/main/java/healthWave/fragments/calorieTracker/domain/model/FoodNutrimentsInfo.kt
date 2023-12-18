package healthWave.fragments.calorieTracker.domain.model

data class FoodNutrimentsInfo(
    val id: Int? = null, //used for deletion from the OnSearch Screen when Added meals are opened.
    // In the whole of OnSearch Screen this class is used for showing the food items, so an unique Id is needed when deleting from database
    val name: String,
    val imageUrl: String?,
    val calories100g: Int,
    val carbs100g: Int,
    val protein100g: Int,
    val fat100g: Int
)