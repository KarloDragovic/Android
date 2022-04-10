package hr.tvz.android.kalkulatordragovic

import android.content.Context
import androidx.core.content.ContextCompat

class DefaultTheme : MyAppTheme {
    override fun id(): Int { // set unique iD for each theme
        return 0
    }

    override fun firstActivityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.almostWhite)
    }

    override fun firstActivityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.black)
    }

    override fun firstActivityIconColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.darkGreen)
    }
}

class RedTheme : MyAppTheme {
    override fun id(): Int { // set unique iD for each theme
        return 1
    }

    override fun firstActivityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.almostWhite)
    }

    override fun firstActivityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.black)
    }
    override fun firstActivityIconColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.darkRed)
    }
}

class OrangeTheme : MyAppTheme {
    override fun id(): Int { // set unique iD for each theme
        return 2
    }

    override fun firstActivityBackgroundColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.almostWhite)
    }

    override fun firstActivityTextColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.almostWhite)
    }
    override fun firstActivityIconColor(context: Context): Int {
        return ContextCompat.getColor(context, R.color.darkOrange)
    }
}
