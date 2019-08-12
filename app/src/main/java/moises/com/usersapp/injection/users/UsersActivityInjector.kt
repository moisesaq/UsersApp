package moises.com.usersapp.injection.users

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import moises.com.usersapp.injection.scopes.ScopeActivity
import moises.com.usersapp.injection.users.fragments.UserDetailFragmentComponent
import moises.com.usersapp.injection.users.fragments.UserDetailFragmentModule
import moises.com.usersapp.injection.users.fragments.UsersFragmentComponent
import moises.com.usersapp.injection.users.fragments.UsersFragmentModule
import moises.com.usersapp.ui.main.MainActivity

@ScopeActivity
@Subcomponent(modules = [UsersActivityModule::class, UsersFragmentModule::class, UserDetailFragmentModule::class])
interface UsersActivityComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}

@Module(subcomponents = [UsersFragmentComponent::class, UserDetailFragmentComponent::class])
abstract class UsersActivityModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract fun bindActivityUsersActivityInjectorFactory(
            builder: UsersActivityComponent.Builder): AndroidInjector.Factory<out Activity>
}
