package com.wsw.cospahm.event;

import java.util.ArrayList;

public abstract class MessageObservable<T> {

    public final ArrayList<T> observerList = new ArrayList<>();

    /**
     * 注册观察者对象
     *
     * @param t
     */
    public void registerObserver(T t) {
        checkNull(t);
        observerList.add(t);
    }

    /**
     * 注销观察者对象
     *
     * @param t
     */
    public void unRegisterObserver(T t) {
        checkNull(t);
        observerList.remove(t);
    }

    /**
     * 清空所有的观察者对象
     */
    public void unRegisterObserverOfAll() {
        if (null != observerList) {
            observerList.clear();
        }
    }

    /**
     * 判断所传的观察者对象是否为空
     *
     * @param t
     */
    private void checkNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
    }

    /**
     * 通知观察者做出行为改变
     *
     * @param unreadMessage
     */
    public abstract void notifyObservers(Object... unreadMessage);
}
