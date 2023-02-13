package com.rzgonz.nutechwallet.data.remote

/**
 * Created by rzgonz on 12/02/23.
 *
 */
class RemoteDataSource(
    private val apiServices: ApiServices
) : ApiServices by apiServices