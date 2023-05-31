package moises.com.usersapp.ui.main.users

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import moises.com.usersapp.model.User
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.repository.response.UserList
import moises.com.usersapp.ui.base.Output
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val repository: RepositoryContract) : ViewModel() {
    val output = Output<List<User>, String>()
    private val compositeDisposable = CompositeDisposable()
    private var isEmpty = true

    fun loadUsers(page: Int, result: Int, seed: String) {
        output.loading.value = isEmpty
        repository.getUsers(page, result, seed)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { success(it) },
                onError = { error(it) }
            ).addTo(compositeDisposable)
    }

    private fun success(list: UserList) {
        hideLoading()
        output.success.value = list.results
        output.isDataValid.value = list.results.isNotEmpty()
        isEmpty = list.results.isEmpty()
    }

    private fun error(e: Throwable) {
        Timber.e("Error loading users: ${e.localizedMessage}")
        hideLoading()
        output.error.value = e.localizedMessage
    }

    private fun hideLoading() {
        output.loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
