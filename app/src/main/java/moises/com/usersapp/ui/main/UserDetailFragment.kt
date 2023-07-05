package moises.com.usersapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import moises.com.usersapp.R
import moises.com.usersapp.databinding.FragmentDetailUserBinding
import moises.com.usersapp.model.User

class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailUserBinding
    private var user: User? = null

    companion object {
        fun newInstance(user: User) = UserDetailFragment().apply {
            // arguments = Bundle().apply { putSerializable("user", user) }
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
        binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDetail()
    }

    private fun showDetail() = with(binding) {
        user?.let {
            Picasso.with(context)
                .load(it.picture.large)
                .placeholder(R.mipmap.default_profile)
                .error(R.mipmap.default_profile)
                .into(imageUser)
            tivUserName.text1 = it.login.username
            tivName.text1 = it.name.first
            tivLastName.text1 = it.name.last
            tivEmail.text1 = it.email
        }
    }
}
