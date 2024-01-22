package code.wave.chapter4

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import code.wave.chapter4.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
  private lateinit var binding: ActivityEditBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityEditBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
      this, R.array.blood_types, android.R.layout.simple_list_item_1
    )

    binding.birthdateLayer.setOnClickListener {
      val listener = OnDateSetListener{ _, year, month, dayOfMonth ->
        binding.birthdateValueTextView.text = "$year-${month.inc()}-$dayOfMonth"
      }
      DatePickerDialog(this, listener, 2000, 1, 1).show()
    }

    binding.warningCheckBox.setOnCheckedChangeListener { _, isChecked ->
      binding.warningEditTextView.isVisible = isChecked
    }

    binding.warningEditTextView.isVisible = binding.warningCheckBox.isChecked

  }
}