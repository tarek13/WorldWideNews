package com.link.worldwidenews.domain.util

import io.reactivex.Scheduler

interface SchedulerProvider {
    val mainThread: Scheduler
    val io: Scheduler
    val newThread: Scheduler
}