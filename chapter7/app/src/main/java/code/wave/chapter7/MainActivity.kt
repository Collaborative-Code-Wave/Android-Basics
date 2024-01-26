package code.wave.chapter7

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import code.wave.chapter7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
  private lateinit var binding: ActivityMainBinding
  private lateinit var wordAdapter: WordAdapter
  private var selectedWord:Word? = null

  private val updateAddWordResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    val isUpdated = it.data?.getBooleanExtra("isUpdated", false) ?: false
    if (it.resultCode == RESULT_OK && isUpdated) {
      updateAddWord()
    }
  }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  private val updateEditWordResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
    val editWord = it.data?.getParcelableExtra<Word>("editWord", Word::class.java)
    if (it.resultCode == RESULT_OK && editWord != null) {
      updateEditWord(editWord)
    }
  }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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

    binding.editButton.setOnClickListener {
      edit()
    }
  }

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  private fun edit(){
    if (selectedWord == null) return
    val intent = Intent(this, AddActivity::class.java).putExtra("originWord", selectedWord)
    updateEditWordResult.launch(intent)
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

  private fun updateEditWord(word: Word){
    Thread {
      val index = wordAdapter.list.indexOfFirst { it.id == word.id }
      wordAdapter.list[index] = word
      runOnUiThread {
        selectedWord = word
        wordAdapter.notifyItemChanged(index)
        binding.textTextView.text = word.text
        binding.meanTextView.text = word.mean
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