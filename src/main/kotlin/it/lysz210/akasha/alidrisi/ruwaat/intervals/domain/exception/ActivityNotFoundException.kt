package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.exception

import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Activity

class ActivityNotFoundException (activityId: Activity.ActivityId, failure: Throwable? = null):
    RuntimeException("Activity $activityId Not Found!", failure)