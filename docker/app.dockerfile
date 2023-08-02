FROM gradle:8.1.1-jdk17

WORKDIR /home

COPY ../build/libs/votogether-0.0.1-SNAPSHOT.jar /app.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=deploy", "-Duser.timezone=Asia/Seoul", "/app.jar"]
