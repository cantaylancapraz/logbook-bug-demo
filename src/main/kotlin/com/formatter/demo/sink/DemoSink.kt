package com.formatter.demo.sink

import com.formatter.demo.model.LogModel
import com.formatter.demo.repository.LogRepository
import org.springframework.stereotype.Service
import org.zalando.logbook.Correlation
import org.zalando.logbook.HttpLogFormatter
import org.zalando.logbook.HttpRequest
import org.zalando.logbook.HttpResponse
import org.zalando.logbook.Precorrelation
import org.zalando.logbook.Sink

@Service
class DemoSink(
    private val formatter: HttpLogFormatter,
    private val logRepository: LogRepository
) : Sink {
    override fun write(precorrelation: Precorrelation, request: HttpRequest) {}

    override fun write(correlation: Correlation, request: HttpRequest, response: HttpResponse) {
        val responseLog = formatter.format(correlation, response)
        val requestLog = formatter.format(correlation, request)
        logRepository.add(LogModel(requestLog, responseLog))
    }
}
