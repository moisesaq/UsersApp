package moises.com.usersapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.fragment_detail_user.*
import moises.com.usersapp.R
import moises.com.usersapp.model.User

class UserDetailFragment : Fragment() {
    private var user: User? = null

    companion object {
        fun newInstance(user: User) = UserDetailFragment().apply {
            arguments = Bundle().apply { putSerializable("user", user) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            user = it.getSerializable("user") as User
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetail()
    }

    private fun showDetail() {
        user?.let {
            Picasso.with(context)
                    .load(it.picture.large)
                    .placeholder(R.mipmap.default_profile)
                    .error(R.mipmap.default_profile)
                    .into(imageUser)
            tivUserName.text1 = it.login.userName
            tivName.text1 = it.name.first
            tivLastName.text1 = it.name.last
            tivEmail.text1 = it.email
        }
    }


}
