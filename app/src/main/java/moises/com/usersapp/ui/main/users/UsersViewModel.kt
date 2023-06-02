package moises.com.usersapp.ui.main.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.ui.base.State
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
        viewModelScope.launch(dispatcher) {
            _state.value = State.Loading(isEmpty)
            _state.value = try {
                val users = repository.getUsers(page, result)
                isEmpty = users.isEmpty()
                State.Success(users)
            } catch (exception: Exception) {
                State.Error(exception.localizedMessage ?: "Error!")
            }
            _state.value = State.Loading(false)
        }
    }
}
