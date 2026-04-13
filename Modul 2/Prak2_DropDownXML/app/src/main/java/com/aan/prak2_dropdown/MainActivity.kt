package com.aan.prak2_dropdown

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.aan.prak2_dropdown.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    // Inisialisasi
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val density = resources.displayMetrics.density
            val padding24dp = (24 * density).toInt()

            v.setPadding(
                systemBars.left + padding24dp,
                systemBars.top + padding24dp,
                systemBars.right + padding24dp,
                systemBars.bottom + padding24dp
            )
            insets
        }

        setupTipDropdown()
        setupListeners()
        calculateTip()
    }

    private fun setupTipDropdown() {
        // Daftar pilihan persen
        val options = listOf("15%", "18%", "20%")
        val adapter = ArrayAdapter(this, R.layout.list_item, options)
        binding.autoCompleteTip.setAdapter(adapter)

        binding.autoCompleteTip.setOnItemClickListener { _, _, _, _ ->
            calculateTip()
        }
    }

    private fun setupListeners() {
        binding.editBillAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateTip()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Switch Round Up
        binding.roundUpSwitch.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }
    }

    private fun calculateTip() {
        // 1. Input
        val stringInTextField = binding.editBillAmount.text.toString()
        val cost = stringInTextField.toDoubleOrNull() ?: 0.0

        // 2. Hitung Otomatis
        val selectedTipString = binding.autoCompleteTip.text.toString().replace("%", "")
        val tipPercentage = selectedTipString.toDoubleOrNull() ?: 15.0
        var tip = tipPercentage / 100 * cost

        // 4. Cek Round Up
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }

        // 5. Tampilkan
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}