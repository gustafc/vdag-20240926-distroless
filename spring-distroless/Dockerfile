# syntax=docker/dockerfile:1.5

FROM eclipse-temurin:21.0.4_7-jdk-noble@sha256:48e264b4a3393475e0d778885687adf6012b3ff25f83b6a6bafbcd42a3ffcc65 AS builder
WORKDIR /app
COPY . .
RUN --mount=type=cache,id=m2-repo,target=/root/.m2/repository \
    --mount=type=cache,id=m2-wrapper,target=/root/.m2/wrapper \
  ./mvnw package && mv target/spring-distroless-*.jar app.jar

FROM gcr.io/distroless/java21-debian12@sha256:cf7fce959603124dcc412bc2d245d84a4e0d86c4c02c6eae2cf13e973c246f24 AS hello-app
USER 10001
COPY --link --from=builder --chown=10001:10001 --chmod=500 /app/app.jar /app.jar

# https://docs.oracle.com/en/java/javase/21/docs/specs/jvmti.html#tooloptions
ENV JAVA_TOOL_OPTIONS='-Xmx256m'

ENTRYPOINT ["java", "-jar", "/app.jar"]

# docker build --target=hello-app . 2>&1 | tee /dev/tty | grep "writing image" | tail -n1 | egrep -o 'sha256:[0-9a-f]+' | cut -d: -f2 | xargs docker run --rm -p8080:8080
# rm -rf hello-app-container && docker build --target=hello-app . --output=type=local,dest=./hello-app-container && find hello-app-container -type f -perm +111