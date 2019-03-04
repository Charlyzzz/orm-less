import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ReglaDiaParticularTest {

    @Test
    fun `es feriado si el dia es el mismo de la fecha`() {
        val hoy = LocalDate.of(2018, 2, 14)
        val paraEstaRegla = ReglaDiaParticular(hoy)
        reQue { paraEstaRegla esFeriado hoy }
    }

    @Test
    fun `no es feriado si no tiene la misma fecha`() {
        val hoy = LocalDate.of(2018, 2, 14)
        val mañana = hoy.plusDays(1)
        val paraEstaRegla = ReglaDiaParticular(mañana)
        niEnPedo { paraEstaRegla esFeriado hoy }
    }

    private fun niEnPedo(block: () -> Boolean) = assertFalse(null, block)
    private fun reQue(block: () -> Boolean) = assertTrue(null, block)
}
