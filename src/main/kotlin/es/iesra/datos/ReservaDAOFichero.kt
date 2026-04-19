package es.iesra.datos
import es.iesra.dominio.*
import java.io.File

class ReservaDAOFichero(private val rutaFichero: String): IReservaDAO {
    private val fichero = File(rutaFichero)

    override fun guardar(reserva: Reserva) {
        val linea = when (reserva) {
            is ReservaHotel -> "Hotel,${reserva.id},${reserva.fechaCreacion},${reserva.descripcion},${reserva.ubicacion},${reserva.numeroNoches}\n"
            is ReservaVuelo -> "Vuelo,${reserva.id},${reserva.fechaCreacion},${reserva.descripcion},${reserva.origen},${reserva.destino},${reserva.horaVuelo}\n"
            else -> ""
        }

        if (linea.isNotEmpty()) {
            fichero.appendText(linea)
        }
    }

    override fun leer(): List<Reserva> {
        val lista = mutableListOf<Reserva>()
        if (!fichero.exists()) return lista

        fichero.forEachLine { linea ->
            if (linea.isNotBlank()) {
                val reservaDatos = linea.split(",")
                val reserva = when (reservaDatos[0]) {
                    "Hotel" -> ReservaHotel.creaInstancia(reservaDatos[3],reservaDatos[4],reservaDatos[5].toInt())
                    "Vuelo" -> ReservaVuelo.creaInstancia(reservaDatos[3],reservaDatos[4],reservaDatos[5],reservaDatos[6])
                    else -> null
                }
                if (reserva != null) {
                    lista.add(reserva)
                }
            }
        }
        return lista.toList()
    }

    override fun borrar(tipo: String, id: String): Boolean {
        if (!fichero.exists()) return false

        val lineasOriginales = fichero.readLines()
        val lineasRestantes = lineasOriginales.filterNot { linea ->
            val campos = linea.split(",")
            if (campos.size >= 2) {
                val coincideTipo = campos[0].equals(tipo, ignoreCase = true)
                val coincideId = campos[1].trim() == id.trim()
                coincideTipo && coincideId
            } else false
        }

        return if (lineasOriginales.size != lineasRestantes.size) {
            fichero.writeText(lineasRestantes.joinToString("\n") + if (lineasRestantes.isNotEmpty()) "\n" else "")
            true
        } else {
            false
        }
    }
}