package moises.com.usersapp.injection.app

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import moises.com.usersapp.injection.users.UsersActivityModule
import moises.com.usersapp.ui.UsersApp

@Singleton
@Component(modules = [AppModule::class, NetModule::class, ViewModelModule::class,
    UsersActivityModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(usersApp: UsersApp): Builder

        fun build(): AppComponent
    }

    fun inject(usersApp: UsersApp)
}
