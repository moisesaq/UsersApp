package moises.com.usersapp.repository

import io.reactivex.Single
import moises.com.usersapp.model.User
import moises.com.usersapp.model.UserList
import moises.com.usersapp.repository.base.ApiService
import moises.com.usersapp.repository.service.Service
import moises.com.usersapp.repository.service.ServiceContract
import java.util.*
import javax.inject.Inject

class Repository
    @Inject
    constructor(private val service: ServiceContract): RepositoryContract {

    override fun getUsers(page: Int, result: Int, seed: String): Single<UserList> {
        return service.getUsers(page, result, seed)
    }
}
