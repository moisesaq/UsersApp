package moises.com.usersapp.repository.service

import io.reactivex.Single
import moises.com.usersapp.model.UserList
import moises.com.usersapp.repository.base.ApiService
import javax.inject.Inject

class Service
    @Inject
    constructor(private val api: ApiService): ServiceContract {

    override fun getUsers(page: Int, result: Int, seed: String): Single<UserList> {
        return api.getUserList(page, result, seed)
    }

}
