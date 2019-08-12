package moises.com.usersapp.injection.users.fragments

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import moises.com.usersapp.injection.scopes.ScopeFragment
import moises.com.usersapp.ui.main.UserDetailFragment

@ScopeFragment
@Subcomponent(modules = [UserDetailFragmentModule::class])
interface UserDetailFragmentComponent : AndroidInjector<UserDetailFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<UserDetailFragment>()
}

@Module
abstract class UserDetailFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(UserDetailFragment::class)
    internal abstract fun bindFragmentUserDetailInjectorFactory(
            builder: UserDetailFragmentComponent.Builder): AndroidInjector.Factory<out Fragment>
}
