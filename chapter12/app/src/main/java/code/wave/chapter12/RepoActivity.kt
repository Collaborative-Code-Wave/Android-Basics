package code.wave.chapter12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import code.wave.chapter12.databinding.ActivityRepoBinding

class RepoActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRepoBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRepoBinding.inflate(layoutInflater)
    setContentView(binding.root)
  }
}