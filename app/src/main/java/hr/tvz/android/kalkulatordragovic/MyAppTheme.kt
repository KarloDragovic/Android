package hr.tvz.android.kalkulatordragovic

import android.content.Context
import com.dolatkia.animatedThemeManager.AppTheme

interface MyAppTheme : AppTheme {
    fun firstActivityBackgroundColor(context: Context): Int
    fun firstActivityTextColor(context: Context): Int
    fun firstActivityIconColor(context: Context): Int
}