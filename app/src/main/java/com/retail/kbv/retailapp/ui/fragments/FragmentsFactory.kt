package com.retail.kbv.retailapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.util.SimpleArrayMap

object FragmentsFactory{
    private val FragClassesMap = SimpleArrayMap<String, Class<*>>()

    @Throws(ClassNotFoundException::class, IllegalAccessException::class, InstantiationException::class)
    fun buildFragment(context: Context, fragmentName: String): BaseFragment<*,*,*> {
        val fragmentClazz = getFragmentClass(context, fragmentName)
        return fragmentClazz.newInstance() as BaseFragment<*, *, *>
    }

    @Throws(ClassNotFoundException::class, IllegalAccessException::class, InstantiationException::class)
    fun buildFragment(context: Context, fragmentName: String, data: Bundle?): BaseFragment<*,*,*> {
        val fragment = buildFragment(context, fragmentName)
        if (data != null && !data.isEmpty) {
            fragment.setArguments(data)
        }
        return fragment
    }

    @Throws(ClassNotFoundException::class)
    fun getFragmentClass(context: Context, fragmentName: String): Class<*> {
        var fragmentClazz = FragClassesMap.get(fragmentName)
        if (fragmentClazz == null) {
            fragmentClazz = context.classLoader.loadClass(fragmentName)
            FragClassesMap.put(fragmentName, fragmentClazz)
        }
        return fragmentClazz
    }
}

