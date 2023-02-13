package com.rzgonz.nutechwallet.data

import com.rzgonz.nutechwallet.data.remote.ApiServices
import com.rzgonz.nutechwallet.data.remote.RemoteDataSource

/**
 * Created by rzgonz on 12/02/23.
 *
 */
class AppRepository(
    private val remoteDataSource: RemoteDataSource
) : ApiServices by remoteDataSource {

}