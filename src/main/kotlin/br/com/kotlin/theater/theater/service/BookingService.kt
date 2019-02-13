package br.com.kotlin.theater.theater.service

import br.com.kotlin.theater.theater.data.BookingRepository
import br.com.kotlin.theater.theater.domain.Booking
import br.com.kotlin.theater.theater.domain.Performance
import br.com.kotlin.theater.theater.domain.Seat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BookingService {

    @Autowired
    lateinit var bookingRepository: BookingRepository

    fun isSeatFree(seat: Seat, performance: Performance): Booking? =
        bookingRepository.findBySeatAndPerformance(seat, performance).orElse(null)

    fun reserveSeat(seat: Seat, performance: Performance, customerName: String): Booking {
        val booking = Booking(0, customerName)
        booking.seat = seat
        booking.performance = performance
        return bookingRepository.save(booking)
    }

}