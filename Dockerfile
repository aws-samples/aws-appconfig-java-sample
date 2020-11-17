FROM maven:3.6.1-amazoncorretto-8 as build

ENV MAVEN_VERSION 3.6.1
ENV MAVEN_HOME /usr/lib/mvn
ENV PATH $MAVEN_HOME/bin:$PATH

WORKDIR /workspace/app


COPY pom.xml .
COPY src src
RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM maven:3.6.1-amazoncorretto-8
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.amazonaws.samples.appconfig.movies.MoviesApplication"]