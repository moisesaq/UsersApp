package moises.com.usersapp.injection.app

import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import moises.com.usersapp.injection.users.UsersActivityComponent
import moises.com.usersapp.repository.base.ApiService
import moises.com.usersapp.repository.Repository
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.repository.service.Service
import moises.com.usersapp.repository.service.ServiceContract
import moises.com.usersapp.ui.UsersApp

@Module(subcomponents = [UsersActivityComponent::class])
class AppModule {
    @Singleton
    @Provides
    fun provideContext(usersApp: UsersApp): Context {
        return usersApp.applicationContext
    }

    @Singleton
    @Provides
    fun provideService(api: ApiService): ServiceContract {
        return Service(api)
    }

    @Singleton
    @Provides
    fun provideRepository(service: ServiceContract): RepositoryContract {
        return Repository(service)
    }
}
