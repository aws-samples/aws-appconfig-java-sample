<a id="top"></a>

<p style="font-size: 24px;"><img src="./qct-icons/transform-logo.svg" style="margin-right: 15px; vertical-align: middle;"></img><b>Code Transformation Summary by Amazon Q </b></p>
<p><img src="./qct-icons/transform-build-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Q has made the following changes during the transformation process. <p>
<p><img src="./qct-icons/transform-variables-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Lines of code in your application: 610 <p>
<p><img src="./qct-icons/transform-clock-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Transformation duration: 12 min(s) <p>
<p><img src="./qct-icons/transform-dependencies-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Dependencies upgraded: 11 out of 15 <p>
<p><img src="./qct-icons/transform-smartStepInto-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Planned deprecated code instances replaced: 14 of 14 <p>
<p><img src="./qct-icons/transform-listFiles-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Files changed: 10 <p>
<p><img src="./qct-icons/transform-build-dark.svg" style="margin-bottom: 1px; vertical-align: middle;"></img> Build status in Java 17: <span style="color: #00CC00">SUCCEEDED</span> <p>

### Table of Contents

1. <a href="#build-log-summary">Build log summary</a> 
1. <a href="#planned-dependencies-replaced">Planned dependencies replaced</a> 
1. <a href="#additional-dependencies-added">Additional dependencies added</a> 
1. <a href="#deprecated-code-replaced">Deprecated code replaced</a> 
1. <a href="#other-changes">Other changes</a> 
1. <a href="#all-files-changed">All files changed</a> 


### Build log summary <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="build-log-summary"></a>

Amazon Q successfully built the upgraded code in Java 17. Here is a relevant snippet from the build log. To view the full build log, open [`buildCommandOutput.log`](./buildCommandOutput.log)

```
The build process for a Java 17 application was successful. The compilation process included both main and test sources. A warning about unchecked or unsafe operations was reported during test compilation.
```


### Planned dependencies replaced <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="planned-dependencies-replaced"></a>

Amazon Q updated the following dependencies that it identified in the transformation plan

| Dependency | Action | Previous version in Java 8 | Current version in Java 17 |
|--------------|--------|--------|--------|
| `jakarta.validation:jakarta.validation-api` | Added | - | 3.1.1 |
| `javax.validation:validation-api` | Removed | 2.0.1.Final | - |
| `junit:junit` | Removed | 4.13.1 | - |
| `org.mockito:mockito-all` | Removed | 1.10.19 | - |
| `org.mockito:mockito-core` | Added | - | - |
| `org.springframework.boot:spring-boot-starter-parent` | Updated | 2.0.5.RELEASE | 3.3.9 |

### Additional dependencies added <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="additional-dependencies-added"></a>

Amazon Q updated the following additional dependencies during the upgrade

| Dependency | Action | Previous version in Java 8 | Current version in Java 17 |
|--------------|--------|--------|--------|
| `org.apache.logging.log4j:log4j-api` | Updated | 2.13.3 | 2.24.3 |
| `org.apache.logging.log4j:log4j-core` | Updated | 2.13.3 | 2.24.3 |
| `org.apache.maven.plugins:maven-compiler-plugin` | Updated | 3.8.1 | - |
| `org.json:json` | Updated | 20200518 | 20240303 |
| `software.amazon.awssdk:bom` | Updated | 2.14.27 | 2.28.6 |

### Deprecated code replaced <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="deprecated-code-replaced"></a>


Amazon Q replaced the following instances of deprecated code. An instance with 0 files changed
indicates Amazon Q wasn't able to replace the deprecated code.

| Deprecated code | Files changed |
|----------------|----------------|
| `java.lang.Boolean.Boolean(boolean)` | 1 |
| `java.lang.Byte.Byte(String)` | 1 |
| `java.lang.Character.Character(char)` | 1 |
| `java.lang.Double.Double(double)` | 1 |
| `java.lang.Float.Float(float)` | 1 |
| `java.lang.Integer.Integer(int)` | 1 |
| `java.lang.Short.Short(String)` | 1 |
| `java.math.BigDecimal.divide(BigDecimal,int)` | 2 |
| `java.math.BigDecimal.divide(BigDecimal,int,int)` | 2 |
| `java.math.BigDecimal.setScale(int,int)` | 2 |
| `java.util.Date.Date(int,int,int)` | 1 |



### Other changes <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="other-changes"></a>



### All files changed <a style="float:right; font-size: 14px;" href="#top">Scroll to top</a><a id="all-files-changed"></a>

| File | Action |
|----------------|--------|
| [pom.xml](../pom.xml) | Updated |
| [src/main/java/com/amazonaws/samples/appconfig/movies/MoviesController.java](../src/main/java/com/amazonaws/samples/appconfig/movies/MoviesController.java) | Updated |
| [src/main/java/com/amazonaws/samples/appconfig/utils/Encoder.java](../src/main/java/com/amazonaws/samples/appconfig/utils/Encoder.java) | Updated |
| [src/main/java/com/amazonaws/samples/appconfig/utils/HTMLBuilder.java](../src/main/java/com/amazonaws/samples/appconfig/utils/HTMLBuilder.java) | Updated |
| [src/main/java/com/amazonaws/samples/appconfig/utils/Math.java](../src/main/java/com/amazonaws/samples/appconfig/utils/Math.java) | Updated |
| [src/main/java/com/amazonaws/samples/appconfig/utils/Security.java](../src/main/java/com/amazonaws/samples/appconfig/utils/Security.java) | Updated |
| [src/test/java/com/amazonaws/samples/appconfig/movies/MathTest.java](../src/test/java/com/amazonaws/samples/appconfig/movies/MathTest.java) | Updated |
| [src/test/java/com/amazonaws/samples/appconfig/movies/MockTest.java](../src/test/java/com/amazonaws/samples/appconfig/movies/MockTest.java) | Updated |
| [src/test/java/com/amazonaws/samples/appconfig/movies/MovieTest.java](../src/test/java/com/amazonaws/samples/appconfig/movies/MovieTest.java) | Updated |
| [src/test/java/com/amazonaws/samples/appconfig/movies/MoviesControllerTest.java](../src/test/java/com/amazonaws/samples/appconfig/movies/MoviesControllerTest.java) | Updated |