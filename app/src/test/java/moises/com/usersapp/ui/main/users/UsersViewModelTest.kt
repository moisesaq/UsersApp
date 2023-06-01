package moises.com.usersapp.ui.main.users

import com.google.common.truth.Truth.assertWithMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import moises.com.usersapp.MainDispatcherRule
import moises.com.usersapp.repository.MockRepositorySuccess
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var instantExecutorRule = MainDispatcherRule()

    @Test
    fun testUserListLoaded() = runBlocking {
        val usersViewModel = UsersViewModel(MockRepositorySuccess(), testDispatcher)
        usersViewModel.loadUsers(0, 10)
        assertWithMessage("Users has been loaded successfully").that(usersViewModel.isEmpty).isFalse()
    }
}