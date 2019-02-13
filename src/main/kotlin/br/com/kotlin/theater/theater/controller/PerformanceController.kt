package br.com.kotlin.theater.theater.controller

import br.com.kotlin.theater.theater.data.PerformanceRepository
import br.com.kotlin.theater.theater.domain.Performance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView



@Controller
@RequestMapping("performances")
class PerformanceController() {

    @Autowired
    lateinit var performanceRepository: PerformanceRepository

    @RequestMapping("")
    fun performancesHomePage() =
            ModelAndView("performances/home","performances", performanceRepository.findAll())

    @RequestMapping("/add")
    fun addPerformance() =
            ModelAndView("performances/add","performance", Performance(0,""))

    @RequestMapping("save", method = [RequestMethod.POST])
    fun savePerformance(performance: Performance) : String {
        performanceRepository.save(performance)
        return "redirect:/performances/"
    }

}