package es.iesra.datos

import es.iesra.dominio.Reserva

interface IReservaDAO {
    fun guardar(reserva: Reserva)
    fun leer(): List<Reserva>
}