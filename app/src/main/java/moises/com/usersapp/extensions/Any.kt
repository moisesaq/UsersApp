package moises.com.usersapp.extensions

inline fun <reified T> Any?.tryToCast(defaultValue: T) = if (this is T) this else defaultValue