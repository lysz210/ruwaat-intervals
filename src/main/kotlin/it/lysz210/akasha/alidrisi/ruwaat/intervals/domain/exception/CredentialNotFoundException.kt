package it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.exception

import it.lysz210.akasha.alidrisi.ruwaat.intervals.domain.model.Credential


class CredentialNotFoundException (clientId: Credential.ClientId, failure: Throwable? = null):
    RuntimeException("Intervals.icu Credentials could not be found for clientId: ${clientId.value}", failure)