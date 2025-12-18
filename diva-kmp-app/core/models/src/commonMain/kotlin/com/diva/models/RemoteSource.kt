package com.diva.models

abstract class RemoteSource<S>(override val source: S) : DataSource<S>(source)
