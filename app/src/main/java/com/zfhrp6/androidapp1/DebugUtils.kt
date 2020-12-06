package com.zfhrp6.androidapp1

class DebugUtils {
    companion object {
        fun getEnclosingNames(): StackTraceElement = Thread.currentThread().stackTrace[3]
    }
}
