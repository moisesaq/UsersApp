package moises.com.usersapp.injection.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import moises.com.usersapp.injection.viewmodel.ViewModelFactory
import moises.com.usersapp.injection.viewmodel.ViewModelKey
import moises.com.usersapp.ui.main.MainEventViewModel
import moises.com.usersapp.ui.main.users.UsersViewModel

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainEventViewModel::class)
    internal abstract fun provideMainEventViewModell(viewModel: MainEventViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UsersViewModel::class)
    internal abstract fun provideUsersViewModel(viewModel: UsersViewModel): ViewModel
}