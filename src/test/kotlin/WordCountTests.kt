import com.github.ajalt.clikt.testing.test
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class WordCountTests {

    private val testFile = "src/test/resources/test.txt"
    private val wk = WordCount()

    @Test
    fun `test command name`() {
        assertEquals(wk.commandName, "wk")
    }

    @Test
    fun `test help output`() {
        val result = wk.test("-h")
        assertEquals("""
Usage: wk [<options>] <filename>

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
        val result = wk.test("-c")
        assertEquals("""
Usage: wk [<options>] <filename>

Error: missing argument <filename>
""".trimStart(), result.stderr)
        assertEquals("", result.stdout)
        assertEquals(1, result.statusCode)
    }

    @Test
    fun `test bytes counter`() {
        val result = wk.test("-c $testFile")
        assertEquals("342190 $testFile\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test lines counter`() {
        val result = wk.test("-l $testFile")
        assertEquals("7145 $testFile\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test words counter`() {
        val result = wk.test("-w $testFile")
        assertEquals("58164 $testFile\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }
}