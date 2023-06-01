package moises.com.usersapp.repository

import moises.com.usersapp.model.*

class MockRepositoryError: RepositoryContract {

    override suspend fun getUsers(page: Int, result: Int): List<User> {
        throw Exception("Something went wrong!")
    }
}