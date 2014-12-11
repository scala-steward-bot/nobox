# nobox

[![Build Status](https://secure.travis-ci.org/xuwei-k/nobox.png?branch=master)](http://travis-ci.org/xuwei-k/nobox)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.xuwei-k/nobox_2.11/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.xuwei-k/nobox_2.11)


immutable primitive array wrapper for Scala

## what's this

nobox means **No** Boxing primitive values.
There are [`ArrayOps`](https://github.com/scala/scala/blob/v2.11.4/src/library/scala/collection/mutable/ArrayOps.scala) and [`WrappedArray`](https://github.com/scala/scala/blob/v2.11.4/src/library/scala/collection/mutable/WrappedArray.scala) in [Scala standard library](http://docs.scala-lang.org/overviews/collections/arrays.html), but these operations sometimes so slow.
This library provide more efficient(faster and less memory) operations for primitive arrays.

### latest stable version

```scala
libraryDependencies += "com.github.xuwei-k" %% "nobox" % "0.1.10"
```

- [generated source code](http://java-src.appspot.com/com.github.xuwei-k/nobox_2.11?latest)
- [API Documentation](https://oss.sonatype.org/service/local/repositories/releases/archive/com/github/xuwei-k/nobox_2.11/0.1.10/nobox_2.11-0.1.10-javadoc.jar/!/index.html)


### snapshot version

```scala
resolvers += Opts.resolver.sonatypeSnapshots

libraryDependencies += "com.github.xuwei-k" %% "nobox" % "0.1.11-SNAPSHOT"
```


- [API Documentation](https://oss.sonatype.org/service/local/repositories/snapshots/archive/com/github/xuwei-k/nobox_2.11/0.1.11-SNAPSHOT/nobox_2.11-0.1.11-SNAPSHOT-javadoc.jar/!/index.html)
- [sxr](https://oss.sonatype.org/service/local/repositories/snapshots/archive/com/github/xuwei-k/nobox_2.11/0.1.11-SNAPSHOT/nobox_2.11-0.1.11-SNAPSHOT-sxr.jar/!/index.html)

