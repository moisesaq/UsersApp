package moises.com.usersapp.ui.main.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import moises.com.usersapp.model.User
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.ui.base.State
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val repository: RepositoryContract,
    private val dispatcher: CoroutineDispatcher
    ) : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state
    var isEmpty = true
        private set

    fun loadUsers(page: Int, result: Int) {
        if (state.value is State.Success<*>) {
            Timber.e(":-> Users have already been loaded")
            return
        }
        viewModelScope.launch(dispatcher) {
            setState(State.Loading)
            try {
                val users = repository.getUsers(page, result)
                isEmpty = users.isEmpty()
                setState(State.Success(users))
                // setState(State.Loading(false))
            } catch (throwable: Throwable) {
                setState(State.Error(throwable))
                // setState(State.Loading(false))
            }
        }
    }

    /*private fun handleSuccess(users: List<User>) {
        handleLoading(false)
        isEmpty = users.isEmpty()
        setState(State.Success(users))
    }

    private fun handleError(throwable: Throwable) {
        handleLoading(false)
        setState(State.Error(throwable))
    }

    private fun handleLoading(isLoading: Boolean) = setState(State.Loading(isLoading))*/

    private fun setState(state: State) {
        _state.postValue(state)
    }
}
