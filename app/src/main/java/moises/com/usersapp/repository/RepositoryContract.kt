package moises.com.usersapp.repository

import io.reactivex.Single
import moises.com.usersapp.model.User
import moises.com.usersapp.model.UserList

interface RepositoryContract {

    fun getUsers(page: Int, result: Int, seed: String): Single<UserList>
}

