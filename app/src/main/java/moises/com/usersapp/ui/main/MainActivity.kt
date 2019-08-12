package moises.com.usersapp.ui.main

import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import javax.inject.Inject

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import moises.com.usersapp.R
import moises.com.usersapp.model.User
import moises.com.usersapp.ui.main.users.UsersFragment

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val eventViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProviders.of(this, viewModelFactory).get(MainEventViewModel::class.java)
    }

    private var twoPanels: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUp()
    }

    private fun setUp() {
        setSupportActionBar(toolbar)
        twoPanels = resources.getBoolean(R.bool.isTablet)
        replaceFragment(R.id.main_container, UsersFragment.newInstance())
        eventViewModel.event.observe(this, Observer { if (it is ShowUserDetail) showUserDetail(it.user) })
    }

    private fun replaceFragment(containerId: Int, fragment: Fragment, stack: Boolean = false) {
        supportFragmentManager.beginTransaction().run {
            replace(containerId, fragment)
            if (stack)
                addToBackStack(fragment::class.java.name)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_user_list, menu)
        return true
    }

    private fun showUserDetail(user: User) {
        if (twoPanels) {
            replaceFragment(R.id.detail_container, UserDetailFragment.newInstance(user))
        } else {
            replaceFragment(R.id.main_container, UserDetailFragment.newInstance(user), true)
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? = injector
}
