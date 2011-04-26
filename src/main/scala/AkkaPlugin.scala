import sbt._

import Keys._

object AkkaPlugin extends Plugin {
  lazy val Akka_Repository = MavenRepository("Akka Repository", "http://akka.io/repository")
  lazy val Sun_JDMK_Repo = MavenRepository("Sun JDMK Repo", "http://wp5.e-taxonomy.eu/cdmlib/mavenrepo")
  lazy val JBoss_Repo = MavenRepository("JBoss Repo", "http://repository.jboss.org/nexus/content/groups/public/")
  lazy val Scala_Tools_Repo = MavenRepository("Scala-Tools Repo", "http://scala-tools.org/repo-releases")
  lazy val GuiceyFruit_Repo = MavenRepository("GuiceyFruit Repo", "http://guiceyfruit.googlecode.com/svn/repo/releases/")
  lazy val Codehaus_Repo = MavenRepository("Codehaus Repo", "http://repository.codehaus.org")

  val akkaVersion = SettingKey[String]("akka-version")
  val akkaModulesVersion = SettingKey[String]("akka-modules-version")
  val akkaModules = SettingKey[Seq[String]]("akka-modules")

  def akkaModule(version: String, moduleVersion: String)(module: String) = "se.scalablesolutions.akka" % ("akka-" + module) % {
    if (Set("scalaz", "camel", "dispatcher-extras", "osgi", "samples", "kernel", "spring", "camel-typed", "amqp").contains(module))
      moduleVersion
    else
      version
  }
  
  val akkaSettings = Seq(
    resolvers ++= Seq(
      Akka_Repository,
      ScalaToolsSnapshots,
      Sun_JDMK_Repo,
      JBoss_Repo,
      JavaNet1Repository,
      GuiceyFruit_Repo,
      Codehaus_Repo
    ),
    moduleConfigurations ++= Seq(
      ModuleConfiguration("org.scannotation", "*", "*", JBoss_Repo),
      ModuleConfiguration("com.sun.jersey.contribs", "*", "*", JavaNet1Repository),
      ModuleConfiguration("org.multiverse", "*", "*", Codehaus_Repo),
      ModuleConfiguration("org.jboss", "*", "*", JBoss_Repo),
      ModuleConfiguration("org.eclipse.jetty", "*", "*", DefaultMavenRepository),
      ModuleConfiguration("javax.jms", "*", "*", Sun_JDMK_Repo),
      ModuleConfiguration("com.rabbitmq", "rabbitmq-client", "0.9.1", Akka_Repository),
      ModuleConfiguration("net.debasishg", "*", "*", ScalaToolsReleases),
      ModuleConfiguration("org.scala-tools.testing", "scalacheck_2.9.0.RC1", "1.9-SNAPSHOT", ScalaToolsSnapshots),
      ModuleConfiguration("com.sun.jersey", "*", "*", JavaNet1Repository),
      ModuleConfiguration("com.atomikos", "*", "*", DefaultMavenRepository),
      ModuleConfiguration("org.scala-tools", "time", "*", ScalaToolsReleases),
      ModuleConfiguration("args4j", "*", "*", JBoss_Repo),
      ModuleConfiguration("org.scalatest", "scalatest", "1.4-SNAPSHOT", ScalaToolsSnapshots),
      ModuleConfiguration("voldemort.store.compress", "h2-lzf", "*", Akka_Repository),
      ModuleConfiguration("org.guiceyfruit", "*", "*", GuiceyFruit_Repo),
      ModuleConfiguration("com.sun.jmx", "*", "*", Sun_JDMK_Repo),
      ModuleConfiguration("com.sun.jdmk", "*", "*", Sun_JDMK_Repo),
      ModuleConfiguration("org.codehaus.aspectwerkz", "aspectwerkz", "2.2.3", Akka_Repository),
      ModuleConfiguration("org.scalaz", "*", "*", ScalaToolsSnapshots),
      ModuleConfiguration("org.jboss.netty", "*", "*", JBoss_Repo)
    ),
    akkaVersion := "1.0",
    akkaModulesVersion <<= akkaVersion(v => v),
    akkaModules := Seq("actor"),
    libraryDependencies <<= (libraryDependencies, akkaModules, akkaVersion, akkaModulesVersion) {
      (deps, modules, version, moduleVersion) =>
      deps ++
      modules.map(akkaModule(version, moduleVersion))
    }
  )
}
