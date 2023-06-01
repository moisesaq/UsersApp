package moises.com.usersapp.ui.main.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import moises.com.usersapp.model.User
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.ui.base.Output
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: RepositoryContract) : ViewModel() {
    val output = Output<List<User>, String>()
    private var isEmpty = true

    fun loadUsers(page: Int, result: Int) {
        viewModelScope.launch {
            output.loading.value = isEmpty
            try {
                handleSuccess(repository.getUsers(page, result))
            } catch (exception: Exception) {
                handleError(exception)
            }
        }
    }

    private fun handleSuccess(users: List<User>) {
        hideLoading()
        output.success.value = users
        output.isDataValid.value = users.isNotEmpty()
        isEmpty = users.isEmpty()
    }

    private fun handleError(e: Throwable) {
        Timber.e("Error loading users: ${e.localizedMessage}")
        hideLoading()
        output.error.value = e.localizedMessage
    }

    private fun hideLoading() {
        output.loading.value = false
    }
}
