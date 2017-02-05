import sbtrelease._
import ReleaseStateTransformations._

xerial.sbt.Sonatype.sonatypeSettings

val sonatypeURL =
"https://oss.sonatype.org/service/local/repositories/"

val updateReadme: State => State = { state =>
  val extracted = Project.extract(state)
  val scalaV = "2.11" // sxr does not support Scala 2.12
  val v = extracted get version
  val org =  extracted get organization
  val n = extracted get name
  val snapshotOrRelease = if(extracted get isSnapshot) "snapshots" else "releases"
  val readme = "README.md"
  val readmeFile = file(readme)
  val newReadme = Predef.augmentString(IO.read(readmeFile)).lines.map{ line =>
    val matchReleaseOrSnapshot = line.contains("SNAPSHOT") == v.contains("SNAPSHOT")
    if(line.startsWith("libraryDependencies") && matchReleaseOrSnapshot){
      s"""libraryDependencies += "${org}" %% "${n}" % "$v""""
    }else if(line.contains(sonatypeURL) && matchReleaseOrSnapshot){
      val sxrIndexHtml = "-sxr.jar/!/index.html"
      val javadocIndexHtml = "-javadoc.jar/!/index.html"
      val baseURL = s"${sonatypeURL}${snapshotOrRelease}/archive/${org.replace('.', '/')}/${n}_${scalaV}/${v}/${n}_${scalaV}-${v}"
      if(line.contains(javadocIndexHtml)){
        s"- [API Documentation](${baseURL}${javadocIndexHtml})"
      }else if (line.contains(sxrIndexHtml)){
        s"- [sxr](${baseURL}${sxrIndexHtml})"
      }else line
    }else line
  }.mkString("", "\n", "\n")
  IO.write(readmeFile, newReadme)
  val git = new Git(extracted get baseDirectory)
  git.add(readme) ! state.log
  git.commit(message = "update " + readme, sign = false) ! state.log
  "git diff HEAD^" ! state.log
  state
}

commands += Command.command("updateReadme")(updateReadme)

val updateReadmeProcess: ReleaseStep = updateReadme

def releaseStepCross[A](key: TaskKey[A]) = ReleaseStep(
  action = state => Project.extract(state).runTask(key, state)._1,
  enableCrossBuild = true
)

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  updateReadmeProcess,
  tagRelease,
  releaseStepCross(PgpKeys.publishSigned),
  setNextVersion,
  commitNextVersion,
  updateReadmeProcess,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)

credentials ++= PartialFunction.condOpt(sys.env.get("SONATYPE_USER") -> sys.env.get("SONATYPE_PASS")){
  case (Some(user), Some(pass)) =>
    Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", user, pass)
}.toList

val checkPackage = taskKey[Unit]("show pom.xml and sources.jar")

checkPackage := {
  println(IO.read(makePom.value))
  println()
  IO.withTemporaryDirectory{ dir =>
    IO.unzip((packageSrc in Compile).value, dir).map(f => f.getName -> f.length) foreach println
  }
}
