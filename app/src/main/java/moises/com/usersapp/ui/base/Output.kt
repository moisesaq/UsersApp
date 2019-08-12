package moises.com.usersapp.ui.base

import androidx.lifecycle.MutableLiveData
import moises.com.usersapp.extensions.SingleLiveEvent

open class Output<S, E> {
    val loading by lazy { SingleLiveEvent<Boolean>() }
    val success by lazy { MutableLiveData<S>() }
    val onComplete by lazy { SingleLiveEvent<Any>() }
    val error by lazy { SingleLiveEvent<E>() }
    val isDataValid by lazy { SingleLiveEvent<Boolean>() }
}