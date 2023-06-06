package moises.com.usersapp.ui.main.users

import com.google.common.truth.Truth.assertWithMessage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import moises.com.usersapp.MainDispatcherRule
import moises.com.usersapp.model.User
import moises.com.usersapp.repository.MockRepositoryError
import moises.com.usersapp.repository.MockRepositorySuccess
import moises.com.usersapp.ui.base.State
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class Users2ViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var usersViewModel: UsersViewModel
    private lateinit var usersViewModel2: UsersViewModel
    private lateinit var states: MutableList<State>

    @get:Rule
    var instantExecutorRule = MainDispatcherRule()

    @Before
    fun setup() {
        states = mutableListOf()
        usersViewModel = UsersViewModel(MockRepositorySuccess(), testDispatcher)
        usersViewModel.state.observeForever(states::add)
        usersViewModel2 = UsersViewModel(MockRepositoryError(), testDispatcher)
        usersViewModel2.state.observeForever(states::add)
    }

    @Test
    fun userListHasBeenLoaded() {
        usersViewModel.loadUsers(0, 10)
        assertWithMessage("Users has been loaded successfully").that(usersViewModel.isEmpty).isFalse()
    }

    @Test
    fun `states has been executed correctly when users loaded successfully`() {
        usersViewModel.loadUsers(0, 10)
        assertWithMessage("Start loading...").that(states[0]).isEqualTo(State.Loading(true))
        assertWithMessage("Users loaded successfully").that(states[1]).isEqualTo(State.Success(listOf(User.testUser())))
        assertWithMessage("Finish loading").that(states[2]).isEqualTo(State.Loading(false))
    }

    @Test
    fun `states has been executed correctly when users failure load`() {
        val errorMessage = "Something went wrong!"
        usersViewModel2.loadUsers(0, 10)
        assertWithMessage("Start loading...").that(states[0]).isEqualTo(State.Loading(true))
        // assertWithMessage("Users failure load").that(states[1]).isEqualTo(State.Error(errorMessage))
        // assertWithMessage("Users failure load").that(states[1]).isInstanceOf(State.Error::class.java)
        assertWithMessage("Finish loading").that(states[2]).isEqualTo(State.Loading(false))
    }
}