package code.wave.chapter7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import code.wave.chapter7.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
  private lateinit var binding: ActivityAddBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAddBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}