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
Usage: wk [<options>] [<filename>]

Options:
  -c          The number of bytes in the input file or stdin is written to the
              stdout.
  -l          The number of lines in the input file or stdin is written to the
              stdout.
  -m          The number of characters in the input file or stdin is written to
              the stdout. If the current locale does not support multibyte
              characters, this is equivalent to the -c option. This will cancel
              out any prior usage of the -c option.
  -w          The number of words in the input file or stdin is written to the
              stdout.
  -h, --help  Show this message and exit
""".trimStart(), result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test missing file name`() {
        val result = wk.test("-c")
        assertEquals("  0\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test bytes counter`() {
        val result = wk.test("-c $testFile")
        assertEquals("  342190 $testFile\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test lines counter`() {
        val result = wk.test("-l $testFile")
        assertEquals("  7146 $testFile\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test characters counter`() {
        val result = wk.test("-m $testFile")
        assertEquals("  339292 $testFile\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test words counter`() {
        val result = wk.test("-w $testFile")
        assertEquals("  58164 $testFile\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }

    @Test
    fun `test default counter`() {
        val result = wk.test(testFile)
        assertEquals("  7146  58164  339292 $testFile\n", result.stdout)
        assertEquals("", result.stderr)
        assertEquals(0, result.statusCode)
    }
}