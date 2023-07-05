package moises.com.usersapp.ui.main.users

import com.google.common.truth.Truth.assertWithMessage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import moises.com.usersapp.MainDispatcherRule
import moises.com.usersapp.model.User
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.ui.base.State
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository: RepositoryContract = mockk()
    private val user: User = mockk()
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var states: MutableList<State>

    @get:Rule
    var instantExecutorRule = MainDispatcherRule()

    @Before
    fun setup() {
        usersViewModel = UsersViewModel(repository, testDispatcher)
        states = mutableListOf()
        usersViewModel.state.observeForever(states::add)
    }

    @Test
    fun userListHasBeenLoaded() {
        coEvery { repository.getUsers(any(), any()) } returns listOf(user)
        usersViewModel.loadUsers(0, 10)
        assertWithMessage("Users has been loaded successfully").that(usersViewModel.isEmpty).isFalse()
    }

    @Test
    fun `states has been executed correctly when users loaded successfully`() {
        coEvery { repository.getUsers(any(), any()) } returns listOf(user)
        usersViewModel.loadUsers(0, 10)
        assertWithMessage("State loading...").that(states[0]).isEqualTo(State.Loading)
        assertWithMessage("Users loaded successfully").that(states[1]).isEqualTo(State.Success(listOf(user)))
        coVerify {
            repository.getUsers(0, 10)
        }
    }

    @Test
    fun `states has been executed correctly when users failure load`() {
        val exception = Exception("Something went wrong!")
        coEvery { repository.getUsers(any(), any()) } throws exception
        usersViewModel.loadUsers(0, 10)

        val expectedStates = listOf(State.Loading, State.Error(exception))
        assertWithMessage("States have been executed correctly").that(states).isEqualTo(expectedStates)
        coVerify {
            repository.getUsers(0, 10)
        }
    }
}