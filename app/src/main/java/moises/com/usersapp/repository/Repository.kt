package moises.com.usersapp.repository

import moises.com.usersapp.model.User
import moises.com.usersapp.repository.base.ApiService
import javax.inject.Inject

class Repository
    @Inject
    constructor(private val api: ApiService): RepositoryContract {

    override suspend fun getUsers(page: Int, result: Int): List<User> {
        return api.getUserList(page, result).results
    }
}
