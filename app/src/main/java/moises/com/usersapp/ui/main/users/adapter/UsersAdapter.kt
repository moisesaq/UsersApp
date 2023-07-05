package moises.com.usersapp.ui.main.users.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import moises.com.usersapp.R
import moises.com.usersapp.model.User
import moises.com.usersapp.tools.LayoutType
import timber.log.Timber
import javax.inject.Inject

class UsersAdapter(private val onTap: (User) -> Unit): RecyclerView.Adapter<UserViewHolder>() {
    private val users = mutableListOf<User>()
    private var layoutType: LayoutType? = null

    init {
        this.layoutType = LayoutType.LIST
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = when (layoutType) {
            LayoutType.LIST -> LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
            LayoutType.GRID -> LayoutInflater.from(parent.context).inflate(R.layout.user_grid_item, parent, false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        }
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position], onTap)
    }

    override fun getItemCount(): Int = users.size

    fun addItems(users: List<User>) {
        Timber.e(":-> Add items: ${users.size}")
        this.users.clear()
        this.users.addAll(users)
        notifyItemRangeRemoved(0, this.users.size)
    }

    fun setLayoutManagerType(layoutType: LayoutType) {
        this.layoutType = layoutType
    }
}
