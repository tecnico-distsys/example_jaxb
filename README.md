# JAX-B example

This is a Java application using JAX-B (Java API for XML data binding)

Depending on the command line arguments, the example executes differently:

- marshal - produces an XML file from Java objects
- unmarshal - produces Java objects from an XML file
- unmarshal schema - same as unmarshal but also validates the XML using the provided XML Schema


## Instructions for using Maven

To generate code from XML Schema use one of the following commands:

```
mvn jaxb2:xjc
mvn generate-sources
```

Note: In Eclipse, it may be necessary to 
manually add "target/generated-sources/jaxb" to the build path.


To compile and copy the properties files to the output directory:

```
mvn compile
```

To run the default example using exec plugin:

```
mvn exec:java
```

To list available profiles (one for each example):

``` 
mvn help:all-profiles
```

To run a specific example, select the profile with -P:

```
mvn exec:java -P unmarshal-ok
```


## To configure the Maven project in Eclipse

'File', 'Import...', 'Maven'-'Existing Maven Projects'

'Select root directory' and 'Browse' to the project base folder.

Check that the desired POM is selected and 'Finish'.


----

[SD Faculty](mailto:leic-sod@disciplinas.tecnico.ulisboa.pt)
