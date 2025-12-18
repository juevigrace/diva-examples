package com.diva.models

abstract class LocalSource<S>(override val source: S) : DataSource<S>(source)
