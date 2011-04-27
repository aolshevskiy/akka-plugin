import sbt._

import Keys._

object AkkaPlugin extends Plugin {
	val AkkaRepo             = MavenRepository("Akka Repository", "http://akka.io/repository")
	val ClojarsRepo          = MavenRepository("Clojars Repo", "http://clojars.org/repo")
	val CodehausRepo         = MavenRepository("Codehaus Repo", "http://repository.codehaus.org")
	val GuiceyFruitRepo      = MavenRepository("GuiceyFruit Repo", "http://guiceyfruit.googlecode.com/svn/repo/releases/")
	val JBossRepo            = MavenRepository("JBoss Repo", "http://repository.jboss.org/nexus/content/groups/public/")
	val JavaNetRepo          = MavenRepository("java.net Repo", "http://download.java.net/maven/2")
	val MsgPackRepo          = MavenRepository("Message Pack Releases Repo","http://msgpack.sourceforge.net/maven2/")
	val SonatypeSnapshotRepo = MavenRepository("Sonatype OSS Repo", "http://oss.sonatype.org/content/repositories/releases")
	val SunJDMKRepo          = MavenRepository("Sun JDMK Repo", "http://wp5.e-taxonomy.eu/cdmlib/mavenrepo")
	val TerrastoreRepo       = MavenRepository("Terrastore Releases Repo", "http://m2.terrastore.googlecode.com/hg/repo")
	val ZookeeperRepo        = MavenRepository("Zookeeper Repo", "http://lilycms.org/maven/maven2/deploy/")

	val akkaVersion = SettingKey[String]("akka-version")
	val akkaModules = SettingKey[Seq[String]]("akka-modules")

	def akkaModule(version: String)(module: String) = "se.scalablesolutions.akka" % ("akka-" + module) % version

	val akkaSettings = Seq(
		resolvers ++= Seq(
			AkkaRepo,
			ClojarsRepo,
			CodehausRepo,
			GuiceyFruitRepo,
			JBossRepo,
			JavaNetRepo,
			MsgPackRepo,
			SonatypeSnapshotRepo,
			SunJDMKRepo,
			TerrastoreRepo,
			ZookeeperRepo
		),
		moduleConfigurations ++= Seq(
			ModuleConfiguration("se.scalablesolutions.akka", AkkaRepo),
			ModuleConfiguration("org.codehaus.aspectwerkz", AkkaRepo),
			ModuleConfiguration("org.apache.cassandra", AkkaRepo),
			ModuleConfiguration("com.eaio", AkkaRepo),
			ModuleConfiguration("com.facebook", AkkaRepo),
			ModuleConfiguration("voldemort.store.compress", AkkaRepo),
			ModuleConfiguration("org.apache.hbase", AkkaRepo),
			ModuleConfiguration("jsr166x", AkkaRepo),
			ModuleConfiguration("spy", "memcached", AkkaRepo),
			ModuleConfiguration("net.lag", AkkaRepo),
			ModuleConfiguration("com.redis", AkkaRepo),
			ModuleConfiguration("sbinary", AkkaRepo),
			ModuleConfiguration("sjson.json", AkkaRepo),
			ModuleConfiguration("com.trifork", AkkaRepo),
			ModuleConfiguration("org.scala-tools", "vscaladoc", "1.1-md-3", AkkaRepo),
			ModuleConfiguration("args4j", JBossRepo),
			ModuleConfiguration("org.atmosphere", SonatypeSnapshotRepo),
			ModuleConfiguration("com.mongodb.casbah", ScalaToolsReleases),
			ModuleConfiguration("com.sun.grizzly", JavaNetRepo),
			ModuleConfiguration("org.guiceyfruit", GuiceyFruitRepo),
			ModuleConfiguration("org.jboss", JBossRepo),
			ModuleConfiguration("com.sun.jdmk", SunJDMKRepo),
			ModuleConfiguration("javax.jms", SunJDMKRepo),
			ModuleConfiguration("com.sun.jmx", SunJDMKRepo),
			ModuleConfiguration("com.sun.jersey.contribs", JavaNetRepo),
			ModuleConfiguration("com.sun.jersey", JavaNetRepo),
			ModuleConfiguration("jgroups", JBossRepo),
			ModuleConfiguration("jsr166y", TerrastoreRepo),
			ModuleConfiguration("org.msgpack", MsgPackRepo),
			ModuleConfiguration("org.multiverse", CodehausRepo),
			ModuleConfiguration("org.jboss.netty", JBossRepo),
			ModuleConfiguration("org.jboss.resteasy", JBossRepo),
			ModuleConfiguration("org.scannotation", JBossRepo),
			ModuleConfiguration("terrastore", TerrastoreRepo),
			ModuleConfiguration("org.scala-tools", "time", ScalaToolsReleases),
			ModuleConfiguration("voldemort", ClojarsRepo),
			ModuleConfiguration("org.apache.hadoop.zookeeper", ZookeeperRepo)
		),
		akkaVersion := "1.0",
		akkaModules := Seq("actor"),
		libraryDependencies <<= (libraryDependencies, akkaModules, akkaVersion) {
			(deps, modules, version) =>
				deps ++
			modules.map(akkaModule(version))
		}
	)
}
