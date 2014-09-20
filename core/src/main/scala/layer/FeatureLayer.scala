package layer

import feature._

trait FeatureLayer extends Layer {

  def features[K, V]: Iterable[Feature[K, V]]

}
