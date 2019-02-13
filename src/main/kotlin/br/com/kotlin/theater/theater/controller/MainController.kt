package br.com.kotlin.theater.theater.controller

import br.com.kotlin.theater.theater.data.PerformanceRepository
import br.com.kotlin.theater.theater.data.SeatRepository
import br.com.kotlin.theater.theater.domain.Booking
import br.com.kotlin.theater.theater.domain.Performance
import br.com.kotlin.theater.theater.domain.Seat
import br.com.kotlin.theater.theater.service.BookingService
import br.com.kotlin.theater.theater.service.TheaterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {

    @Autowired
    lateinit var theaterService: TheaterService

    @Autowired
    lateinit var bookingService: BookingService

    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun homePage(): ModelAndView {
        val model = generateModel(CheckAvailabilityBackingBean())
        return ModelAndView("seatBooking", model)
    }

    @RequestMapping("bootstrap")
    fun createInitialData(): ModelAndView {
        val seats = theaterService.seats
        seatRepository.saveAll(seats)
        return homePage()
    }

    @RequestMapping("checkAvailability", method = [RequestMethod.POST])
    fun checkAvailability(bean: CheckAvailabilityBackingBean): ModelAndView {
        val seat = theaterService.find(bean.selectedSeatNum, bean.selectedSeatRow)
        val performance = performanceRepository.findById(bean.selectedPerformance!!).get()
        val result: Booking? = bookingService.isSeatFree(seat!!, performance)
        bean.available = result == null
        bean.booking = result
        bean.performance = performance
        bean.seat = seat
        return ModelAndView("seatBooking", generateModel(bean))
    }

    @RequestMapping("booking", method = [RequestMethod.POST])
    fun bookASeat(bean: CheckAvailabilityBackingBean): ModelAndView {
        val booking = bookingService.reserveSeat(bean.seat!!, bean.performance!!, bean.customerName)
        return ModelAndView("bookingConfirmed", "booking", booking)
    }

    private fun generateModel(bean: CheckAvailabilityBackingBean) =
        mapOf("bean" to bean,
            "performances" to performanceRepository.findAll(),
            "seatNums" to 1..36,
            "seatRows" to 'A'..'O')
}

class CheckAvailabilityBackingBean {


    var selectedSeatNum:Int = 1
    var selectedSeatRow:Char = 'A'
    var selectedPerformance:Long? = null
    var customerName: String = ""
    var booking: Booking? = null
    var available: Boolean? = null
    var performance: Performance? = null
    var seat: Seat? = null
}