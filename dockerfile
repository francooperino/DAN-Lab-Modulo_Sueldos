FROM openjdk:11.0.7-slim
RUN apt-get update && apt-get install -y --no-install-recommends libfontconfig1 && rm -rf /var/lib/apt/lists/*
LABEL maintainer="nachobarzola97@gmail.com"
VOLUME /tmp/reportes/recibos
VOLUME /tmp/reportes/templates/recibosueldo
ARG JAR_FILE
ADD target/${JAR_FILE} sueldos-0.0.1-SNAPSHOT.jar
RUN echo ${JAR_FILE}
EXPOSE 9004
ENTRYPOINT ["java","-jar","/sueldos-0.0.1-SNAPSHOT.jar"]