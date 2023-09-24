package healthWave.fragments.calorieTracker.domain.useCase

data class FoodUseCases(
    val searchFood: SearchFood,
    val insertFood: InsertFood,
    val getFoodByDate: GetFoodByDate,
    val getFoodByName: GetFoodByName,
    val deleteFoodById: DeleteFoodById,
    val insertWater: InsertWater,
    val getWaterByDate: GetWaterByDate,
    val deleteWaterById: DeleteWaterById
)
