package moises.com.usersapp.repository.response

import com.google.gson.Gson
import moises.com.usersapp.model.User

data class UserList(val results: List<User>, val info: Info) {

    override fun toString(): String = Gson().toJson(this)
}