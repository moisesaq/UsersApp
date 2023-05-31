package moises.com.usersapp.repository.response

import com.google.gson.Gson

data class Info(val results: Int, val page: Int) {

    override fun toString(): String = Gson().toJson(this)
}