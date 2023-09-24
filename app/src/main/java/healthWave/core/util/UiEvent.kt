package healthWave.core.util

sealed class UiEvent {
    data class ShowToast(val message: UiText): UiEvent()
}