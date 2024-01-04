import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path

class WordCount : CliktCommand(name = "wk") {
    private val countBytes by option("-c").flag()
        .help { "The number of bytes in the input file is written to the standard output." }
    private val countLines by option("-l").flag()
        .help { "The number of lines in the input file is written to the standard output." }
    private val countCharacters by option("-m").flag()
        .help { "The number of characters in the input file is written to the standard output. " +
                "If the current locale does not support multibyte characters, this is equivalent to the -c option. " +
                "This will cancel out any prior usage of the -c option." }
    private val countWords by option("-w").flag()
        .help { "The number of words in the input file is written to the standard output." }
    private val fileName by argument()

    override fun run() {
        val filePath = Path.of(fileName)
        val counter = when {
            countBytes -> countBytes(filePath)
            countLines -> countLines(filePath)
            countWords -> countWords(filePath)
            countCharacters -> countCharacters(filePath)
            else -> defaultCount(filePath)
        }
        echo("  $counter $fileName")
    }

    private fun defaultCount(filePath: Path): String {
        val lines = countLines(filePath)
        val words = countWords(filePath)
        val characters = countCharacters(filePath)
        return "$lines  $words  $characters"
    }

    private fun countBytes(filePath: Path): Int = Files.readAllBytes(filePath).size
    private fun countLines(filePath: Path): Int = Files.readAllLines(filePath).size
    private fun countWords(filePath: Path): Int = Files.readString(filePath).trim().split("\\s+".toRegex()).size

    private fun countCharacters(filePath: Path): Int {
        return if (Charset.defaultCharset().displayName().contains("UTF-8", ignoreCase = true)) {
            Files.readString(filePath).length
        } else {
            countBytes(filePath)
        }
    }
}

fun main(args: Array<String>) = WordCount().main(args)
