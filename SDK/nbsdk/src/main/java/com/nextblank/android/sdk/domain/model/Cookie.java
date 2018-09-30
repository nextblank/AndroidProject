package com.nextblank.android.sdk.domain.model;

import java.io.Serializable;
import java.sql.Date;

public class Cookie implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String mName;
    protected String mValue;
    protected String mComment;
    protected String mDomain;
    protected Date mExpiry;
    protected String mPath;
    protected boolean mSecure;
    protected int sessionOnly;
    protected int mVersion;
    protected long lastUsed;

    public Cookie() {
        super();
    }

    public Cookie(String cookie) {
        super();
        lastUsed = System.currentTimeMillis();
        String[] cs = cookie.split(";");
        int i = 0;
        for (String c : cs) {
            String key = "";
            String value = "";
            int pos = c.indexOf("=");
            if (pos < 0) {
                key = c;
            } else {
                key = c.substring(0, pos);
                value = c.substring(pos + 1);
            }
            if (key != null) key = key.trim();
            if (i == 0) {
                this.mName = key;
                this.mValue = value;
            } else {
                setProperty(key, value);
            }
            i++;
        }
    }

    @Override
    public String toString() {
        return mName + "=" + mValue;
    }

    public void setProperty(String key, String value) {
        if (key.equalsIgnoreCase("Comment"))
            setmComment(value);
        if (key.equalsIgnoreCase("Discard"))
            ;
        if (key.equalsIgnoreCase("Domain"))
            setmDomain(value);
        if (key.equalsIgnoreCase("Expires"))
            ;
        if (key.equalsIgnoreCase("Max-Age"))
            ;
        if (key.equalsIgnoreCase("Path"))
            setmPath(value);
        if (key.equalsIgnoreCase("Secure"))
            ;
        if (key.equalsIgnoreCase("Version"))
            ;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmValue() {
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public String getmComment() {
        return mComment;
    }

    public void setmComment(String mComment) {
        this.mComment = mComment;
    }

    public String getmDomain() {
        return mDomain;
    }

    public void setmDomain(String mDomain) {
        this.mDomain = mDomain;
    }

    public Date getmExpiry() {
        return mExpiry;
    }

    public void setmExpiry(Date mExpiry) {
        this.mExpiry = mExpiry;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public boolean ismSecure() {
        return mSecure;
    }

    public void setmSecure(boolean mSecure) {
        this.mSecure = mSecure;
    }

    public int getSessionOnly() {
        return sessionOnly;
    }

    public void setSessionOnly(int sessionOnly) {
        this.sessionOnly = sessionOnly;
    }

    public int getmVersion() {
        return mVersion;
    }

    public void setmVersion(int mVersion) {
        this.mVersion = mVersion;
    }

    public long getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(long lastUsed) {
        this.lastUsed = lastUsed;
    }
}
