package code.wave.chapter13.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "rss")
data class NewsRss(
  @Element(name = "channel")
  val channel: RssChannel
)
