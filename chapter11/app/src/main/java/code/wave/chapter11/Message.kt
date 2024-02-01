package code.wave.chapter11

import com.google.gson.annotations.SerializedName

data class Message(
  @SerializedName("message")
  val message: String
)
