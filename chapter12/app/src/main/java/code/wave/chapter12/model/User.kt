package code.wave.chapter12.model

import com.google.gson.annotations.SerializedName

data class User(
  @SerializedName("id")
  val id: Int,
  @SerializedName("login")
  val username: String,
)
