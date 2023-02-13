package com.rzgonz.nutechwallet.core.provider

import org.koin.core.module.Module

interface BaseModuleProvider {

    val modules: List<Module>

}