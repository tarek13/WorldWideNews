package com.link.worldwidenews.utils.schedulers

import com.link.worldwidenews.domain.util.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestingSchedulerProvider : SchedulerProvider {
    override val mainThread: Scheduler
        get() = Schedulers.trampoline()
    override val io: Scheduler
        get() = Schedulers.trampoline()
    override val newThread: Scheduler
        get() = Schedulers.newThread()
}