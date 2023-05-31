package moises.com.usersapp.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import moises.com.usersapp.repository.Repository
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.repository.base.ApiService
import moises.com.usersapp.repository.service.Service
import moises.com.usersapp.repository.service.ServiceContract
import moises.com.usersapp.ui.UsersApp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContext(@ApplicationContext app: Context): UsersApp = app as UsersApp

    @Singleton
    @Provides
    fun provideService(api: ApiService): ServiceContract = Service(api)

    @Singleton
    @Provides
    fun provideRepository(service: ServiceContract): RepositoryContract = Repository(service)
}
