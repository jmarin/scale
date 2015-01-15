package elasticsearch

import com.typesafe.config.ConfigFactory

object Settings {

  private val config = ConfigFactory.load()
  val host = config.getString("elasticsearch.host")
  val port = config.getInt("elasticsearch.port")

}
