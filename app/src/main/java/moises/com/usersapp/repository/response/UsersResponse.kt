package moises.com.usersapp.repository.response

import moises.com.usersapp.model.User

data class UsersResponse(val results: List<User>, val info: Info)