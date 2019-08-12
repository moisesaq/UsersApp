package moises.com.usersapp.ui.main.users.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import moises.com.usersapp.R
import moises.com.usersapp.model.User
import moises.com.usersapp.tools.LayoutType
import javax.inject.Inject

class UsersAdapter
    @Inject
    constructor(): RecyclerView.Adapter<UserViewHolder>() {
    private val users = mutableListOf<User>()
    private lateinit var onTap: (User) -> Unit
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
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun setLayoutManagerType(layoutType: LayoutType) {
        this.layoutType = layoutType
    }

    fun addOnTap(onTap: (User) -> Unit) {
        this.onTap = onTap
    }
}
