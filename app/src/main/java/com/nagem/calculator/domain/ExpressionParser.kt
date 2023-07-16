package com.nagem.calculator.domain

class ExpressionParser(
    private val calculation: String
) {

    fun parse(): List<ExpressionPart> {
        val result = mutableListOf<ExpressionPart>()

        var counter = 0

        while (counter < calculation.length) {
            val currentChar = calculation[counter]
            when {
                currentChar in operationSymbols -> {
                    result.add(
                        ExpressionPart.Op(operationFromSymbol(currentChar))
                    )
                }
                currentChar.isDigit() -> {
                    counter = parseNumber(counter, result)
                    continue
                }
                currentChar in "()" -> {
                    parseParentheses(currentChar, result)
                }
            }
            counter++
        }

        return result
    }

    private fun parseNumber(startingIndex: Int, result: MutableList<ExpressionPart>): Int {
        var counter = startingIndex
        val numberAsString = buildString {
            while (counter < calculation.length && calculation[counter] in "0123456789.") {
                append(calculation[counter])
                counter++
            }
        }
        result.add(ExpressionPart.Number(numberAsString.toDouble()))

        return counter
    }

    private fun parseParentheses(currentChar: Char, result: MutableList<ExpressionPart>) {
        result.add(
            ExpressionPart.Parentheses(
                type = when (currentChar) {
                    '(' -> ParenthesesType.Opening
                    ')' -> ParenthesesType.Closing
                    else -> throw java.lang.IllegalArgumentException("Invalid parentheses type")
                }
            )
        )
    }
}

