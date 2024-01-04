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
            countBytes -> Files.readAllBytes(filePath).size
            countLines -> Files.readAllLines(filePath).size
            countWords -> Files.readString(filePath).trim().split("\\s+".toRegex()).size
            countCharacters -> {
                if (Charset.defaultCharset().displayName().contains("UTF-8", ignoreCase = true)) {
                    Files.readString(filePath).length
                } else {
                    Files.size(filePath)
                }
            }
            else -> 0
        }
        echo("  $counter $fileName")
    }
}

fun main(args: Array<String>) = WordCount().main(args)
