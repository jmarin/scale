package layer

import feature._

trait FeatureLayer extends Layer {

  def features: Iterable[Feature]

}
