package moises.com.usersapp.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Login(val username: String) {

    override fun toString(): String = Gson().toJson(this)
}