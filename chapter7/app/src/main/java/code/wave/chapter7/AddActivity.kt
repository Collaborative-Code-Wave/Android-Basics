package code.wave.chapter7

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import code.wave.chapter7.databinding.ActivityAddBinding
import com.google.android.material.chip.Chip

class AddActivity : AppCompatActivity() {
  private lateinit var binding: ActivityAddBinding
  private var originWord: Word? = null

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityAddBinding.inflate(layoutInflater)
    setContentView(binding.root)

    initViews()
    binding.addButton.setOnClickListener {
      if (originWord == null) {
        add()
      } else {
        edit()
      }
    }
  }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  private fun initViews() {
    val types = listOf(
      "명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사"
    )
    binding.typeChipGroup.apply {
      types.forEach { text -> addView(createChip(text)) }
    }

    binding.textInputEditText.addTextChangedListener {
      it?.let { text ->
        binding.textTextInputLayout.error = when(text.length) {
          0 -> "값을 입력해주세요"
          1 -> "2자 이상을 입력해주세요"
          else -> null
        }
      }
    }

    originWord = intent.getParcelableExtra<Word>("originWord", Word::class.java)
    originWord?.let { word ->
      binding.textInputEditText.setText(word.text)
      binding.meanInputEditText.setText(word.mean)
      val selectedChip = binding.typeChipGroup.children.firstOrNull { (it as Chip).text == word.type } as? Chip
      selectedChip?.isChecked = true
    }
  }

  private fun createChip(text: String): Chip {
    return Chip(this).apply {
      setText(text)
      isCheckable = true
      isClickable = true
    }
  }

  private fun add() {
    val text = binding.textInputEditText.text.toString()
    val mean = binding.meanInputEditText.text.toString()
    val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
    val word = Word(text, mean, type)

    Thread {
      AppDataBase.getInstance(this)?.wordDao()?.insert(word)
      runOnUiThread {
        Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
      }
      val intent = Intent().putExtra("isUpdated", true)
      setResult(RESULT_OK, intent)
      finish()
    }.start()
  }

  private fun edit() {
    val text = binding.textInputEditText.text.toString()
    val mean = binding.meanInputEditText.text.toString()
    val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
    val editWord = originWord?.copy(text = text, mean = mean, type = type)

    Thread {
      editWord?.let {
        AppDataBase.getInstance(this)?.wordDao()?.update(editWord)
        runOnUiThread { Toast.makeText(this, "수정을 완료했습니다.", Toast.LENGTH_SHORT).show() }
        val intent = Intent().putExtra("editWord", editWord)
        setResult(RESULT_OK, intent)
        finish()
      }
    }.start()
  }
}