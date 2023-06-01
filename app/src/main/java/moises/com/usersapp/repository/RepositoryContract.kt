package moises.com.usersapp.repository

import moises.com.usersapp.model.User

interface RepositoryContract {

    suspend fun getUsers(page: Int, result: Int): List<User>
}

