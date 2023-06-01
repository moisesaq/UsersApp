package moises.com.usersapp.repository.response

import com.google.common.truth.Truth.assertWithMessage
import moises.com.usersapp.model.Login
import moises.com.usersapp.model.Name
import moises.com.usersapp.model.Picture
import moises.com.usersapp.model.User
import org.junit.Before
import org.junit.Test

class UsersResponseTest {
    private lateinit var info: Info

    @Before
    fun setup() {
        info = Info(0, 0)
    }

    @Test
    fun validateIfResultIsEmpty() {
        val response = UsersResponse(emptyList(), info)
        assertWithMessage("Result of users is empty").that(response.results).isEmpty()
    }

    @Test
    fun validateIfResultIsNotEmpty() {
        val user = User(Login("username"), Name("title", "", ""),
            "email", Picture(""))
        val response = UsersResponse(listOf(user), info)
        assertWithMessage("Result of users is NOT empty").that(response.results).isNotEmpty()
    }
}