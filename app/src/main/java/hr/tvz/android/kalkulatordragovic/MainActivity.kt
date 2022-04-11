package hr.tvz.android.kalkulatordragovic

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.dolatkia.animatedThemeManager.AppTheme
import com.dolatkia.animatedThemeManager.ThemeActivity
import com.dolatkia.animatedThemeManager.ThemeManager
import hr.tvz.android.kalkulatordragovic.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*
import kotlin.math.pow


class MainActivity : ThemeActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        loadLocale()
        setContentView(view)

        binding.izracunaj.setOnClickListener {

            val iznosKredita = binding.iznosKredita.text.toString()
            val trajanjeKredita = binding.trajanjeKredita.text.toString()
            val kamata = binding.kamata.text.toString()

            if (iznosKredita.isNotEmpty() && trajanjeKredita.isNotEmpty() && kamata.isNotEmpty()) {

                    if(iznosKredita.toInt() != 0 && kamata.toInt() != 0 && trajanjeKredita.toInt() != 0){

                        val iznosKreditaNum = iznosKredita.toDouble()
                        val trajanjeKreditaNum = trajanjeKredita.toDouble()
                        val kamataNum = kamata.toDouble() / 100.0

                        val anuitet = iznosKreditaNum * ((kamataNum * (1.0 + kamataNum).pow(trajanjeKreditaNum))/((1.0 + kamataNum).pow(trajanjeKreditaNum) - 1.0))
                        val ukupnaKamata = (anuitet * trajanjeKreditaNum) - iznosKreditaNum
                        val ukupniPovrat = anuitet * trajanjeKreditaNum

                        val format: NumberFormat = NumberFormat.getCurrencyInstance()
                        format.setMaximumFractionDigits(2)
                        format.setCurrency(Currency.getInstance("EUR"))


                        binding.mjesecnaRata.text = format.format(anuitet)

                        binding.ukupnaKamata.text = format.format(ukupnaKamata)

                        binding.ukupniPovrat.text = format.format(ukupniPovrat)

                    }else{
                        Toast.makeText(applicationContext, "Vrijednosti ne smiju biti nula", Toast.LENGTH_SHORT).show()
                    }
            }else if (iznosKredita.isEmpty() || trajanjeKredita.isEmpty() || kamata.isEmpty()){
                Toast.makeText(applicationContext, "Niste unjeli sve vrijendosti", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext, "Došlo je do pogreške", Toast.LENGTH_SHORT).show()
            }

            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)

        }

        binding.promjeniJezik.setOnClickListener{
            showPromjenaJezikaList()
        }

        binding.seekBoja.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seek: SeekBar?) {
            }

            override fun onStopTrackingTouch(seek: SeekBar?) {
                if (seek != null) {
                    if (seek.progress == 0){
                        ThemeManager.instance.changeTheme(RedTheme(), view)
                    }
                    if (seek.progress == 1){
                        ThemeManager.instance.changeTheme(DefaultTheme(), view)
                    }
                    if (seek.progress == 2){
                        ThemeManager.instance.changeTheme(OrangeTheme(), view)
                    }
                }
            }
        })

    }

    override fun syncTheme(appTheme: AppTheme) {
        val myAppTheme = appTheme as MyAppTheme
        // set background color
        binding.root.setBackgroundColor(myAppTheme.firstActivityBackgroundColor(this))
        binding.izracunaj.setBackgroundColor(myAppTheme.firstActivityIconColor(this))
        binding.promjeniJezik.setColorFilter(myAppTheme.firstActivityIconColor(this))
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(myAppTheme.firstActivityIconColor(this)))
        binding.seekBoja.progressDrawable.setColorFilter(myAppTheme.firstActivityIconColor(this), PorterDuff.Mode.SRC_IN)
        binding.seekBoja.thumb.setColorFilter(myAppTheme.firstActivityIconColor(this), PorterDuff.Mode.SRC_IN)
        binding.ukupniPovrat.setTextColor(myAppTheme.firstActivityIconColor(this))
        binding.ukupnaKamata.setTextColor(myAppTheme.firstActivityIconColor(this))
        binding.mjesecnaRata.setTextColor(myAppTheme.firstActivityIconColor(this))
        binding.iznosKredita.background.mutate().setColorFilter(myAppTheme.firstActivityIconColor(this), PorterDuff.Mode.SRC_IN)
        binding.kamata.background.mutate().setColorFilter(myAppTheme.firstActivityIconColor(this), PorterDuff.Mode.SRC_IN)
        binding.trajanjeKredita.background.mutate().setColorFilter(myAppTheme.firstActivityIconColor(this), PorterDuff.Mode.SRC_IN)
    }



    override fun getStartTheme(): AppTheme {
        return DefaultTheme()
    }

    private fun showPromjenaJezikaList() {
        val listJezika = arrayOf("Hrvatski", "Engleski", "Danski")

        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Izaberi jezik")
        mBuilder.setSingleChoiceItems(listJezika, -1){ dialog, which ->
            if(which == 0){
                setLocale("hr")
                recreate()
            }else if(which == 1){
                setLocale("en")
                recreate()
            }else if(which == 2){
                setLocale("da")
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun setLocale(s: String) {
        val locale = Locale(s)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", s)
        editor.apply()
    }

    private fun loadLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocale(language)
        }
    }
}