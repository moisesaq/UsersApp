package moises.com.usersapp.ui.base

sealed class State {
    object Loading: State()
    data class Success<T>(val data: T): State()
    data class Error(val error: Throwable): State()
}

