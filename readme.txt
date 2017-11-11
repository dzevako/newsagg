Агрегатор новостей.

1. Собирает новости с указанных ресурсов, сохраняет их в бд, 
отображает в интерфейсе топ 20 самых свежих новостей с учетом фильтра

2. Забор адресов ресурсов и правил парсинга происхоит из файла (.xml)
Пример содержания файла:

<?xml version="1.0" encoding="UTF-8"?>
<conf>
	<parsers>
		<parser>
			<url>https://vz.ru/news/</url>     
			<rule>
				<basePath>div.othnews</basePath>
				<titlePath>h4 > a</titlePath>
				<descriptionPath>div.text</descriptionPath>
			</rule>
			<enabled>true</enabled>
		</parser>
		<parser>
			<url>https://riafan.ru/category/tape_news</url>     
			<rule>
				<basePath>a.item_link</basePath>
				<titlePath>div.title</titlePath>
				<descriptionPath>div.text</descriptionPath>
			</rule>
			<enabled>true</enabled>
		</parser>
	</parsers>
</conf>

Правила парсинга указываются в формате, поддерживаемом библиотекой JSoup,
при этом:
	* basePath - абсолютный путь до новости
	* titlePath - относительный путь до названия новости
	* descriptionPath - относительный путь до содержимого
	
Парсеры можно отключать путем установки флаго enabled в false.
	
3. В интерфейсе происходит автоматическая подгрузка только что найденных новостей.
Список с новостями содержит всегда 20 самых свежих новостей с учетом фильтра.

4. Фильтрация происходит через секунду после ввода символа.
Если несколько символов вводится быстро, то запрос списка выполнится только после ввода последнего	

5. Конфигурация бд зашита в приложение. База данных по умолчанию h2
Содержимое файла конфигурации
##### Application properties #####
#
# DataBase access properties
db.url=jdbc:h2:~/test
db.username=sa
db.password=
db.driver=org.h2.Driver
db.dialect=org.hibernate.dialect.H2Dialect

