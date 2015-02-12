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
 
addSbtPlugin("org.scoverage" %% "sbt-coveralls" % "1.0.0.BETA1") 

// Add the following to have Git manage your build versions
resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"
 
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.4")

//SBT Sonatype plugin
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.2")
