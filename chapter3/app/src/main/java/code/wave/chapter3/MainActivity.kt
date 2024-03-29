package code.wave.chapter3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import code.wave.chapter3.databinding.ActivityMainBinding
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private var cmToM = true
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    val inputEditText = binding.inputEditText
    val inputUnitTextView = binding.inputUnitTextView
    val outputTextView = binding.outputTextView
    val outputUnitTextView = binding.outputUnitTextView
    val swapImageButton = binding.swapImageButton


    var inputNumber = 0

    inputEditText.addTextChangedListener {text ->
      inputNumber = if (text.isNullOrEmpty()) {
        0
      } else {
        text.toString().toInt()
      }

      if (cmToM) {
        outputTextView.text = inputNumber.times(0.01).toString()
      } else {
        outputTextView.text = inputNumber.times(100).toString()
      }
    }

    swapImageButton.setOnClickListener {
      cmToM = cmToM.not()
      if (cmToM){
        inputUnitTextView.text = "cm"
        outputUnitTextView.text = "m"
        outputTextView.text = inputNumber.times(0.01).toString()
      } else {
        inputUnitTextView.text = "m"
        outputUnitTextView.text = "cm"
        outputTextView.text = inputNumber.times(100).toString()
      }
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putBoolean("cmToM", cmToM)
    super.onSaveInstanceState(outState)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    cmToM = savedInstanceState.getBoolean("cmToM")
    binding.inputUnitTextView.text = if (cmToM) "cm" else "m"
    binding.outputUnitTextView.text = if (cmToM) "m" else "cm"
    super.onRestoreInstanceState(savedInstanceState)
  }
}