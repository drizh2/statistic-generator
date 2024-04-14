# Опис основних сутностей 
___
## Обрані сутності
Сутності, які було обрано для даного завдання, що відносяться один до одного як "багато-до-одного":
> Пісня - Виконавець

## Statistic
Statistic - це клас, який являє собою сутність одного об'єкту статистики,
які будуть зберігатися в XML.

## Field
Field - це enum, який зберігає назви полів сутності "Song", та визначає чи є поле колекцією з елементами:
- Назва (NAME)
- Виконавець (ARTIST)
- Альбом (ALBUM)
- Рік випуску (YEAR)
- Жанри (GENRES)

## FileUtils
FileUtils - утилітний клас, який допомагає отримати список файлів з вказаної папки, які потрібно обробити для генерації
статистики.

## JsonUtils
JsonUtils - утилітний клас, який допомагає:
- Отримати Map ключем якої є значення поля для статистики, та кількості зустрічей цього атрибуту в json-об'єктах 
переданого файлу;
- Отримати ```List``` значень з поля, яке містиь колекцію значень розділених комою;

## StatisticGenerator
StatisticGenerator - це клас, який на основі переданих файлів формує ```Statistic``` об'єкти, використовуючи утилітні 
класи для аналізу файлів в декілька потоків (один файл обробляється одним потоком). Після чого записує зібрану 
статистику до XML файлу.

## Main
Main - головний клас, який містить метод main(...) та запускає програму. В ньому виконується перевірка на валідність і 
правильність переданих аргументів при запуску, а також розрахунок часу, витраченого на генерацію статистики.
___

# Приклади вхідних файлів
___

За приклад було обрано два json-файли, які містять по 5 об'єктів-пісень кожний:

### file.json
```json
[
  {
    "name": "Bohemian Rhapsody",
    "artist": "Queen",
    "album": "A Night at the Opera",
    "year": "1975",
    "genres": "Rock, Progressive rock, Hard rock"
  },
  {
    "name": "ROADS UNTRAVELED",
    "artist": "Linkin Park",
    "album": "LIVING THINGS",
    "year": "2012",
    "genres": "Rock, Alternative Rock"
  },
  {
    "name": "Smells Like Teen Spirit",
    "artist": "Nirvana",
    "album": "Nevermind",
    "year": "1991",
    "genres": "Grunge, Alternative rock"
  },
  {
    "name": "Painkiller",
    "artist": "Three Days Grace",
    "album": "Human",
    "year": "2015",
    "genres": "Rock"
  },
  {
    "name": "Hotel California",
    "artist": "Eagles",
    "album": "Hotel California",
    "year": "1976",
    "genres": "Rock, Soft rock"
  }
]
```

### file1.json
```json
[
  {
    "name": "My Hero",
    "artist": "Foo Fighters",
    "album": "The Colour and The Shape",
    "year": "1997",
    "genres": "Indie Rock, Alternative Rock"
  },
  {
    "name": "Dani California",
    "artist": "Red Hot Chili Peppers",
    "album": "Stadium Arcadium",
    "year": "2006",
    "genres": "Alternative Rock, Funk-Rock"
  },
  {
    "name": "Kickstart My Heart",
    "artist": "Motley Crue",
    "album": "Dr. Feelgood",
    "year": "1989",
    "genres": "Metal"
  },
  {
    "name": "Mr. Brightside",
    "artist": "The Killers",
    "album": "Hot Fuss",
    "year": "2004",
    "genres": "Alternative Rock, Indie Rock"
  },
  {
    "name": "Paranoid",
    "artist": "Black Sabbath",
    "album": "Paranoid",
    "year": "1970",
    "genres": "Heavy Metal, Proto-Punk, Rock"
  }
]
```

Вихідний файл-статистика у вигляді XML для поданих вхідних даних буде виглядати наступним чином:

- Для жанрів (genres):

### statistic_by_genres.xml
```xml
<?xml version='1.0' encoding='UTF-8'?>
<Statistics>
  <item>
    <attribute>rock</attribute>
    <value>481</value>
  </item>
  <item>
    <attribute>grunge</attribute>
    <value>120</value>
  </item>
  <item>
    <attribute>alternative rock</attribute>
    <value>243</value>
  </item>
  <item>
    <attribute>indie rock</attribute>
    <value>2</value>
  </item>
  <item>
    <attribute>funk-rock</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>hard rock</attribute>
    <value>120</value>
  </item>
  <item>
    <attribute>metal</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>heavy metal</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>progressive rock</attribute>
    <value>120</value>
  </item>
  <item>
    <attribute>soft rock</attribute>
    <value>120</value>
  </item>
  <item>
    <attribute>proto-punk</attribute>
    <value>1</value>
  </item>
</Statistics>
```

- Для альбомів (album):

### statistic_by_album.xml
```xml
<?xml version='1.0' encoding='UTF-8'?>
<Statistics>
  <item>
    <attribute>stadium arcadium</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>hot fuss</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>hotel california</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>dr. feelgood</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>a night at the opera</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>the colour and the shape</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>human</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>living things</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>nevermind</attribute>
    <value>1</value>
  </item>
  <item>
    <attribute>paranoid</attribute>
    <value>1</value>
  </item>
</Statistics>
```

- Для інших полів статистика генеруватиметься аналогічним чином.
___

# Моменти роботи застосунка
___

- Щоб згенерувати і запустити програму треба зібрати проєкт. Для цього, знаходячись у кореневій директорії проєкту,
ввести команду ```mvn clean package``` 
- JAR файли з залежностями(StatisticGenerator-1.0-SNAPSHOT-jar-with-dependencies.jar) та без залежностей 
(StatisticGenerator-1.0-SNAPSHOT.jar) зібрані за замовченням за шляхом ```target/{назва відповідного jarfile}```.
- Для запуску потрібно використовувати повний шлях до jarfile, шлях до папки з файлами, які оброблятимуться та атрибут,
за яким формуватиметься статистика. Наприклад, у такому вигляді:

```bash
java -jar /home/user/IdeaProjects/StatisticGenerator/target/StatisticGenerator-1.0-SNAPSHOT-jar-with-dependencies.jar /home/user/IdeaProjects/StatisticGenerator/src/main/resources/in genres
```

- Json-файли зібрані у папці ```src/main/resources/in```. Статистика за замовченням генерується у передану в аргументах
папку в додаткову папку ```out```.
___

# Результати експериментів з кількістю потоків
___

Експеримент проводився з 1, 2, 4, 8 потоками. Час вимірювався у наносекундах. Було отримано такі результати:

- 1 потік: Виконано за 157985786 наносекунд;
- 2 потоки: Виконано за 137863451 наносекунд;
- 4 потоки: Виконано за 142431724 наносекунд;
- 8 потоків: Виконано за 146673990 наносекунд;

Отже, як можна побачити, різниця невелика. Це викликано тим, що розмір файлів є невеликим. Але якщо довелося б обробляти
файли великого розміру, то швидкість би помітно збільшилась.