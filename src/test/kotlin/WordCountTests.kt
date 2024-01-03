import com.github.ajalt.clikt.testing.test
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class WordCountTests {

    private val fileName = "src/main/resources/test.txt"
    private val wc = WordCount()

    @Test
    fun `test command name`() {
        assertEquals(wc.commandName, "wc")
    }

    @Test
    fun `test help output`() {
        val result = wc.test("-h")
        assertEquals("""
Usage: wc [<options>] <filename>

Options:
  -c          The number of bytes in the input file is written to the standard
              output.
  -h, --help  Show this message and exit
""".trimStart(), result.stdout)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test missing file name`() {
        val result = wc.test("-c")
        assertEquals("", result.stdout)
        assertEquals("""
Usage: wc [<options>] <filename>

Error: missing argument <filename>
""".trimStart(), result.stderr)
        assertEquals(1, result.statusCode)
    }

    @Test
    fun `test bytes counter`() {
        val result = wc.test("-c $fileName")
        assertEquals("342190 $fileName\n", result.stdout)
        assertEquals(0, result.statusCode)
    }
}