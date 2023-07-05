package moises.com.usersapp.ui.main.users

import com.google.common.truth.Truth.assertWithMessage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import moises.com.usersapp.MainDispatcherRule
import moises.com.usersapp.repository.RepositoryContract
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class Users2ViewModelTest {
    private val repository: RepositoryContract = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var users2ViewModel: Users2ViewModel
    private lateinit var loadingStates: MutableList<Boolean>

    @get:Rule
    var instantTaskExecutorRule = MainDispatcherRule()

    @Before
    fun setup() {
        users2ViewModel = Users2ViewModel(repository, testDispatcher)
        loadingStates = mutableListOf()
        users2ViewModel.output.loading.observeForever(loadingStates::add)
    }

    @Test
    fun `state are being executed correctly`() {
        coEvery { repository.getUsers(any(), any()) } returns listOf(mockk())
        users2ViewModel.loadUsers(0, 10)
        coVerify {
            repository.getUsers(0, 10)
        }
        assertWithMessage("Loading states has been execute correctly").that(loadingStates).isEqualTo(listOf(true, false))
        assertWithMessage("User has been loaded successfully").that(users2ViewModel.output.success.value).isNotEmpty()
    }
}