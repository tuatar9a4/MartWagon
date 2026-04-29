package com.devd.martwagon.utils

import android.util.Log
import timber.log.Timber

class ReleaseTree  : Timber.Tree() {
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        // Release 빌드에서는 VERBOSE, DEBUG, INFO 레벨의 로그를 출력하지 않도록 설정합니다.
        return priority == Log.WARN || priority == Log.ERROR || priority == Log.ASSERT
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // 예시: FirebaseCrashlytics.getInstance().log(message)
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            // 릴리즈 모드에서 VERBOSE, DEBUG 로그는 출력하지 않도록 합니다.
            return
        }
        // 나머지 로그는 Android의 로그에 출력합니다.
        if (t == null) {
            Log.println(priority, tag, message)
        } else {
            Log.println(priority, tag, "$message\n${Log.getStackTraceString(t)}")
        }
    }
}

class DebugTree : Timber.DebugTree() {
    private var stackInfo :String? = null
    override fun createStackElementTag(element: StackTraceElement): String? {
        stackInfo = "(${element.fileName}:${element.lineNumber})#${element.methodName}"
        try {
            stackInfo = if((stackInfo?.length?:0) < 45) stackInfo?.padEnd(45,' ') else stackInfo?.substring(0,45)
        }catch (e :Exception){
            e.printStackTrace()
        }
        return stackInfo
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, "[${tag?:""}] $message", t)
    }
}