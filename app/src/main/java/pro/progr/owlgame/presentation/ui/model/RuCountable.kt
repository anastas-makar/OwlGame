package pro.progr.owlgame.presentation.ui.model

import java.lang.Math.abs

/**
 * Конструктор принимает три формы слова : для количества 0, 1 и 2
 * Метод getForNum возвращает подходящую форму слова для любого целого числа
 * Например:
 * RuCountable("мячей", "мяч", "мяча").getForNum(123) вернёт "мяча"
 * RuCountable("коров", "корова", "коровы").getForNum(12) вернёт "коров"
 * RuCountable("ёлок", "ёлка", "ёлки").getForNum(1001) вернёт "ёлка"
 * RuCountable("ёлок", "ёлка", "ёлки").getForNum(1011) вернёт "ёлок"
 */
data class RuCountable(private val zero : String,
                       private val one : String,
                       private val two : String) {
    fun getForNum(num : Int) : String {
        val absNum = abs(num)
        return when {
            absNum % 100 in 10 .. 20 -> zero
            absNum % 10 == 1 -> one
            absNum % 10 in 2 .. 4 -> two
            else -> zero
        }
    }
}