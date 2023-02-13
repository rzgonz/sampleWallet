package com.rzgonz.nutechwallet.modules.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by rzgonz on 13/02/23.
 *
 */
@Parcelize
class UpdateProfileArgs(
    val firstName: String,
    val lastName: String
):Parcelable