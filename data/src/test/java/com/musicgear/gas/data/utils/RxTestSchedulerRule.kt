package com.musicgear.gas.data.utils

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxTestSchedulerRule : TestRule {

  override fun apply(base: Statement?, description: Description?): Statement =
    object : Statement() {
      override fun evaluate() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setSingleSchedulerHandler { Schedulers.trampoline() }

        base?.evaluate()
        RxJavaPlugins.reset()
      }
    }
}