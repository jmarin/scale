Revolver.settings

initialCommands := """|import server._""".stripMargin

addCommandAlias("gs", "re-start server.GeometryServiceApp -Dakka.remote.netty.tcp.port=2551 -Dakka.cluster.roles.0=geometry-service -Dgeometry.http-service.port=8001")
