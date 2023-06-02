package moises.com.usersapp.repository

import com.google.common.truth.Truth.assertWithMessage
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import moises.com.usersapp.model.User
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class RepositoryTest {
    private val repository: RepositoryContract = mockk()
    private val user: User = mockk()

    @Test
    fun `repository returns at least one user`() {
        // runBlocking { Mockito.`when`(repository.getUsers(0, 10)).thenReturn(listOf(User.testUser())) }
        coEvery { repository.getUsers(any(), any()) } returns listOf(user)
        runBlocking {
            assertWithMessage("Repository has returned one user").that(repository.getUsers(0, 10)).hasSize(1)
        }
    }

    @Test
    fun `repository throws an exception`() {
        val exception = Exception("Error :(")
        coEvery { repository.getUsers(any(), any()) } throws exception
        // runBlocking { Mockito.`when`(repository.getUsers(0, 10)).thenThrow(Exception::class.java) }
        val exceptionCaptured = assertThrows(Exception::class.java) {
            runBlocking {
                repository.getUsers(0, 10)
            }
        }
        assertWithMessage("Repository has thrown exception").that(exceptionCaptured).isEqualTo(exception)
    }
}