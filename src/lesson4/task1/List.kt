@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.digitNumber
import lesson3.task1.minDivisor
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.math.*

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    if (v.isEmpty()) return 0.0
    var z = 0.0
    for (element in v.map { it * it }) z += element
    return abs(sqrt(z))
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double =
    when {
        (list.isEmpty()) -> 0.0
        else -> (list.sum() / list.size)
    }


/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isEmpty()) return list
    val z = mean(list)
    for (i in list.indices) {
        list[i] -= z
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    if (a.isEmpty() || b.isEmpty()) return 0
    var z = 0
    val c = a + b
    for (i in c.indices) {
        z += c[i] * c[(c.size - 1) / 2.0.toInt() + i + 1]
        if (i == (c.size - 1) / 2.0.toInt()) break
    }
    return z
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    if (p.isEmpty()) return 0
    var sum = p[0]
    for (i in 1 until p.size) {
        sum += p[i] * x.toDouble().pow(i).toInt()
    }
    return sum
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    for (i in 1 until list.size) {
        list[i] += list[i - 1]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var z = n
    val res = mutableListOf<Int>()
    while (z != 1) {
        val m = minDivisor(z)
        res += m
        z /= m
    }
    return res
}


/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String {
    val s = factorize(n)
    var z = "${s[0]}"
    for (i in 1 until s.size) {
        z += "*${s[i]}"
    }
    return z
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var s = n
    val z = mutableListOf<Int>()
    while (s !in 0 until base) {
        z += s % base
        s /= base
    }
    z += s
    return z.reversed()
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    val k = convert(n, base)
    var m = ""
    for (i in k.indices) {
        if (k[i] <= 9) m += "${k[i]}"
        else {
            when {
                k[i] == 10 -> m += 'a'
                k[i] == 11 -> m += 'b'
                k[i] == 12 -> m += 'c'
                k[i] == 13 -> m += 'd'
                k[i] == 14 -> m += 'e'
                k[i] == 15 -> m += 'f'
                k[i] == 16 -> m += 'g'
                k[i] == 17 -> m += 'h'
                k[i] == 18 -> m += 'i'
                k[i] == 19 -> m += 'j'
                k[i] == 20 -> m += 'k'
                k[i] == 21 -> m += 'l'
                k[i] == 22 -> m += 'm'
                k[i] == 23 -> m += 'n'
                k[i] == 24 -> m += 'o'
                k[i] == 25 -> m += 'p'
                k[i] == 26 -> m += 'q'
                k[i] == 27 -> m += 'r'
                k[i] == 28 -> m += 's'
                k[i] == 29 -> m += 't'
                k[i] == 30 -> m += 'u'
                k[i] == 31 -> m += 'v'
                k[i] == 32 -> m += 'w'
                k[i] == 33 -> m += 'x'
                k[i] == 34 -> m += 'y'
                k[i] == 35 -> m += 'z'
            }
        }
    }
    return m
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var num = 0
    var z = 0
    for (i in digits.size - 1 downTo 0) {
        num += digits[i] * base.toDouble().pow(z).toInt()
        z += 1
    }
    return num
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    var res = 0
    for ((k, i) in (str.length - 1 downTo 0).withIndex()) {
        if (str[i].toInt() <= 57) res += (str[i].toInt() - 48) * base.toDouble().pow(k).toInt()
        if (str[i].toInt() >= 97) res += (str[i].toInt() - 87) * base.toDouble().pow(k).toInt()
    }
    return res
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var k = ""
    for (i in 1..n) k += 'I'
    return k.replace("IIIII", "V")
        .replace("IIII", "IV")
        .replace("VV", "X")
        .replace("VIV", "IX")
        .replace("XXXXX", "L")
        .replace("XXXX", "XL")
        .replace("LL", "C")
        .replace("LXL", "XC")
        .replace("CCCCC", "D")
        .replace("CCCC", "CD")
        .replace("DD", "M")
        .replace("DCD", "CM")
}


/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val res = mutableListOf<String>()
    val u = mutableListOf(n / 1000, n % 1000)
    for (i in 0..1) {
        while (u[i] != 0) {
            when (u[i] / 100) {
                1 -> res += "сто"
                2 -> res += "двести"
                3 -> res += "триста"
                4 -> res += "четыреста"
                5 -> res += "пятьсот"
                6 -> res += "шестьсот"
                7 -> res += "семьсот"
                8 -> res += "восемьсот"
                9 -> res += "девятьсот"
            }
            u[i] %= 100
            when (u[i]) {
                10 -> res += "десять"
                11 -> res += "одиннадцать"
                12 -> res += "двенадцать"
                13 -> res += "тринадцать"
                14 -> res += "четырнадцать"
                15 -> res += "пятнадцать"
                16 -> res += "шестнадцать"
                17 -> res += "семнадцать"
                18 -> res += "восемнадцать"
                19 -> res += "девятнадцать"
            }
            if (u[i] in 10..19) break
            when (u[i] / 10) {
                2 -> res += "двадцать"
                3 -> res += "тридцать"
                4 -> res += "сорок"
                5 -> res += "пятьдесят"
                6 -> res += "шестьдесят"
                7 -> res += "семьдесят"
                8 -> res += "восемьдесят"
                9 -> res += "девяносто"
            }
            u[i] %= 10
            when (u[i]) {
                1 -> res += if (i == 0) "одна" else "один"
                2 -> res += if (i == 0) "две" else "два"
                3 -> res += if (i == 0) "три" else "три"
                4 -> res += if (i == 0) "четыре" else "четыре"
                5 -> res += "пять"
                6 -> res += "шесть"
                7 -> res += "семь"
                8 -> res += "восемь"
                9 -> res += "девять"
            }
            break
        }
        if (i == 0 && n / 1000 != 0) res += when {
            u[i] == 1 -> "тысяча"
            u[i] in 2..4 -> "тысячи"
            else -> "тысяч"
        }
    }
    return res.joinToString(separator = " ")
}