package moises.com.usersapp.model

import com.google.gson.Gson
import java.io.Serializable

data class Name(
    val title: String,
    val first: String,
    val last: String) {

    override fun toString(): String = Gson().toJson(this)
}