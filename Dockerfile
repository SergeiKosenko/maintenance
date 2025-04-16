# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный JAR файл в контейнер
COPY target/maintenance-0.0.1-SNAPSHOT.jar /app/maintenance-0.0.1-SNAPSHOT.jar
#
# Указываем команду для запуска приложения
CMD ["java", "-jar", "maintenance-0.0.1-SNAPSHOT.jar"]

## Копируем скомпилированный JAR файл в контейнер
#COPY /maintenanceV1.jar /app/maintenanceV1.jar

## Указываем команду для запуска приложения
#CMD ["java", "-jar", "maintenanceV1.jar"]