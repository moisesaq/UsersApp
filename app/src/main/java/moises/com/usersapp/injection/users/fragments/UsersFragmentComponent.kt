package moises.com.usersapp.injection.users.fragments

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import moises.com.usersapp.injection.scopes.ScopeFragment
import moises.com.usersapp.ui.main.users.UsersFragment

@ScopeFragment
@Subcomponent(modules = [UsersFragmentModule::class])
interface UsersFragmentComponent : AndroidInjector<UsersFragment> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<UsersFragment>()
}

@Module
abstract class UsersFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(UsersFragment::class)
    internal abstract fun bindFragmentUsersInjectorFactory(
            builder: UsersFragmentComponent.Builder): AndroidInjector.Factory<out Fragment>
}
