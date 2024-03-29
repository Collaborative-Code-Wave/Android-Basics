package code.wave.chapter13.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "channel")
data class RssChannel(
  @PropertyElement(name = "title")
  val title: String,
  @Element(name = "item")
  val items: List<NewsItem>? = null,
)
