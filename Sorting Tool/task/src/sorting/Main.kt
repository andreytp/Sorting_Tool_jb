package sorting

import java.util.*
import kotlin.math.round

fun main(args: Array<String>) {
    var sortingType = "natural"
    if ("-sortingType" in args) {
        val sortTypeIndex = args.indexOf("-sortingType")
        if (args.size > sortTypeIndex + 1 && args[sortTypeIndex + 1].trim() in setOf("byCount", "natural")) {
            sortingType = args[sortTypeIndex + 1].trim()
        } else {
            println("No sorting type defined!")
            return
        }
    }
    var dataType = "word"
    if ("-dataType" in args) {
        val dataTypeIndex = args.indexOf("-dataType")
        if (args.size > dataTypeIndex + 1 && args[dataTypeIndex + 1].trim() in setOf("long", "word", "line")) {
            dataType = args[dataTypeIndex + 1].trim()
        } else {
            println("No data type defined!")
            return
        }
    }

    val unknownParams = args.filter{ it.first() == '-' }.filter{ it != "-sortingType" }.filter{ it != "-dataType" }

    if (unknownParams.isNotEmpty()) {
        for (item in unknownParams) {
            println("\"$item\" isn not a valid parameter. It will be skipped.")
        }
    }

    when (dataType) {
        "long" -> longSort(sortingType)
        else -> stringsSort(sortingType, dataType)
    }

}

private fun intSort(): Int {
    val str = mutableListOf<Long>()
    val scanner = Scanner(System.`in`)
    while (scanner.hasNextLong()) {
        val input = scanner.nextLong()
        str += input
    }
    str.sort()
    println("Total numbers: ${str.size}.")
    println("Sorted data: " + str.joinToString(separator = " "))
    return 0
}

@Suppress("UNCHECKED_CAST")
private fun longSort(sortingType: String): Int {
    val str = mutableListOf<Long>()
    val scanner = Scanner(System.`in`)
    while (scanner.hasNextLine()) {
        val input = scanner.nextLine()
        for (item in input.split(" ")) {
            if (item.contains(Regex("[^-*\\d+]"))) {
                println("\"${item}\" is not a long. It will be skipped.")
                continue
            }
            if (item.isNotEmpty()) {
                str += item.toLong()
            }
        }
    }
    println("Total numbers: ${str.size}.")
    if (sortingType == "natural") {
        println("Sorted data: " + str.sortedWith(compareBy { it }).joinToString(separator = " "))
        return 0
    }
    val sorted = str.groupingBy { it }.eachCount().entries.sortedWith(compareBy({ it.value }, { it.key }))
    for (mItem in sorted) {
        val item: Map.Entry<Double, Int> = mItem as Map.Entry<Double, Int>
        println(
            "${item.key.toInt()}: ${item.value} time(s), " +
                    "${round(100 * item.value.toDouble() / str.size.toDouble()).toInt()}%"
        )
    }
    return 0
}

fun stringsSort(sortingType: String, dataType: String): Int {
    val str = mutableListOf<String>()
    val scanner = Scanner(System.`in`)
    while (scanner.hasNextLine()) {
        val input = scanner.nextLine()

        if (dataType == "word") {
            str += input.split(' ').filter { it.isNotEmpty() }
        } else {
            str += input
        }


    }
    println("Total ${dataType}s: ${str.size}.")
    if (sortingType == "natural") {
        println(
            "Sorted data: " + str.sorted().joinToString(
                separator = if (dataType == "word") {
                    " "
                } else {
                    "\n"
                }
            )
        )
        return 0
    }
    val sorted = str.groupingBy { it }.eachCount().entries.sortedWith(compareBy({ it.value }, { it.key }))
    for (mItem in sorted) {
        val item: Map.Entry<String, Int> = mItem
        println(
            "${item.key}: ${item.value} time(s), " +
                    "${round(100 * item.value.toDouble() / str.size.toDouble()).toInt()}%"
        )
    }
    return 0
}
