package moises.com.usersapp.repository

import com.google.common.truth.Truth.assertWithMessage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import moises.com.usersapp.model.User
import moises.com.usersapp.repository.base.ApiService
import moises.com.usersapp.repository.response.UsersResponse
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

internal class RepositoryTest {
    private val apiService: ApiService = mockk()
    private val user: User = mockk()
    private lateinit var repository: RepositoryContract
    private lateinit var response: UsersResponse

    @Before
    fun setup() {
        repository = Repository(apiService)
        response = UsersResponse(listOf(user), mockk())
    }

    @Test
    fun `repository returns at least one user`() {
        // runBlocking { Mockito.`when`(repository.getUsers(0, 10)).thenReturn(listOf(User.testUser())) }
        coEvery { apiService.getUserList(any(), any()) } returns response
        // coEvery { repository.getUsers(any(), any()) } returns listOf(user)
        runBlocking {
            val users = repository.getUsers(0, 10)
            assertWithMessage("Repository has returned one user").that(users.first()).isEqualTo(response.results.first())
        }
        coVerify {
            apiService.getUserList(0, 10)
        }
    }

    @Test
    fun `repository throws an exception`() {
        val exception = Exception("Error :(")
        coEvery { apiService.getUserList(any(), any()) } throws exception
        // runBlocking { Mockito.`when`(repository.getUsers(0, 10)).thenThrow(Exception::class.java) }
        val exceptionCaptured = assertThrows(Exception::class.java) {
            runBlocking {
                repository.getUsers(0, 10)
            }
        }
        assertWithMessage("Repository has thrown exception").that(exceptionCaptured).isEqualTo(exception)
        coVerify(exactly = 1) {
            apiService.getUserList(0, 10)
        }
    }

    @Test
    fun `repository is calling private function`() {
        // coEvery { apiService.getUserList(any(), any()) } returns response
        coEvery { repository.getUsers(0, 10) } returns response.results
        // every { repository["showResponse"](response) } returns Unit
        runBlocking { repository.getUsers(0, 10) }
        /*verify {
            repository["showResponse"](response)
        }*/
        coVerify {
            apiService.getUserList(0, 10)
        }

    }
}