## Sbt 1.x plugin for [Flyway](https://flywaydb.org)

### Getting started
Adding Flyway to your build is very easy. First, update to your `project/plugin.sbt` file to include:
```sbtshell
 // the library is available in Bintray repository
 resolvers += "cxb811201" at "https://dl.bintray.com/cxb811201/sbt-plugins"
 
 addSbtPlugin("cn.51scala" % "flyway-sbt" % "5.2.4")
```

Edit `build.sbt` to enable the plugin and configure the database access:
```sbtshell
enablePlugins(FlywayPlugin)
name := "plugtest"
version := "0.0.1"
name := "flyway-sbt-test1"

libraryDependencies += "org.hsqldb" % "hsqldb" % "2.4.0"

flywayUrl := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser := "SA"
flywayPassword := ""
flywayLocations += "db/migration"
flywayUrl in Test := "jdbc:hsqldb:file:target/flyway_sample;shutdown=true"
flywayUser in Test := "SA"
flywayPassword in Test := ""

```

Migrate your database using `sbt flywayMigrate` or clean it using `sbt flywayClean`.

Note that the `flywayTarget` setting has been disabled due to [this bug](https://github.com/flyway/flyway/issues/1919).

### Building and testing
Build and testing uses `sbt` and it's plugin [testing framework](http://www.scala-sbt.org/1.x/docs/Testing-sbt-plugins.html). 
The test cases are pretty basic (hint: we need more of those). There is no support for `sbt` prior to 1.0. Use the 
[legacy plugin](https://github.com/flyway/flyway/tree/master/flyway-sbt) instead.

Note that from v5.0.0 onwards, the plugin has to be explicitly enabled using `enablePlugins(FlywayPlugin)`. This prevents
Flyway actions triggering unrelated build activity and addresses [this issue](https://github.com/flyway/flyway/issues/1329).

Build and test the plugin using

```bash
sbt scripted
```

Early adopters should just publish a clone or fork of this repository locally:
```bash
git clone https://github.com/cxb811201/flyway-sbt.git
cd flyway-sbt
sbt publishLocal
```
