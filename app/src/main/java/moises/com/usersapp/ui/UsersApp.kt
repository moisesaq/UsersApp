package moises.com.usersapp.ui

import android.app.Activity
import android.app.Application

import javax.inject.Inject

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import moises.com.usersapp.injection.app.DaggerAppComponent
import timber.log.Timber

class UsersApp : Application(), HasActivityInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        setUpDagger()
        Timber.plant(Timber.DebugTree())
    }

    private fun setUpDagger() {
        val appComponent = DaggerAppComponent
                .builder()
                .application(this)
                .build()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return injector
    }
}
