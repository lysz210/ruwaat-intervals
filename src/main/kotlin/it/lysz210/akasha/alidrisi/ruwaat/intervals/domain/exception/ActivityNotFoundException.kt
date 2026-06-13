package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.exception


class ActivityNotFoundException (activityId: String, failure: Throwable? = null):
    RuntimeException("Active $activityId Not Found!", failure)