FROM oracle/graalvm-ce:1.0.0-rc9 as build-env

WORKDIR /jar

ADD ./main.jar /jar/

RUN native-image \
  -jar /jar/main.jar \
  --verbose \
  -H:Name=main \
  -H:+ReportUnsupportedElementsAtRuntime

FROM alpine-glibc

WORKDIR /app

COPY --from=build-env /jar/main /app/main

ENTRYPOINT [ "/app/main" ]
