package moises.com.usersapp.repository

import io.reactivex.Single
import moises.com.usersapp.repository.response.UserList

interface RepositoryContract {

    fun getUsers(page: Int, result: Int, seed: String): Single<UserList>
}

