package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.exception

import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.credential.Key


class CredentialNotFoundException (key: Key, failure: Throwable? = null):
    RuntimeException("Active Credentials could not be found for key: ${key.qualifiedId}", failure)