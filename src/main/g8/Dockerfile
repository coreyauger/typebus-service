# Using an Ubuntu 14.04 Official Linux + Java 8 (1.8.0_25)
# https://hub.docker.com/r/olagache/java-8-oracle/
FROM olagache/java-8-oracle

COPY target/pack /opt/$name;format="normalize"$

# Define user and working directory:
USER root
WORKDIR /opt/$name;format="normalize"$

EXPOSE 2553/tcp

ENTRYPOINT ["sh", "./bin/$name;format="normalize"$"]

