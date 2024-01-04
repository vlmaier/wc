import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path

class WordCount : CliktCommand(name = "wk") {
    private val countBytes by option("-c").flag()
        .help { "The number of bytes in the input file or stdin is written to the stdout." }
    private val countLines by option("-l").flag()
        .help { "The number of lines in the input file or stdin is written to the stdout." }
    private val countCharacters by option("-m").flag()
        .help { "The number of characters in the input file or stdin is written to the stdout. " +
                "If the current locale does not support multibyte characters, this is equivalent to the -c option. " +
                "This will cancel out any prior usage of the -c option." }
    private val countWords by option("-w").flag()
        .help { "The number of words in the input file or stdin is written to the stdout." }
    private val fileName by argument().optional()

    override fun run() {
        val bufferedReader = if (fileName.isNullOrBlank()) {
            InputStreamReader(System.`in`).buffered()
        } else {
            Files.newBufferedReader(Path.of(fileName!!))
        }
        bufferedReader.use {
            val content = it.readText()
            val counter = when {
                countBytes -> countBytes(content)
                countLines -> countLines(content)
                countWords -> countWords(content)
                countCharacters -> countCharacters(content)
                else -> defaultCount(content)
            }
            echo("  $counter ${fileName ?: ""}".trimEnd())
        }
    }

    private fun defaultCount(content: String) =
        "${countLines(content)}  ${countWords(content)}  ${countCharacters(content)}"

    private fun countBytes(content: String) = content.toByteArray(Charsets.UTF_8).size
    private fun countLines(content: String) = content.split("\n").size - 1
    private fun countWords(content: String) = content.trim().split("\\s+".toRegex()).size

    private fun countCharacters(content: String): Int {
        return if (Charset.defaultCharset().displayName().contains(Charsets.UTF_8.name(), ignoreCase = true)) {
            content.length
        } else {
            countBytes(content)
        }
    }
}

fun main(args: Array<String>) = WordCount().main(args)
