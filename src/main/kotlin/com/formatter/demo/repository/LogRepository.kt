package com.formatter.demo.repository

import com.formatter.demo.model.LogModel
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.math.log

@Component
class LogRepository {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val logs = mutableListOf<LogModel>()

    fun add(log: LogModel) {
        logger.info("Adding log: {}", log)
        logs.add(log)
    }

    fun findAll() = logs
}
