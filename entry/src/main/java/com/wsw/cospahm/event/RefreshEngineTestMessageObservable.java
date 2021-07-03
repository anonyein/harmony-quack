package com.wsw.cospahm.event;

public class RefreshEngineTestMessageObservable extends MessageObservable<RefreshEngineTestMessageObserver> {

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     */
    private volatile static RefreshEngineTestMessageObservable instance;


    /**
     * 私有的构造函数
     */
    private RefreshEngineTestMessageObservable() {

    }

    public static RefreshEngineTestMessageObservable getInstance() {
        if (instance == null) {
            synchronized (RefreshEngineTestMessageObserver.class) {
                if (instance == null) {
                    instance = new RefreshEngineTestMessageObservable();
                }
            }
        }
        return instance;
    }

    @Override
    public void notifyObservers(Object... unreadMessage) {
        try {
            for (RefreshEngineTestMessageObserver observer : observerList) {
                observer.onRefreshEngineTestMessage((String) unreadMessage[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}