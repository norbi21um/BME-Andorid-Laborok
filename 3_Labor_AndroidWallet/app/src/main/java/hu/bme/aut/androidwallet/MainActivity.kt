package hu.bme.aut.androidwallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.androidwallet.databinding.ActivityMainBinding
import hu.bme.aut.androidwallet.databinding.SalaryRowBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rowBinding: SalaryRowBinding
    private var sum:Int = 0
    private var negate:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.saveButton.setOnClickListener {
            if (binding.salaryName.text.toString().isEmpty() || binding.salaryAmount.text.toString().isEmpty()) {
                //Toast.makeText(this, R.string.warn_message, Toast.LENGTH_LONG).show()
                val snackbar:Snackbar = Snackbar.make(it, R.string.warn_message, Snackbar.LENGTH_LONG)
                snackbar.show()
                return@setOnClickListener
            }
            rowBinding = SalaryRowBinding.inflate(layoutInflater)

            rowBinding.salaryDirectionIcon.setImageResource(if (binding.expenseOrIncome.isChecked) R.drawable.expense else R.drawable.income)
            rowBinding.rowSalaryName.text = binding.salaryName.text.toString()
            rowBinding.rowSalaryAmount.text = binding.salaryAmount.text.toString()
            binding.listOfRows.addView(rowBinding.root)


            //Visibility vissza állítása az összegzőnek
            if(binding.expenseOrIncome.isChecked)
                negate = -1
            else
                negate = 1

            var tmp:Int = binding.salaryAmount.text.toString().toInt()
            sum = sum + (negate*tmp)
            binding.sumOfValues.text=sum.toString()
            binding.sumOfValues.isVisible=true

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all -> {
                binding.listOfRows.removeAllViews()
                sum = 0
                binding.sumOfValues.isVisible=false
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}