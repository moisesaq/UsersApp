package moises.com.usersapp.repository.service

import io.reactivex.Single
import moises.com.usersapp.repository.response.UserList

interface ServiceContract {

    fun getUsers(page: Int, result: Int, seed: String): Single<UserList>
}
