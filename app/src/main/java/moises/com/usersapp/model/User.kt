package moises.com.usersapp.model

import com.google.gson.Gson

data class User(
    val login: Login,
    val name: Name,
    val email: String,
    val picture: Picture
) {

    override fun toString(): String = Gson().toJson(this)
}