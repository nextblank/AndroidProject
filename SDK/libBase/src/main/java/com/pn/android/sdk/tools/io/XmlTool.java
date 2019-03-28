package com.pn.android.sdk.tools.io;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.pn.android.sdk.tools.Tools;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class XmlTool {
    private static final Object lock = new Object();
    private static volatile XmlTool instance;

    public static XmlTool instance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new XmlTool();
                }
            }
        }
        return instance;
    }

    private Map<String, String> mMap;

    /**
     * get system setting parameter string
     *
     * @return setting parameter value string
     */
    public String getValue(String name) {
        String result = "";
        String v = mMap.get(name);
        if (v != null) {
            result = v.trim();
        }
        return result;
    }

    /**
     * @param xmlResId
     */
    public XmlTool name(int xmlResId) {
        try {
            return loadSetting(xmlResId);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private XmlTool loadSetting(int xmlResId) throws XmlPullParserException, IOException {
        Resources res = Tools.app().getResources();
        XmlResourceParser parser = res.getXml(xmlResId);
        mMap = new HashMap<String, String>();
        int eventType = parser.next();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.getName().equalsIgnoreCase("param")) {
                    String name = parser.getAttributeValue(null, "name");
                    String value = parser.getAttributeValue(null, "value");
                    mMap.put(name, value);
                }
            }
            eventType = parser.next();
        }
        return this;
    }
}