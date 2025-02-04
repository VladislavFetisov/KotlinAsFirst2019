@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import lesson3.task1.digitNumber
import java.io.File
import kotlin.text.StringBuilder

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    val text = File(inputName).readText()
        .toLowerCase()
    for (word in substrings) res[word] = text.windowed(word.length) { if (it == word.toLowerCase()) 1 else 0 }
        .sum()
    return res
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val text = File(inputName).readText()
    if (text.isEmpty()) output.close()
    else {
        val line = StringBuilder().append(text[0])
        val letters = listOf('ж', 'ч', 'ш', 'щ')
        val corrections = mapOf('Ы' to 'И', 'ы' to 'и', 'Я' to 'А', 'я' to 'а', 'Ю' to 'У', 'ю' to 'у')
        for (i in 1 until text.length) {
            if (text[i] in corrections && text[i - 1].toLowerCase() in letters) {
                line.append(corrections[text[i]])
            } else line.append(text[i])
        }
        output.write(line.toString())
        output.close()
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val text = File(inputName).readText()
    val line = StringBuilder()
    val lines = text.split("\n")
        .map { it.trim() }
    val maxLength = lines.maxBy { it.length }!!.length
    for (string in lines) {
        for (i in 0 until (maxLength - string.length) / 2) line.append(" ")
        line.append(string, "\n")
    }
    output.write(line.toString())
    output.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    val line = StringBuilder()
    var maxLength = 0
    for (a in lines) {
        val w = a.split(" ")
            .filter { it != "" }
        val countSymbols = w.sumBy { it.length } + w.size - 1
        if (countSymbols > maxLength) maxLength = countSymbols
    }
    for (i in lines) {
        val words = i.split(" ")
            .filter { it != "" }   //все слова в строке
        if (words.isNotEmpty() && words.size != 1) {
            val lengthWords = words.map { it.length }
                .sum()
            val countGaps =
                    (maxLength - lengthWords) / (words.size - 1) // грубое общее кол-во пробелов,которое могу добавить
            var remainingGaps = (maxLength - lengthWords) % (words.size - 1) // возможный остаток по пробелам
            for (j in words.indices) {
                line.append(words[j])
                if (j == words.size - 1) break
                for (k in 0 until countGaps) line.append(" ")
                if (remainingGaps != 0) {
                    line.append(" ")
                    remainingGaps--
                }
            }
        }
        if (words.size == 1) line.append(words[0])
        line.append("\n")
    }
    output.write(line.toString())
    output.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    val res2 = mutableMapOf<String, Int>()
    var count = 0
    val text = File(inputName).readText()
    if (text.isEmpty()) return emptyMap()
    val text1 = text.split(Regex("""[^A-zА-яЁё]"""))
        .filter { it != "" }
    for (i in text1) res[i.toLowerCase()] = res.getOrDefault(i.toLowerCase(), 0) + 1
    if (res.size > 20) {
        while (count < 20) {
            val max = res.maxBy { it.value }!!.toPair()
            res2 += max
            res -= max.first
            count++
        }
    } else return res
    return res2
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val text = File(inputName).readText()
    val string = StringBuilder()
    val map = mutableMapOf<Char, String>()
    for ((key, value) in dictionary) map[key.toLowerCase()] = value.toLowerCase()
    for (i in text) {
        if (i.toLowerCase() in map) {
            if (i.isUpperCase()) string.append(map[i.toLowerCase()]?.capitalize())
            else string.append(map[i])
        } else string.append(i)
    }
    output.write(string.toString())
    output.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val text = File(inputName).readText()
        .split(Regex("""\s+"""))
    if (text.isEmpty()) output.close()
    var list = mutableListOf<String>()
    var count = 0
    for (word in text) {
        val length = word.length
        if (length >= count && word.toLowerCase().toSet().size == length) {
            if (length != count) list = list.filter { false } //Очищаю лист,не знаю,как сделать по-другому
                .toMutableList()
            list.plusAssign(word)
            count = length
        }
    }
    output.write(list.joinToString(", "))
    output.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val lines = File(inputName).readLines()
    val line = StringBuilder()
    line.append("<html>", "<body>", "<p>")
    var countDash = 0
    var countStars = 0
    var cDB = 0 //count Double Stars
    var openP = 1
    for (k in lines.indices) {
        if (lines[k].isEmpty() && openP == 1 && k != 0 && lines[k - 1].isNotEmpty()) {
            line.append("</p>")
            openP = 0
        } else {
            var i = 0
            while (i < lines[k].length) {
                if (openP == 0) {
                    line.append("<p>")
                    openP = 1
                }
                if (lines[k][i] == '~') {
                    if (i + 1 < lines[k].length && lines[k][i + 1] == '~') {
                        if (countDash == 0) {
                            line.append("<s>")
                            countDash++
                        } else if (countDash == 1) {
                            line.append("</s>")
                            countDash--
                        }
                    }
                }
                if (lines[k][i] == '*') {
                    if (i + 1 < lines[k].length && lines[k][i + 1] == '*') {
                        if (cDB == 0) {
                            line.append("<b>")
                            cDB++
                            i += 1
                        } else if (cDB == 1) {
                            line.append("</b>")
                            cDB--
                            i += 1
                        }
                    } else {
                        if (countStars == 0) {
                            line.append("<i>")
                            countStars++
                        } else if (countStars == 1) {
                            line.append("</i>")
                            countStars--
                        }
                    }
                }
                if (lines[k][i] != '~' && lines[k][i] != '*') line.append(lines[k][i])
                i++
            }
        }
    }
    if (openP == 1) line.append("</p>")
    output.write(line.append("</body>", "</html>").toString())
    output.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val output = File(outputName).bufferedWriter()
    val string = StringBuilder()
    val countGaps = digitNumber(lhv * rhv) + 1   //Поскольку есть знак умножения
    for (i in 0 until (countGaps - digitNumber(lhv))) string.append(" ")
    string.append(lhv, "\n*")
    for (i in 0 until (countGaps - digitNumber(rhv)) - 1) string.append(" ")
    string.append(rhv, "\n")
    for (i in 0 until countGaps) string.append("-")
    string.append("\n")
    for (i in 0 until countGaps - digitNumber(lhv * (rhv % 10))) string.append(" ")
    string.append(lhv * (rhv % 10), "\n")
    var c = rhv / 10
    var category = 1
    while (c != 0) {
        string.append("+")
        for (i in 0 until (countGaps - digitNumber(lhv * (c % 10)) - category) - 1) string.append(" ")
        string.append(lhv * (c % 10), "\n")
        c /= 10
        category++
    }
    for (i in 0 until countGaps) string.append("-")
    output.write(string.append("\n", " ", lhv * rhv).toString())
    output.close()
}

/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}
/*val output = File(outputName).bufferedWriter()
val string = StringBuilder()
val c = mutableListOf<Int>()
var count = 0
var firstNum = lhv
string.append(" ", lhv, " ", "|", " ", rhv, "\n-")
while (firstNum != 0) {
    c += firstNum % 10
    firstNum /= 10
}
for (i in c.reversed()) {
    if (i + count * 10 >= rhv || lhv < rhv) {
        count = count * 10 + i
        val k = count - count % rhv
        string.append(k)
        for (j in 0 until c.size - digitNumber(count) + 3) string.append(" ")
        string.append(lhv / rhv, "\n")
        for (z in 0..digitNumber(k)) string.append("-") //Потому как есть знак "минус"
        string.append("\n")
        break
    }
    count = count * 10 + i
}
output.write(string.toString())
output.close()*/


