package com.pengshi.wokx.util;

import android.content.res.Resources;

/**
 * Created by karlp on 7/17/2017.
 */

public class LanguageUtil {
    public static String getSetLocale(Resources res){
        return res.getConfiguration().locale.getLanguage();
    }
}
