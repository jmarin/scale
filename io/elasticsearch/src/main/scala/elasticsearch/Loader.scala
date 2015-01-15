package elasticsearch

import feature._

trait Loader {

  def loadFeature(f: Feature): Unit = {

  }

  def loadFeatures(features: List[Feature]): Unit = {

  }

  def loadFeatures(it: Iterator[Feature]): Unit = {

  }

}
