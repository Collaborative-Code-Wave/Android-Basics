package code.wave.chapter13.model

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "item")
data class NewsItem(
  @PropertyElement(name = "title")
  val title: String? = null,
  @PropertyElement(name = "link")
  val link: String? = null,
)
