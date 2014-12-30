publishMavenStyle := false
 
instrumentSettings
 
ScoverageKeys.minimumCoverage := 95
 
ScoverageKeys.failOnMinimumCoverage := true
 
ScoverageKeys.highlighting := true
 
coverallsSettings

bintrayPublishSettings
 
bintray.Keys.repository in bintray.Keys.bintray := "sbt-plugins"
 
licenses += ("Apache", url("http://www.apache.org/licenses/LICENSE-2.0"))
 
bintray.Keys.bintrayOrganization in bintray.Keys.bintray := None


