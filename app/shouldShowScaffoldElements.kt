private val TypedDestination<*>.shouldShowScaffoldElements: Boolean
    get() = when (this) {
        is SplashScreenDestination,
        is SignUpFirstScreenDestination,
        is SignUpSecondScreenDestination,
        is SignUpThirdScreenDestination -> false

        else -> true
    }