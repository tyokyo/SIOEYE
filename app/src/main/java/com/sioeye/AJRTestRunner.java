package com.sioeye;

/**
 * Created by qiang.zhang on 2017/1/9.
 */
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.test.runner.AndroidJUnitRunner;

public class AJRTestRunner extends AndroidJUnitRunner
{
    @Override
    public void onCreate(Bundle arguments)
    {
        MultiDex.install(getTargetContext());
        super.onCreate(arguments);
    }
}