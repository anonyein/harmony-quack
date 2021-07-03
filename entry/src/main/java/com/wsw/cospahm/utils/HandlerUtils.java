package com.wsw.cospahm.utils;

import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;

/**
 * Created by shuiwang.wang on 2018/8/27.
 */

public class HandlerUtils {

    private static EventRunner workThread;

    private static EventHandler workHandler, mainHandler;

    public static void init() {
        workThread = EventRunner.create("worker");
        workHandler = new EventHandler(workThread);
        mainHandler = new EventHandler(EventRunner.getMainEventRunner());
    }

    /**
     * 工作线程 退出
     */
    public static void quit() {
        if (workThread != null) {
            workThread.stop();
            workThread = null;
        }

        if (workHandler != null) {
            workHandler.removeAllEvent();
            workHandler = null;
        }

        if (mainHandler != null) {
            mainHandler.removeAllEvent();
            mainHandler = null;
        }
    }


    /**
     * 是否在UI线程上
     *
     * @return true：UI线程 false：工作线程
     */
    public static boolean isUIThread() {
        return EventRunner.current() == EventRunner.getMainEventRunner();
    }


    /**
     * UI线程 操作行为
     *
     * @param runnable Runnable
     */
    public static void runOnUiThread(Runnable runnable) {

        if (isUIThread()) {
            runnable.run();
        } else {
            if (mainHandler == null) {
                throw new RuntimeException("HandlerUtils init方法未初始化");
            }
            mainHandler.postTask(runnable);
        }
    }

    /**
     * 工作线程
     *
     * @param runnable Runnable
     */
    public static void runWorkThread(Runnable runnable) {
        if (isUIThread()) {
            if (workHandler == null) {
                throw new RuntimeException("HandlerUtils init方法未初始化");
            }
            workHandler.postTask(runnable);
        } else {
            runnable.run();
        }
    }

    public static void runOnUiThreadDelay(Runnable runnable, long delayMillis) {
        if (mainHandler == null) {
            throw new RuntimeException("HandlerUtils init方法未初始化");
        }
        mainHandler.postTimingTask(runnable, delayMillis);
    }

    public static void removeRunable(Runnable runnable) {
        if (mainHandler == null) {
            throw new RuntimeException("HandlerUtils init方法未初始化");
        }
        mainHandler.removeTask(runnable);
    }
}
