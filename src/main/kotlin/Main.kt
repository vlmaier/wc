import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import java.nio.file.Files
import java.nio.file.Path

class WordCount : CliktCommand(name = "wk") {
    private val countBytes by option("-c").flag()
        .help { "The number of bytes in the input file is written to the standard output." }
    private val countLines by option("-l").flag()
        .help { "The number of lines in the input file is written to the standard output." }
    private val countWords by option("-w").flag()
        .help { "The number of words in the input file is written to the standard output." }
    private val fileName by argument()

    override fun run() {
        val filePath = Path.of(fileName)
        val count = when {
            countBytes -> Files.readAllBytes(filePath).size
            countLines -> Files.readAllLines(filePath).size
            countWords -> Files.readString(filePath).trim().split("\\s+".toRegex()).size
            else -> 0
        }
        echo("$count $fileName")
    }
}

fun main(args: Array<String>) = WordCount().main(args)
