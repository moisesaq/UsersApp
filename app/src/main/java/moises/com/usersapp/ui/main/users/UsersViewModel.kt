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
        Timber.e(":-> Load users: $page")
        viewModelScope.launch(dispatcher) {
            handleLoading(isEmpty)
            try {
                handleSuccess(repository.getUsers(page, result))
            } catch (exception: Throwable) {
                handleError(exception)
            }
        }
    }

    private fun handleSuccess(users: List<User>) {
        isEmpty = users.isEmpty()
        handleLoading(false)
        setState(State.Success(users))
    }

    private fun handleError(throwable: Throwable) {
        handleLoading(false)
        setState(State.Error(throwable))
    }

    private fun handleLoading(isLoading: Boolean) = setState(State.Loading(isLoading))

    private fun setState(state: State) {
        Timber.i(":-> Set state: $state")
        _state.postValue(state)
    }
}
