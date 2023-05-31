package moises.com.usersapp.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import moises.com.usersapp.extensions.SingleLiveEvent
import moises.com.usersapp.model.User
import javax.inject.Inject

@HiltViewModel
class MainEventViewModel @Inject constructor(): ViewModel() {

    val event by lazy { SingleLiveEvent<MainEvent>() }

    sealed class MainEvent {
        data class ShowUserDetail(val user: User): MainEvent()
    }

    fun showUserDetail(user: User) {
        event.value = MainEvent.ShowUserDetail(user)
    }
}