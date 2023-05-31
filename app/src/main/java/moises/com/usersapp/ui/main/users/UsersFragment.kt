package moises.com.usersapp.ui.main.users

import android.os.Bundle

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import javax.inject.Inject

import dagger.android.support.AndroidSupportInjection
import moises.com.usersapp.R
import moises.com.usersapp.databinding.FragmentUsersBinding
import moises.com.usersapp.extensions.isVisible
import moises.com.usersapp.model.User
import moises.com.usersapp.tools.EndlessRecyclerOnScrollListener
import moises.com.usersapp.tools.LayoutType
import moises.com.usersapp.ui.base.BaseFragment
import moises.com.usersapp.ui.main.MainEventViewModel
import moises.com.usersapp.ui.main.users.adapter.UsersAdapter

class UsersFragment : BaseFragment() {
    private lateinit var binding: FragmentUsersBinding
    @Inject
    lateinit var usersAdapter: UsersAdapter
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UsersViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(UsersViewModel::class.java)
    }
    private lateinit var eventViewModel: MainEventViewModel

    private lateinit var currentLayoutType: LayoutType
    private var layoutManager: RecyclerView.LayoutManager? = null

    private var menuChangeView: MenuItem? = null
    private var page = 1

    companion object {
        fun newInstance(): UsersFragment = UsersFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        activity?.let {
            eventViewModel = ViewModelProviders.of(it, viewModelFactory).get(MainEventViewModel::class.java)
        }
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
        usersAdapter.addOnTap { eventViewModel.showUserDetail(it) }
        layoutManager = LinearLayoutManager(context)
        binding.recyclerView.apply {
            layoutManager = layoutManager
            adapter = usersAdapter
        }
        loadUserList()
    }

    private fun loadUserList() {
        viewModel.output.loading.observe(viewLifecycleOwner) { binding.loading.isVisible(it) }
        viewModel.output.success.observe(viewLifecycleOwner) { usersAdapter.addItems(it) }
        viewModel.output.error.observe(viewLifecycleOwner) { showMessageInToast(it) }
        viewModel.output.isDataValid.observe(viewLifecycleOwner) { if (!it) showMessageInToast("Empty") }
        viewModel.loadUsers(page, 10, "")
    }

    private fun setRecyclerViewLayoutManager(layoutType: LayoutType) {
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
    }
}
