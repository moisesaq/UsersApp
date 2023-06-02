package moises.com.usersapp.ui.base

sealed class State {
    data class Loading(val isLoading: Boolean): State()
    data class Success<T>(val data: T): State()
    data class Error(val errorMessage: String): State()
}

