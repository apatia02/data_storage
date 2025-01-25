## Основная цель репозитория
Освоить навык работы с БД Room, Android Storage и Shared Preferences на основе решения типовых задач и сформировать понимание о границах применимости разных типов хранилищ 
### Задача №1
1. В созданном сэмпле написать пример сохранения объекта с тремя полями в Shared Preferences
Сохранённый объект нужно достать из Shared Preferences и вывести в logact
2. Аналогично сделать с использованием DataStore

<details>
  <summary>Сохранение в Shared Preferences</summary>
<img src="https://github.com/user-attachments/assets/3c2931c1-acaf-4fde-bbc5-3f461b113c37"/>
</details>

<details>
  <summary>Сохранение в  DataStore</summary>
<img src="https://github.com/user-attachments/assets/4d155547-c5d9-4020-8c8e-ce95ca3c3585"/>
</details>

### Задача №2
1. В созданном сэмпле написать пример сохранения и открытия непустых файлов из разных Storage (extenral/internal). Результат вывести в logcat
2. Попробовать сохрнаить файл в external storage на Android 11+ и объяснить поведение
3. Изучить Shared Storage и написать пример сохранения и чтения файла через MediaStore

<details>
  <summary>Работа с extenral/internal на Android 8</summary>
<img src="https://github.com/user-attachments/assets/21002908-421d-41db-a2bd-8ae719d2df5a"/>
</details>

<details>
  <summary>Сохраненный файл на Android 8</summary>
<img src="https://github.com/user-attachments/assets/f06c5034-59a0-46ac-8315-2afa639abb23"/>
</details>

<details>
  <summary>Работа с MediaStore на Android 12</summary>
<img src="https://github.com/user-attachments/assets/988029c4-1cad-43c4-9fbd-5e43a1ea4418"/>
</details>

<details>
  <summary>Сохраненный файл на Android 12</summary>
<img src="https://github.com/user-attachments/assets/089089df-82d2-4f85-ba3a-a6276e41c0ef"/>
</details>

### Задача №3
В созданном сэмпле с помощью Room:
1. Добавить два Entity (пользователь и адрес) и СRUD запросы к ним
2. Реализовать хранение составного объекта - объединить из двух таблиц и сделать пользователя с адресом. Вывести в logact
3. Добавить хранение объекта с 3 полями внутри Entity адреса

<details>
  <summary>Работа бд</summary>
<img src="https://github.com/user-attachments/assets/1e740dec-2029-42f1-8e1c-e91c9d4453a4"/>
</details>

### Задача №4
1. Добавить в Entity пользователя новое поле, которое должно быть непустым
2. Написать миграцию структуры БД на новую версию

Миграцию сделал в отдельной [ветке](https://github.com/apatia02/data_storage/tree/migration).
<details>
  <summary>Таблица пользователей до миграции</summary>
<img src="https://github.com/user-attachments/assets/1b7a7cc7-4350-41c5-a77e-47ccba064381"/>
</details>

<details>
  <summary>Таблица пользователей после миграции</summary>
  <img src="https://github.com/user-attachments/assets/a915454b-650e-419b-a3da-7e8e2a101603"/>
</details>
