package moises.com.usersapp.ui.main

import moises.com.usersapp.model.User

sealed class MainEvent

data class ShowUserDetail(val user: User): MainEvent()