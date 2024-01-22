package code.wave.chapter4

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import code.wave.chapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.goInputActivityButton.setOnClickListener {
      val intent = Intent(this,EditActivity::class.java)
      startActivity(intent)
    }
    getDataUiUpdate()
  }

  override fun onResume() {
    super.onResume()
    getDataUiUpdate()
  }

  private fun getDataUiUpdate(){
    with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)){
      binding.nameValueTextView.text = getString(NAME, "미정")
      binding.birthdateValueTextView.text = getString(BIRTHDATE, "미정")
      binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
      binding.phoneValueTextView.text = getString(EMERGENCY_CONTACT, "미정")
      val warning = getString(WARNING, "")
      binding.warningTextView.isVisible = warning.isNullOrEmpty().not()
      binding.warningValueTextView.isVisible = warning.isNullOrEmpty().not()
      if (!warning.isNullOrEmpty()){
        binding.warningValueTextView.text = warning
      }
    }
  }
}