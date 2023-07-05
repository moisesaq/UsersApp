package moises.com.usersapp.ui.main.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import moises.com.usersapp.model.User
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.ui.base.Output
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class Users2ViewModel @Inject constructor(
    private val repository: RepositoryContract,
    private val dispatcher: CoroutineDispatcher
    ) : ViewModel() {
    val output = Output<List<User>, Throwable>()

    fun loadUsers(page: Int, result: Int) {
        if (output.success.value?.isNotEmpty() == true) {
            Timber.e(":-> Users have already been loaded")
            return
        }
        viewModelScope.launch(dispatcher) {
            output.loading.postValue(true)
            try {
                val users = repository.getUsers(page, result)
                output.success.postValue(users)
                hideLoading()
            } catch (throwable: Throwable) {
                output.error.postValue(throwable)
                hideLoading()
            }
        }
    }

    private fun hideLoading() {
        output.loading.postValue(false)
    }
}
