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
        assertWithMessage("Start loading...").that(states[0]).isEqualTo(State.Loading(true))
        assertWithMessage("Users loaded successfully").that(states[1]).isEqualTo(State.Success(listOf(user)))
        assertWithMessage("Finish loading").that(states[2]).isEqualTo(State.Loading(false))
        coVerify {
            repository.getUsers(0, 10)
        }
    }

    @Test
    fun `states has been executed correctly when users failure load`() {
        val exception = Exception("Something went wrong!")
        coEvery { repository.getUsers(any(), any()) } throws exception
        usersViewModel.loadUsers(0, 10)

        assertWithMessage("Start loading...").that(states[0]).isEqualTo(State.Loading(true))
        assertWithMessage("Users failure load").that(states[1]).isEqualTo(State.Error(exception))
        // assertWithMessage("Users failure load").that(states[1]).isInstanceOf(State.Error::class.java)
        assertWithMessage("Finish loading").that(states[2]).isEqualTo(State.Loading(false))
        coVerify {
            repository.getUsers(0, 10)
        }
    }

    @Test
    fun `loading is calling setState function`() {
        coEvery { repository.getUsers(any(), any()) } returns listOf(user)
        // every { usersViewModel["handleLoading"](any()) } returns Unit
        // every { usersViewModel["handleLoading"](any()) } returns Unit
        // every { usersViewModel["handleLoading"](false) } returns Unit
        every { usersViewModel["setState"](State.Success(listOf(user))) } returns Unit
        usersViewModel.loadUsers(0, 10)
        verify(exactly = 1) {
            usersViewModel["setState"](State.Success(listOf(user)))
        }
    }
}