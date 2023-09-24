package healthWave.launcher.domain.useCase.database

data class UserUseCases(
    val getUser: GetUser,
    val addUser: AddUser,
    val updateUser: UpdateUser,
    val updateUserFirstAndLastName: UpdateUserFirstAndLastName
)
