# syntax=docker/dockerfile:1.5

FROM eclipse-temurin:21.0.4_7-jdk-noble@sha256:48e264b4a3393475e0d778885687adf6012b3ff25f83b6a6bafbcd42a3ffcc65 AS builder
WORKDIR /app
RUN jlink \
  --add-modules java.desktop,java.logging,java.management,java.naming,java.security.jgss,java.instrument \
  --compress zip-9 \
  --strip-debug \
  --no-header-files \
  --no-man-pages \
  --output /app/jre
COPY . .
RUN --mount=type=cache,id=m2-repo,target=/root/.m2/repository \
    --mount=type=cache,id=m2-wrapper,target=/root/.m2/wrapper \
  ./mvnw package && mv target/spring-distroless-*.jar app.jar

# See https://github.com/GoogleContainerTools/distroless/blob/main/base/README.md for the various base containers
FROM gcr.io/distroless/base-nossl@sha256:c6f902c6895a2316ef879382b8c0504365243df01befa255a042b320924019a8 AS spring-app
USER 10001
COPY --link --from=builder /app/app.jar /app.jar
COPY --link --from=builder /app/jre     /app/jre

# https://docs.oracle.com/en/java/javase/21/docs/specs/jvmti.html#tooloptions
ENV JAVA_TOOL_OPTIONS='-Xmx50m'

ENTRYPOINT ["/app/jre/bin/java", "-jar", "/app.jar"]

# docker build --target=spring-app . 2>&1 | tee /dev/tty | grep "writing image" | tail -n1 | egrep -o 'sha256:[0-9a-f]+' | cut -d: -f2 | xargs docker run --rm -p8080:8080
# rm -rf spring-app-container && docker build --target=spring-app . --output=type=local,dest=./spring-app-container && find spring-app-container -type f -perm +111