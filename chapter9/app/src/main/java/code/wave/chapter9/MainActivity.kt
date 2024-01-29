package code.wave.chapter9

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import code.wave.chapter9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private var mediaPlayer: MediaPlayer? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.playButton.setOnClickListener { mediaPlayerPlay() }
    binding.stopButton.setOnClickListener { mediaPlayerStop() }
    binding.pauseButton.setOnClickListener { mediaPlayerPause() }

  }

  private fun mediaPlayerPause(){
    mediaPlayer?.pause()
  }

  private fun mediaPlayerStop(){
    mediaPlayer?.stop()
    mediaPlayer?.release()
    mediaPlayer = null
  }

  private fun mediaPlayerPlay() {
    if (mediaPlayer == null){
      mediaPlayer = MediaPlayer.create(this, R.raw.file_example).apply {
        isLooping = true
      }
    }
    mediaPlayer?.start()
  }
}