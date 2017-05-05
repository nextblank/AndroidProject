package com.nextblank.sdk.tools;

import android.app.Application;
import android.content.Context;

import com.nextblank.sdk.tools.apk.ApkTool;
import com.nextblank.sdk.tools.app.AppTool;
import com.nextblank.sdk.tools.app.BarTool;
import com.nextblank.sdk.tools.app.CrashTool;
import com.nextblank.sdk.tools.app.FragmentTool;
import com.nextblank.sdk.tools.app.InputMethodTool;
import com.nextblank.sdk.tools.app.IntentTool;
import com.nextblank.sdk.tools.app.ProcessTool;
import com.nextblank.sdk.tools.app.SnackbarTool;
import com.nextblank.sdk.tools.app.ToastTool;
import com.nextblank.sdk.tools.constants.ConstantsTool;
import com.nextblank.sdk.tools.convert.ConvertTool;
import com.nextblank.sdk.tools.device.DeviceTool;
import com.nextblank.sdk.tools.device.NetworkTool;
import com.nextblank.sdk.tools.image.BitmapTool;
import com.nextblank.sdk.tools.io.CloseTool;
import com.nextblank.sdk.tools.io.FileTool;
import com.nextblank.sdk.tools.io.SPTool;
import com.nextblank.sdk.tools.io.StorageTool;
import com.nextblank.sdk.tools.io.XmlTool;
import com.nextblank.sdk.tools.log.LogTool;
import com.nextblank.sdk.tools.regex.RegexTool;
import com.nextblank.sdk.tools.secure.SecureAESTool;
import com.nextblank.sdk.tools.secure.SecureBase64Tool;
import com.nextblank.sdk.tools.secure.SecureDES3Tool;
import com.nextblank.sdk.tools.secure.SecureDESTool;
import com.nextblank.sdk.tools.secure.SecureHexTool;
import com.nextblank.sdk.tools.secure.SecureMD5Tool;
import com.nextblank.sdk.tools.secure.SecureRSATool;
import com.nextblank.sdk.tools.secure.SecureTool;
import com.nextblank.sdk.tools.shell.ShellTool;
import com.nextblank.sdk.tools.string.PinYinTool;
import com.nextblank.sdk.tools.string.SpannableStringTool;
import com.nextblank.sdk.tools.string.StringTool;
import com.nextblank.sdk.tools.thread.ThreadPoolTool;
import com.nextblank.sdk.tools.time.LunarTool;
import com.nextblank.sdk.tools.time.TimeTool;
import com.nextblank.sdk.tools.unit.UnitTool;
import com.nextblank.sdk.tools.zip.ZipTool;

import java.lang.reflect.Method;

public class Tools {

    private static boolean mOpenLog;
    private static Application mApp;
    private static ApkTool mApk;
    private static StringTool mString;
    private static SpannableStringTool mSpannableString;
    private static RegexTool mRegex;
    private static LogTool mLog;
    private static SecureTool mSecure;
    private static SecureAESTool mAES;
    private static SecureBase64Tool mBase64;
    private static SecureDES3Tool mDES3;
    private static SecureDESTool mDES;
    private static SecureHexTool mHex;
    private static SecureMD5Tool mMD5;
    private static SecureRSATool mRSA;
    private static StorageTool mStorage;
    private static AppTool mAppTool;
    private static DeviceTool mDevice;
    private static BitmapTool mBitmap;
    private static InputMethodTool mInputMethodTool;
    private static ToastTool mToast;
    private static SnackbarTool mSnackbar;
    private static NetworkTool mNetwork;
    private static SPTool mSP;
    private static CloseTool mClose;
    private static ConstantsTool mConstants;
    private static ConvertTool mConvert;
    private static FileTool mFile;
    private static IntentTool mIntent;
    private static ProcessTool mProcess;
    private static ShellTool mShell;
    private static TimeTool mTime;
    private static LunarTool mLunar;
    private static UnitTool mUnit;
    private static ZipTool mZip;
    private static BarTool mBar;
    private static PinYinTool mPinYin;
    private static FragmentTool mFragment;
    private static CrashTool mCrash;
    private static ThreadPoolTool mThreadPool;
    private static XmlTool mXml;

    public Tools() {
        throw new UnsupportedOperationException("not support instantiate Tools");
    }

    public static void init(Application app) {
        if (mApp == null) {
            mApp = app;
        }
    }

    /**
     * if open the Tools log msg.
     *
     * @param isOpenLog
     */
    public static void openToolsLog(boolean isOpenLog) {
        mOpenLog = isOpenLog;
    }

    public static boolean isToolsLogging() {
        return mOpenLog;
    }

    public static Application app() {
        if (mApp == null) {
            try {
                try {
                    // 在IDE进行布局预览时使用
                    Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                    Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                    Context context = (Context) method.invoke(null);
                    mApp = new MockApplication(context);
                } catch (Throwable ignored) {
                    throw new RuntimeException("please invoke x.Ext.init(app) on Application#onCreate()"
                            + " and register your Application in manifest.");
                }
            } catch (Throwable ignored) {
                throw new RuntimeException("please invoke Tools.init(app) on Application#onCreate()"
                        + " and register your Application in manifest.");
            }
        }
        return mApp;
    }

    public static ApkTool apk() {
        if (mApk == null) {
            mApk = ApkTool.instance();
        }
        return mApk;
    }

    public static StringTool string() {
        if (mString == null) {
            mString = StringTool.instance();
        }
        return mString;
    }

    public static SpannableStringTool spannable() {
        if (mSpannableString == null) {
            mSpannableString = SpannableStringTool.instance();
        }
        return mSpannableString;
    }


    public static RegexTool regex() {
        if (mRegex == null) {
            mRegex = RegexTool.instance();
        }
        return mRegex;
    }

    public static LogTool log() {
        if (mLog == null) {
            mLog = LogTool.instance();
        }
        return mLog;
    }

    public static SecureTool secure() {
        if (mSecure == null) {
            mSecure = SecureTool.instance();
        }
        return mSecure;
    }

    public static SecureAESTool secureAES() {
        if (mAES == null) {
            mAES = SecureAESTool.instance();
        }
        return mAES;
    }

    public static SecureBase64Tool secureBase64() {
        if (mBase64 == null) {
            mBase64 = SecureBase64Tool.instance();
        }
        return mBase64;
    }

    public static SecureDES3Tool secureDES3() {
        if (mDES3 == null) {
            mDES3 = SecureDES3Tool.instance();
        }
        return mDES3;
    }

    public static SecureDESTool secureDES() {
        if (mDES == null) {
            mDES = SecureDESTool.instance();
        }
        return mDES;
    }

    public static SecureHexTool secureHex() {
        if (mHex == null) {
            mHex = SecureHexTool.instance();
        }
        return mHex;
    }

    public static SecureMD5Tool secureMD5() {
        if (mMD5 == null) {
            mMD5 = SecureMD5Tool.instance();
        }
        return mMD5;
    }

    public static SecureRSATool secureRSA() {
        if (mRSA == null) {
            mRSA = SecureRSATool.instance();
        }
        return mRSA;
    }

    public static StorageTool storage() {
        if (mStorage == null) {
            mStorage = StorageTool.instance();
        }
        return mStorage;
    }

    public static AppTool appTool() {
        if (mAppTool == null) {
            mAppTool = AppTool.instance();
        }
        return mAppTool;
    }

    public static DeviceTool device() {
        if (mDevice == null) {
            mDevice = DeviceTool.instance();
        }
        return mDevice;
    }

    public static BitmapTool bitmap() {
        if (mBitmap == null) {
            mBitmap = BitmapTool.instance();
        }
        return mBitmap;
    }

    public static InputMethodTool inputMethod() {
        if (mInputMethodTool == null) {
            mInputMethodTool = InputMethodTool.instance();
        }
        return mInputMethodTool;
    }

    public static ToastTool toast() {
        if (mToast == null) {
            mToast = ToastTool.instance();
        }
        return mToast;
    }

    public static SnackbarTool snackbar() {
        if (mSnackbar == null) {
            mSnackbar = SnackbarTool.instance();
        }
        return mSnackbar;
    }

    public static NetworkTool network() {
        if (mNetwork == null) {
            mNetwork = NetworkTool.instance();
        }
        return mNetwork;
    }

    public static SPTool sp() {
        if (mSP == null) {
            mSP = SPTool.instance();
        }
        return mSP;
    }

    public static CloseTool close() {
        if (mClose == null) {
            mClose = CloseTool.instance();
        }
        return mClose;
    }

    public static ConstantsTool constants() {
        if (mConstants == null) {
            mConstants = ConstantsTool.instance();
        }
        return mConstants;
    }

    public static ConvertTool convert() {
        if (mConvert == null) {
            mConvert = ConvertTool.instance();
        }
        return mConvert;
    }

    public static FileTool file() {
        if (mFile == null) {
            mFile = FileTool.instance();
        }
        return mFile;
    }

    public static IntentTool intent() {
        if (mIntent == null) {
            mIntent = IntentTool.instance();
        }
        return mIntent;
    }

    public static ProcessTool process() {
        if (mProcess == null) {
            mProcess = ProcessTool.instance();
        }
        return mProcess;
    }

    public static ShellTool shell() {
        if (mShell == null) {
            mShell = ShellTool.instance();
        }
        return mShell;
    }

    public static TimeTool time() {
        if (mTime == null) {
            mTime = TimeTool.instance();
        }
        return mTime;
    }

    public static LunarTool lunar() {
        if (mLunar == null) {
            mLunar = LunarTool.instance();
        }
        return mLunar;
    }

    public static UnitTool unit() {
        if (mUnit == null) {
            mUnit = UnitTool.instance();
        }
        return mUnit;
    }

    public static ZipTool zip() {
        if (mZip == null) {
            mZip = ZipTool.instance();
        }
        return mZip;
    }

    public static BarTool bar() {
        if (mBar == null) {
            mBar = BarTool.instance();
        }
        return mBar;
    }

    public static PinYinTool pinYin() {
        if (mPinYin == null) {
            mPinYin = PinYinTool.instance();
        }
        return mPinYin;
    }

    public static FragmentTool fragment() {
        if (mFragment == null) {
            mFragment = FragmentTool.instance();
        }
        return mFragment;
    }

    public static CrashTool crash() {
        if (mCrash == null) {
            mCrash = CrashTool.instance();
        }
        return mCrash;
    }

    public static ThreadPoolTool threadPool() {
        if (mThreadPool == null) {
            mThreadPool = ThreadPoolTool.instance();
        }
        return mThreadPool;
    }

    public static XmlTool xml() {
        if (mXml == null) {
            mXml = XmlTool.instance();
        }
        return mXml;
    }

    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }
}
