# syntax=docker/dockerfile:1.5

FROM alpine@sha256:beefdbd8a1da6d2915566fde36db9db0b524eb737fc57cd1367effd16dc0d06d AS builder
WORKDIR /app
RUN apk add  build-base=0.5-r3
COPY hello.c .
RUN gcc -static hello.c -o hello

FROM scratch AS hello-app
USER 10001
COPY --link --from=builder --chown=0:0 --chmod=555 /app/hello /hello
ENTRYPOINT ["/hello"]

# docker build --target=hello-app . 2>&1 | tee /dev/tty | grep "writing image" | tail -n1 | egrep -o 'sha256:[0-9a-f]+' | cut -d: -f2 | xargs docker run --rm
# rm -rf hello-app-container && docker build --target=hello-app . --output=type=local,dest=./hello-app-container && find hello-app-container -type f