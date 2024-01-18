package code.wave.chapter3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import code.wave.chapter3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    val inputEditText = binding.inputEditText
    val outputTextView = binding.outputTextView
    val inputUnitTextView = binding.inputUnitTextView
    val outputUnitTextView = binding.outputUnitTextView
  }
}