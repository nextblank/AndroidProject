package com.nextblank.sdk.domain.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Session单例模式，用于不同Activity之间传递对象，包括Button等空间对象
 */
public class Session {
    private Map objectContainer;
    private static Session session;

    private Session() {
        objectContainer = new HashMap();
    }

    public static Session getSession() {
        if (session == null) {
            session = new Session();
            return session;
        }
        return session;

    }

    public void put(Object key, Object value) {
        objectContainer.put(key, value);
    }

    public Object get(Object key) {
        return objectContainer.get(key);
    }

    public void clear() {
        objectContainer.clear();
    }

    public void remove(Object key) {
        objectContainer.remove(key);
    }


}
