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
  -l          The number of lines in the input file is written to the standard
              output.
  -w          The number of words in the input file is written to the standard
              output.
  -h, --help  Show this message and exit
""".trimStart(), result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test missing file name`() {
        val result = wc.test("-c")
        assertEquals("""
Usage: wc [<options>] <filename>

Error: missing argument <filename>
""".trimStart(), result.stderr)
        assertEquals("", result.stdout)
        assertEquals(1, result.statusCode)
    }

    @Test
    fun `test bytes counter`() {
        val result = wc.test("-c $fileName")
        assertEquals("342190 $fileName\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test lines counter`() {
        val result = wc.test("-l $fileName")
        assertEquals("7145 $fileName\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test words counter`() {
        val result = wc.test("-w $fileName")
        assertEquals("58164 $fileName\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }
}