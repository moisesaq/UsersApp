package moises.com.usersapp.ui.base

import androidx.lifecycle.MutableLiveData

open class BaseOutput<E> {
    val loading by lazy { SingleLiveEvent<Boolean>() }
    val error by lazy { SingleLiveEvent<E>() }
}

class Output<S, E>: BaseOutput<E>() {
    val success by lazy { MutableLiveData<S>() }
}

class SimpleOutput<C, E>: BaseOutput<E>() {
    val completed by lazy { SingleLiveEvent<C>() }
}