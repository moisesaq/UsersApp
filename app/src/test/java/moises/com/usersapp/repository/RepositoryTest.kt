package moises.com.usersapp.repository

import com.google.common.truth.Truth.assertWithMessage
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import moises.com.usersapp.model.User
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class RepositoryTest {

    @Mock
    lateinit var repository: RepositoryContract

    @Before
    fun setup() {
        repository = Mockito.mock(RepositoryContract::class.java)
    }

    @Test
    fun `repository returns at least one user`() {
        runBlocking {
            Mockito.`when`(repository.getUsers(0, 10)).thenReturn(listOf(User.testUser()))
        }
        runBlocking {
            assertWithMessage("Repository has returned one user").that(repository.getUsers(0, 10)).hasSize(1)
        }
    }

    @Test
    fun `repository throws an exception`() {
        val exception = Exception("Error :(")
        runBlocking {
            Mockito.`when`(repository.getUsers(0, 10)).thenThrow(Exception::class.java)
        }
        val exceptionCaptured = assertThrows(Exception::class.java) {
            runBlocking {
                repository.getUsers(0, 10)
            }
        }
        assertWithMessage("Repository has thrown exception").that(exceptionCaptured).isEqualTo(exception)

    }
}