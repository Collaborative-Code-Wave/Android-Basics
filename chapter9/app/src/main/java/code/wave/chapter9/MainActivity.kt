package code.wave.chapter9

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import code.wave.chapter9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.playButton.setOnClickListener { mediaPlayerPlay() }
    binding.stopButton.setOnClickListener { mediaPlayerStop() }
    binding.pauseButton.setOnClickListener { mediaPlayerPause() }

  }

  private fun mediaPlayerPause(){
    val intent = Intent(this, MediaPlayService::class.java)
      .apply {
        action = MEDIA_PLAYER_PAUSE
      }
    startService(intent)
  }

  private fun mediaPlayerStop(){
    val intent = Intent(this, MediaPlayService::class.java)
      .apply {
        action = MEDIA_PLAYER_STOP
      }
    startService(intent)
  }

  private fun mediaPlayerPlay() {
    val intent = Intent(this, MediaPlayService::class.java)
      .apply {
        action = MEDIA_PLAYER_PLAY
      }
    startService(intent)
  }
}