package moises.com.usersapp.ui.main.users.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.siyamed.shapeimageview.RoundedImageView
import com.squareup.picasso.Picasso
import moises.com.usersapp.R
import moises.com.usersapp.model.User

data class UserViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private var mImage: RoundedImageView = view.findViewById(R.id.image_user)
    private var mTitle: TextView = view.findViewById(R.id.name_user)

    fun bind(user: User, onTap: (User) -> Unit) {
        Picasso.with(view.context)
                .load(user.picture.large)
                .placeholder(R.mipmap.default_profile)
                .error(R.mipmap.default_profile)
                .into(mImage)
        mTitle.text = user.name.first
        view.setOnClickListener { onTap(user) }
    }
}