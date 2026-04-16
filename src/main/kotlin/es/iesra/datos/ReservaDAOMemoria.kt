package es.iesra.datos

import es.iesra.dominio.Reserva

class ReservaDAOMemoria: IReservaDAO {
    private val lista = mutableListOf<Reserva>()

    override fun guardar(reserva: Reserva) {
        lista.add(reserva)
    }

    override fun leer(): List<Reserva> {
        return lista.toList()
    }
}