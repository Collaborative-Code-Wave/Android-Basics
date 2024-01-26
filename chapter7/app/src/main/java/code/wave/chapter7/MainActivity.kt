package code.wave.chapter7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import code.wave.chapter7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
  private lateinit var binding: ActivityMainBinding
  private lateinit var wordAdapter: WordAdapter
  private var selectedWord:Word? = null
  private val updateAddWordResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    val isUpdated = it.data?.getBooleanExtra("isUpdated", false)
    if (it.resultCode == RESULT_OK && isUpdated!!) {
      updateAddWord()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    initRecyclerView()
    binding.addButton.setOnClickListener {
      updateAddWordResult.launch(Intent(this, AddActivity::class.java))
    }

    binding.deleteButton.setOnClickListener {
      delete()
    }
  }

  private fun delete(){
    if (selectedWord == null) return
    Thread {
      selectedWord?.let {
        AppDataBase.getInstance(this)?.wordDao()?.delete(it)
        runOnUiThread {
          wordAdapter.list.remove(it)
          wordAdapter.notifyDataSetChanged()
          binding.textTextView.text = ""
          binding.meanTextView.text = ""
          selectedWord = null
          Toast.makeText(this,"삭제가 완료됐습니다.", Toast.LENGTH_SHORT).show()
        }
      }
    }.start()
  }

  private fun updateAddWord(){
    Thread {
      AppDataBase.getInstance(this)?.wordDao()?.getLatestWord()?.let {
        wordAdapter.list.add(0, it)
        runOnUiThread { wordAdapter.notifyDataSetChanged() }
      }
    }.start()
  }

  private fun initRecyclerView() {
    wordAdapter = WordAdapter(mutableListOf(), this)
    binding.wordRecyclerView.apply {
      adapter = wordAdapter
      layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
      val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
      addItemDecoration(dividerItemDecoration)
    }

    Thread {
      val list = AppDataBase.getInstance(this)?.wordDao()?.getAll() ?: emptyList()
      wordAdapter.list.addAll(list)
      runOnUiThread { wordAdapter.notifyDataSetChanged() }
    }.start()
  }

  override fun onClick(word: Word) {
    selectedWord = word
    binding.textTextView.text = word.text
    binding.meanTextView.text = word.text
  }
}