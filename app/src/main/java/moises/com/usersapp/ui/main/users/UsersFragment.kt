package moises.com.usersapp.ui.main.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import moises.com.usersapp.R
import moises.com.usersapp.databinding.FragmentUsersBinding
import moises.com.usersapp.extensions.isVisible
import moises.com.usersapp.model.User
import moises.com.usersapp.tools.EndlessRecyclerOnScrollListener
import moises.com.usersapp.tools.LayoutType
import moises.com.usersapp.extensions.tryToCast
import moises.com.usersapp.ui.base.BaseFragment
import moises.com.usersapp.ui.base.State
import moises.com.usersapp.ui.main.MainEventViewModel
import moises.com.usersapp.ui.main.users.adapter.UsersAdapter
import timber.log.Timber

@AndroidEntryPoint
class UsersFragment : BaseFragment() {
    private lateinit var binding: FragmentUsersBinding

    private val usersViewModel: UsersViewModel by activityViewModels()
    private val eventViewModel: MainEventViewModel by activityViewModels()

    private lateinit var usersAdapter: UsersAdapter
    private lateinit var currentLayoutType: LayoutType
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var menuChangeView: MenuItem? = null
    private var page = 1

    companion object {
        fun newInstance(): UsersFragment = UsersFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        usersAdapter = UsersAdapter { eventViewModel.showUserDetail(it) }
        layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = usersAdapter
        loadUsers()
    }

    private fun loadUsers() {
        usersViewModel.state.observe(viewLifecycleOwner, this::onStateChanged)
        usersViewModel.loadUsers(page, 50)
    }

    private fun onStateChanged(state: State) {
        Timber.e(":-> onStateChanged: $state")
        when(state) {
            is State.Loading -> binding.progressBar.isVisible = state.isLoading
            is State.Success<*> -> usersAdapter.addItems(state.data.tryToCast(emptyList()))
            is State.Error -> showError(state.error)
        }
    }

    /*private fun setRecyclerViewLayoutManager(layoutType: LayoutType) {
        var scrollPosition = 0
        if (binding.recyclerView.layoutManager != null) {
            scrollPosition = (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        }

        when (layoutType) {
            LayoutType.LIST -> {
                layoutManager = LinearLayoutManager(activity)
                currentLayoutType = LayoutType.LIST
            }
            LayoutType.GRID -> {
                layoutManager = GridLayoutManager(activity, if (isTablet || isLandScape || isPhoneLarge) 3 else 2)
                currentLayoutType = LayoutType.GRID
            }
        }

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.scrollToPosition(scrollPosition)
        usersAdapter.setLayoutManagerType(currentLayoutType)
        binding.recyclerView.adapter = usersAdapter

        menuChangeView?.setIcon(currentLayoutType.icon)
        binding.recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener(layoutManager as LinearLayoutManager?, page) {
            override fun onLoadMore(currentPage: Int) {
                //mPresenter.loadUsers(currentPage, RESULTS, "");
                page = currentPage
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuChangeView = menu.findItem(R.id.action_change_view)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_view -> {
                setRecyclerViewLayoutManager(if (currentLayoutType == LayoutType.LIST)
                    LayoutType.GRID
                else
                    LayoutType.LIST)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showUsers(users: List<User>) {
        usersAdapter.addItems(users)
        if (isAdded && isTablet ) {
            //mListener.onLoadCompleted(users.get(0));
        }
    }*/
}
