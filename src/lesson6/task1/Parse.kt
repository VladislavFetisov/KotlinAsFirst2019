@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val months = mapOf(
    "января" to 1,
    "февраля" to 2,
    "марта" to 3,
    "апреля" to 4,
    "мая" to 5,
    "июня" to 6,
    "июля" to 7,
    "августа" to 8,
    "сентября" to 9,
    "октября" to 10,
    "ноября" to 11,
    "декабря" to 12
)

fun dateStrToDigit(str: String): String {
    try {
        val line = str.split(" ")
        if (line.size != 3 || line[0].toInt() < 1) return ""
        val month = months[line[1]] ?: return ""
        val day = line[0].toInt()
        val year = line[2].toInt()
        if (daysInMonth(month, year) < day) return ""
        return String.format("%02d.%02d.%d", day, month, year)
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    try {
        val line = digital.split(".")
        if (line.size != 3 || line[0].toInt() < 1) return ""
        val day = line[0].toInt()
        val year = line[2].toInt()
        val month = line[1].toInt()
        if (daysInMonth(month, year) < day || month !in 1..12) return ""
        for ((key, value) in months) {
            if (value == month) return String.format("%d %s %d", day, key, year)
        }
        return ""
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах. "12 --  34- 5 -- 67 -98"
 */
fun flattenPhoneNumber(phone: String): String =
        if (!Regex("""(\+\d+)? *(\([\d\- ]+\))? *(\d+[\- ]*)+""").matches(phone)) ""
        else phone.filter { it != ' ' && it != '(' && it != '-' && it != ')' }

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (Regex("""[^\d\-%\s]""").containsMatchIn(jumps) || !Regex("""[\d]""").containsMatchIn(jumps)) return -1
    var c = -1
    val line = jumps.split(" ")
    for (i in line) {
        val j = i.toIntOrNull()
        if (j != null && j > c) c = j
    }
    return c
}


/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (!Regex("""(\d+ [+%-]+ ?)+""").matches(jumps)) return -1
    var count = -1
    val line = jumps.split(" ")
    for (i in line.indices step (2)) if (line[i].toInt() > count && "+" in line[i + 1]) count = line[i].toInt()
    return count
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException "4 - -2"
 */
fun plusMinus(expression: String): Int {
    require(Regex("""(\d+ [+-] )*\d+""").matches(expression))
    val line = expression.split(" ")
    var sum = line[0].toInt()
    for (i in 1 until line.size step (2)) {
        if (line[i] == "+") sum += line[i + 1].toInt()
        else sum -= line[i + 1].toInt()
    }
    return sum
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val line = str.split(" ")
        .map { it.toLowerCase() }
    if (line.size < 2) return -1
    var c = 0
    var prevWord = line[0]
    for (i in 1 until line.size) {
        if (line[i] == prevWord) return c
        c += line[i - 1].count() + 1
        prevWord = line[i]
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (!description.matches(Regex("""([^\s;]+ \d+(\.\d+)?; )*([^\s;]+ \d+(\.\d+)?)"""))) return ""
    val line = description.split("; ")
    var z = Double.NEGATIVE_INFINITY
    var res = ""
    for (i in line) {
        val k = i.split(" ")
        if (k[1].toDouble() > z) {
            res = k[0]
            z = k[1].toDouble()
        }
    }
    return res
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100 assertEquals(694, fromRoman("DCXCIV"))
 *
 * Вернуть -1, если roman не является корректным римским числом\\
 */
fun fromRoman(roman: String): Int {
    if (roman.isEmpty() ||
        !roman.matches(Regex("""M*(CM|DC{0,3}|CD|C{1,3})?(XC|LX{0,3}|XL|X{1,3})?(IX|VI{0,3}|IV|I{1,3})?"""))) return -1
    var res = 0
    val numbers = mapOf(
        "M" to 1000, "D" to 500, "C" to 100, "L" to 50, "X" to 10, "V" to 5, "I" to 1
    )
    for (i in roman) {
        res += numbers.getValue(i.toString())
    }
    when {
        "CD" in roman -> res -= 200
        "CM" in roman -> res -= 200
    }
    when {
        "XC" in roman -> res -= 20
        "XL" in roman -> res -= 20
    }
    when {
        "IX" in roman -> res -= 2
        "IV" in roman -> res -= 2
    }
    return res
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    require(Regex("""[+\->< ]+(\[[+\->< \[\]]+])*""").matches(commands))
    var indexChar = 0
    var countCommands = 0
    val list = mutableListOf<Int>()
    var value = cells / 2
    var bracket = 1
    for (i in 0 until cells) list.add(0)
    while (indexChar < commands.length && countCommands < limit) {
        when (commands[indexChar]) {
            '+' -> list[value]++
            '>' -> value++
            '-' -> list[value]--
            '<' -> value--
        }
        if (commands[indexChar] == '[' && list[value] == 0) {
            for (i in (indexChar + 1) until commands.length) {
                if (commands[i] == '[') bracket++
                if (commands[i] == ']') bracket--
                if (bracket == 0) {
                    indexChar += i - indexChar
                    bracket = 1
                    break
                }
            }
        }
        if (commands[indexChar] == ']' && list[value] != 0) {
            for (i in (indexChar - 1) downTo 0) {
                if (commands[i] == '[') bracket++
                if (commands[i] == ']') bracket--
                if (bracket == 2) {
                    indexChar -= indexChar - i
                    bracket = 1
                    break
                }
            }
        }
        if (indexChar + 1 < commands.length &&
            (value == 0 && commands[indexChar + 1] == '<' || value == cells - 1 && commands[indexChar + 1] == '>')) {
            throw IllegalStateException()
        }
        indexChar++
        countCommands++
    }
    return list
}

/*fun bestRes(examResults: List<String>, treshold: Double): Map<String, Double> {
    val a = mutableMapOf<String, Double>()
    for (string in examResults) {
        require(Regex("""[А-я]+ [А-я]+ - ([А-я]+ [3-5],?)+""").matches(string))
        val nameAndDic = string.split(" - ")
        val marks = string.split(Regex("""[^3-5]"""))
            .filter { it != "" }
        val midMark = marks.sumBy { it.toInt() } / marks.size.toDouble()
        a[nameAndDic[0]] = midMark
    }
    return a.filter { it.value >= treshold }
}

fun myFun(text: String): List<String> {
    val res = mutableListOf<String>()
    val map = mutableMapOf<String, List<Int>>()
    if (!Regex("""([A-z0-9]+ \d+:\d+\n?)+""").matches(text)) return listOf()
    val list = text.split("\n")
    for (i in list) {
        val time = i.split(" ")
        val time1 = time[1].split(":")
        val time2 = time1[0].toInt() * 60 + time1[1].toInt()
        if (time[0] in map) map[time[0]] = map[time[0]]!!.plus(time2)
        else map[time[0]] = mutableListOf(time2)
    }
    for ((key, value) in map) {
        if (value.size != 1) {
            for (i in value.indices) {
                for (j in i + 1 until value.size - 1) if (i + 1 < value.size && kotlin.math.abs(value[i] - value[i + 1]) < 2) res += key
            }
        }
    }
    return res
}*/