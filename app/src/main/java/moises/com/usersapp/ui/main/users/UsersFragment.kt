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
import androidx.lifecycle.Observer

import javax.inject.Inject

import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_users.*
import moises.com.usersapp.R
import moises.com.usersapp.extensions.isVisible
import moises.com.usersapp.model.User
import moises.com.usersapp.tools.EndlessRecyclerOnScrollListener
import moises.com.usersapp.tools.LayoutType
import moises.com.usersapp.ui.base.BaseFragment
import moises.com.usersapp.ui.main.MainEventViewModel
import moises.com.usersapp.ui.main.users.adapter.UsersAdapter

class UsersFragment : BaseFragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        usersAdapter.addOnTap { eventViewModel.showUserDetail(it) }
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = usersAdapter
        loadUserList()
    }

    private fun loadUserList() {
        viewModel.output.loading.observe(viewLifecycleOwner, Observer { loading.isVisible(it) })
        viewModel.output.success.observe(viewLifecycleOwner, Observer { usersAdapter.addItems(it) })
        viewModel.output.error.observe(viewLifecycleOwner, Observer { showMessageInToast(it) })
        viewModel.output.isDataValid.observe(viewLifecycleOwner, Observer { if (!it) showMessageInToast("Empty")})
        viewModel.loadUsers(page, 10, "")
    }

    private fun setRecyclerViewLayoutManager(layoutType: LayoutType) {
        var scrollPosition = 0
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
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

        recyclerView.layoutManager = layoutManager
        recyclerView.scrollToPosition(scrollPosition)
        usersAdapter.setLayoutManagerType(currentLayoutType)
        recyclerView.adapter = usersAdapter

        menuChangeView?.setIcon(currentLayoutType.icon)
        recyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener(layoutManager as LinearLayoutManager?, page) {
            override fun onLoadMore(currentPage: Int) {
                //mPresenter.loadUsers(currentPage, RESULTS, "");
                page = currentPage
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menuChangeView = menu!!.findItem(R.id.action_change_view)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
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
