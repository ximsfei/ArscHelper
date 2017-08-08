package com.ximsfei.arschelper.utils

class Log {
    static TAG = "ArscHelper: "
    def static d(String msg) {
        println(TAG + msg)
    }

    def static d(String tag, String msg) {
        println(TAG + tag + " -> " + msg)
    }
}
