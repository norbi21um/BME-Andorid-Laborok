package hu.bme.aut.workplaceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.workplaceapp.adapter.ProfilePageAdapter
import hu.bme.aut.workplaceapp.data.DataManager
import hu.bme.aut.workplaceapp.databinding.ActivityHolidayBinding

class HolidayActivity : AppCompatActivity(), DatePickerDialogFragment.OnDateSelectedListener {
    private lateinit var binding : ActivityHolidayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHolidayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTakeHoliday.setOnClickListener {
            if(DataManager.remainingHolidays >0)
                DatePickerDialogFragment().show(supportFragmentManager, "DATE_TAG")
        }
        loadHolidays()
    }

    private fun loadHolidays(){
        val entries = listOf(
            PieEntry(DataManager.holidays.toFloat(), "Taken"),
            PieEntry(DataManager.remainingHolidays.toFloat(), "Remaining")
        )

        val dataSet = PieDataSet(entries, "Holidays")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = PieData(dataSet)
        binding.chartHoliday.data = data
        binding.chartHoliday.invalidate()
    }

    override fun onDateSelected(year: Int, month: Int, day: Int) {
        val numHolidays = DataManager.holidays
        if (DataManager.remainingHolidays > 0){
            DataManager.holidays = numHolidays + 1
        }
        loadHolidays()
    }
}