# Курсовой проект по модулю «Автоматизация тестирования» для профессии «Инженер по тестированию»

## [![Java CI with Gradle](https://github.com/Victor1963100/CourseProject/actions/workflows/blank.yml/badge.svg?branch=main)](https://github.com/Victor1963100/CourseProject/actions/workflows/blank.yml)

Курсовой проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## **Инструкция для запуска автотестов**

На вашем ПК должен быть установлени следующий soft:

- IntelliJ IDEA
- Docker desktop (совместно с Docker-compose)
- Chrome браузер

1. Создать git-репозиторий в заранее созданной папке: `git init`

2. Клонировать проект в репозиторий:

`git cline https://github.com/Victor1963100/CourseProject

3. Открыть проект в IntelliJ IDEA

4. Через терминал IDEA запустить контейнер с СУБД при помощи команды: `docker-compose up`

5. Через терминал IDEA запустить веб-сервис при помощи команды: `java -jar artifacts/aqa-shop.jar`

6. Проверить, что приложение успешно запустилось: для этого в Сhrome браузере введите следующий URL: http://localhost/8080/

7. Запустить автотесты через терминал IDEA при помощи команды: `gradlew test`

8. Проверить отправку данных карты на виртуальный платежный сервер можно по URL: http://185.119.57.197:9998/container/af5c95638cc2

## **Требования/документация**

(https://github.com/netology-code/aqa-qamid-diplom)

## **План тестирования веб-сервиса покупки тура**

[План тестирования](https://github.com/Victor1963100/CourseProject/blob/main/Plan.md)

## **Баг - репорты**

1. [№ 1](https://github.com/Victor1963100/CourseProject/issues/1)
2. [№ 2](https://github.com/Victor1963100/CourseProject/issues/2)
3. [№ 3](https://github.com/Victor1963100/CourseProject/issues/3)
4. [№ 4](https://github.com/Victor1963100/CourseProject/issues/4)
5. [№ 5](https://github.com/Victor1963100/CourseProject/issues/8)
6. [№ 6](https://github.com/Victor1963100/CourseProject/issues/9)
7. [№ 7](https://github.com/Victor1963100/CourseProject/issues/10)
8. [№ 8](https://github.com/Victor1963100/CourseProject/issues/11)

## **Отчёт о проведённом тестировании веб-сервиса**

(https://github.com/Victor1963100/CourseProject/blob/main/Report.md)

## **Отчёт о проведённой автоматизации веб - сервиса**

https://github.com/Victor1963100/CourseProject/blob/main/Summary.md
