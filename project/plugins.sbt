//Scalastyle plugin
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.5.0")

//Scalariform plugin
addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")

//SBT Native packager
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "0.7.1")

addSbtPlugin("com.github.gseitz" % "sbt-protobuf" % "0.3.3")

// Plugins for publishing

resolvers += Resolver.url(
"bintray-sbt-plugin-releases",
url("http://dl.bintray.com/content/sbt/sbt-plugin-releases"))(
Resolver.ivyStylePatterns)
 
addSbtPlugin("me.lessis" % "bintray-sbt" % "0.1.1")
 
resolvers += Classpaths.sbtPluginReleases
 
resolvers += Classpaths.typesafeReleases
 
addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "1.0.1")
 
addSbtPlugin("com.sksamuel.scoverage" %% "sbt-coveralls" % "0.0.5")
 
addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")
 
// Add the following to have Git manage your build versions
resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"
 
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")
