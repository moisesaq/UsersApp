package moises.com.usersapp.repository

import moises.com.usersapp.model.User
import moises.com.usersapp.repository.base.ApiService
import moises.com.usersapp.repository.response.UsersResponse
import javax.inject.Inject

class Repository
@Inject
constructor(private val api: ApiService) : RepositoryContract {

    override suspend fun getUsers(page: Int, result: Int): List<User> {
        val response = api.getUserList(page, result)
        showResponse(response)
        return response.results
    }

    private fun showResponse(response: UsersResponse) {
    }


}
