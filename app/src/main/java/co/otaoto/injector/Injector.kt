package co.otaoto.injector

import co.otaoto.api.Api
import co.otaoto.api.WebApi

val API: Api get() = Injector.api

object Injector {
    internal var api: Api = WebApi()
}
