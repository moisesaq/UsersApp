package moises.com.usersapp.repository

import com.google.common.truth.Truth.assertWithMessage
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class Repository2Test {
    private lateinit var repository: RepositoryContract
    private lateinit var repositoryWithError: RepositoryContract

    @Before
    fun setup() {
        repository = MockRepositorySuccess()
        repositoryWithError = MockRepositoryError()
    }

    @Test
    fun repositoryReturnsUsers() {
        runBlocking {
            assertWithMessage("Repository returns users").that(repository.getUsers(0, 0)).isNotEmpty()
        }
    }

    @Test
    fun repositoryThrowsException() {
        val exception = assertThrows(Exception::class.java) {
            runBlocking {
                repositoryWithError.getUsers(0, 0)
            }
        }
        assertWithMessage("Repository throws error").that(exception).hasMessageThat().contains("Something went wrong!")
    }
}