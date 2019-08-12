package moises.com.usersapp.ui.main

import androidx.lifecycle.ViewModel
import moises.com.usersapp.extensions.SingleLiveEvent
import moises.com.usersapp.model.User
import javax.inject.Inject

class MainEventViewModel
    @Inject
    constructor(): ViewModel() {

    val event by lazy { SingleLiveEvent<MainEvent>() }

    fun showUserDetail(user: User) {
        event.value = ShowUserDetail(user)
    }
}