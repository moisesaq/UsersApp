package moises.com.usersapp.ui.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import moises.com.usersapp.R
import moises.com.usersapp.databinding.ActivityMainBinding
import moises.com.usersapp.model.User
import moises.com.usersapp.ui.main.users.UsersFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val eventViewModel: MainEventViewModel by viewModels()
    private var twoPanels: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }

    private fun setUp() {
        setSupportActionBar(binding.toolbar)
        twoPanels = resources.getBoolean(R.bool.isTablet)
        replaceFragment(R.id.main_container, UsersFragment.newInstance())
        eventViewModel.event.observe(this) { if (it is MainEventViewModel.MainEvent.ShowUserDetail) showUserDetail(it.user) }
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
}
