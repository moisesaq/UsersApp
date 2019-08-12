package moises.com.usersapp.extensions

import android.view.View
import android.view.View.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addAdapter(adapter: RecyclerView.Adapter<*>) {
    layoutManager = LinearLayoutManager(context)
    this.adapter = adapter
}

fun View.getString(stringId: Int): String {
    return context.getString(stringId)
}

fun View.findColor(colorId: Int): Int {
    return ContextCompat.getColor(context, colorId)
}

fun View.isVisible(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

fun View.isHidden(hidden: Boolean) {
    visibility = if (hidden) INVISIBLE else VISIBLE
}

fun View.rotate(degrees: Float) {
    this.animate().rotation(degrees)
}