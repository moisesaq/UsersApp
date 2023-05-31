package moises.com.usersapp.model

import com.google.gson.Gson

data class Picture(val large: String?, val medium: String?, val thumbnail: String?) {

    override fun toString(): String = Gson().toJson(this)
}