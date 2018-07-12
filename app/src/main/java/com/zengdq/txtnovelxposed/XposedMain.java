package com.zengdq.txtnovelxposed;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedMain implements IXposedHookLoadPackage {
    private String zhilian="com.tataera.tbook.online.TxtBookBrowserActivity";
    private String mainActivityAd="com.bujiadian.txtnovel.MainActivity";
    private String tataAdAdapter="com.tataera.sdk.nativeads.TataAdAdapter";
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                final ClassLoader cl= ((Context) param.args[0]).getClassLoader();
                Class<?> zhilianhookclass = null;
                try {
                    zhilianhookclass=cl.loadClass(zhilian);
                    if (zhilianhookclass!=null){
                        XposedHelpers.findAndHookMethod(zhilianhookclass, "showAd",float.class, new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                return null;
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                Class<?> mainActivityAdclass = null;
                try {
                    mainActivityAdclass=cl.loadClass(mainActivityAd);
                    if (mainActivityAdclass!=null){
                        XposedHelpers.findAndHookMethod(mainActivityAdclass, "a",Activity.class, ViewGroup.class,View.class,String.class,String.class,"com.qq.e.ads.splash.SplashADListener",int.class, new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                Class<?> finalMainActivityAdclass = null;
                                finalMainActivityAdclass=cl.loadClass(mainActivityAd);
                                if (finalMainActivityAdclass!=null){
                                    Method myteststr =  XposedHelpers.findMethodBestMatch(finalMainActivityAdclass, "onADDismissed");
                                    myteststr.invoke(finalMainActivityAdclass);
                                }
                                return null;
                            }
                        });

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                Class<?> tataAdAdapterclass = null;
                try {
                    tataAdAdapterclass=cl.loadClass(tataAdAdapter);
                    if (tataAdAdapterclass!=null){
                        XposedHelpers.findAndHookMethod(tataAdAdapterclass, "loadAds",String.class, new XC_MethodReplacement() {
                            @Override
                            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                                return null;
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
