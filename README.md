# IP-Addr-Counter
Счётчик IP-адресов. Для регистрации уникальных значений используется битовая карта размером 256^4 бит (512 Мб).
Каждый IP-адрес преобразуется в натуральное число n в диапазоне от 0 до 256^4 - 1 включительно, затем n-ному биту присваивается занчение 1.

Приемуществом такого подхода является фиксированный размер памяти при увеличении количества адресов в выборке. Это позволяет сканировать сотни гигабайт логов, занимая относительно немного памяти. 

## Конфиг
Перед запуском создать в корне проекта файл `config` с атрибутом `ipFilePath`, содержащим полный путь до файла с айпишниками.