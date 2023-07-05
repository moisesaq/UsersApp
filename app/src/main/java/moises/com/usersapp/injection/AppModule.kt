package moises.com.usersapp.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import moises.com.usersapp.repository.Repository
import moises.com.usersapp.repository.RepositoryContract
import moises.com.usersapp.repository.base.ApiService
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
    fun provideRepository(api: ApiService): RepositoryContract = Repository(api)

    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO
}
