package top.wefor.randompicker

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created on 2018/7/28.
 * @author ice
 */
fun showTips(triggerView: View, text: CharSequence) {
    val snackbar = Snackbar.make(triggerView, text, 2000)
    val view = snackbar.view
    view.setBackgroundResource(R.color.colorPrimary)
    snackbar.show()
}