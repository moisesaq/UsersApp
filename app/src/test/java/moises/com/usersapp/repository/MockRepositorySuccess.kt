package moises.com.usersapp.repository

import moises.com.usersapp.model.*

class MockRepositorySuccess: RepositoryContract {

    override suspend fun getUsers(page: Int, result: Int): List<User> {
        val user = User(Login("username"), Name("name", "", ""), "", Picture(""))
        return listOf(user)
    }
}