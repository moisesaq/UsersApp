package moises.com.usersapp.model

import com.google.gson.Gson

data class Picture(val large: String?) {

    fun safePicture() = getSafePicture()

    private fun getSafePicture() = large ?: ""

    override fun toString(): String = Gson().toJson(this)
}