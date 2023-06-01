package moises.com.usersapp.ui.main.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.ui.base.State
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: RepositoryContract) : ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    private var isEmpty = true

    fun loadUsers(page: Int, result: Int) {
        viewModelScope.launch {
            _state.value = State.Loading(isEmpty)
            _state.value = try {
                State.Success(repository.getUsers(page, result))
            } catch (exception: Exception) {
                State.Error(exception)
            }
            _state.value = State.Loading(false)
        }
    }
}
