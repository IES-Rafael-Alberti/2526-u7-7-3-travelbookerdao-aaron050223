package es.iesra.datos
import es.iesra.dominio.Reserva
import java.io.File

class ReservaDAO(private val rutaFichero: String): IReservaDAO {
    private val fichero = File(rutaFichero)

    override fun agregar(reserva: Reserva): Boolean {
        if (!fichero.exists()) {
            return false
        } else {
            fichero.writeText(reserva.toString())
            return true
        }
    }


}