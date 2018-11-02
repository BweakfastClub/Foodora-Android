package club.bweakfast.foodora.util

import android.content.Context
import android.os.Build
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.Toast
import club.bweakfast.foodora.R
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

/**
 * Created by silve on 3/2/2018.
 */

private val emptyAnimationHandler: (FragmentTransaction) -> Unit = {}

fun FragmentActivity.showFragment(
    fragment: Fragment,
    @IdRes containerID: Int = R.id.container,
    name: String? = null,
    setAnimations: (FragmentTransaction) -> Unit = emptyAnimationHandler
) {
    val transaction = supportFragmentManager.beginTransaction()

    setAnimations(transaction)
    transaction.replace(containerID, fragment)
    name?.let { transaction.addToBackStack(it) }
    transaction.commit()
}

fun PreferenceActivity.showFragment(
    fragment: PreferenceFragment,
    @IdRes containerID: Int = R.id.container,
    name: String? = null
) {
    val transaction = fragmentManager.beginTransaction()

    transaction.replace(containerID, fragment)
    name?.let { transaction.addToBackStack(it) }
    transaction.commit()
}

fun EditText.listenForChanges(): Flowable<String> {
    var currentQuery = ""
    return Flowable.create({
        this.addTextChangedListener(object : TextListener() {
            override fun afterTextChanged(s: Editable) {
                super.afterTextChanged(s)
                val query = s.toString()
                if (currentQuery != query) {
                    currentQuery = query
                    it.onNext(query)
                }
            }
        })
    }, BackpressureStrategy.DROP)
}

fun showDarkStatusIcons(decorView: View, show: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (show) decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        else decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

fun showView(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

fun Int.toDP(context: Context) = this.toFloat().toDP(context).toInt()

fun Float.toDP(context: Context) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

fun Context.toast(@StringRes resId: Int) = toast(getString(resId))

fun Context.toast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.longToast(@StringRes resId: Int) = longToast(getString(resId))

fun Context.longToast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

open class TextListener : TextWatcher {
    override fun afterTextChanged(s: Editable) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, end: Int, count: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, end: Int, count: Int) {}
}