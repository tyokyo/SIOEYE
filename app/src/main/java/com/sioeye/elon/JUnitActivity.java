package com.sioeye.elon;

/**
 * Created by qiang.zhang on 2017/1/9.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sioeye.R;

public class JUnitActivity extends Activity {
    static final String LOG_TAG = "junit";
    Thread testRunnerThread = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.runner);
        Button launcherButton = (Button)findViewById( R.id.ok);
        launcherButton.setOnClickListener( new View.OnClickListener() {
            public void onClick( View view ) {
                startTest();
            }
        } );
    }

    private synchronized void startTest() {
        if( ( testRunnerThread != null ) &&
                !testRunnerThread.isAlive() )
            testRunnerThread = null;
        if( testRunnerThread == null ) {
            testRunnerThread = new Thread( new TestRunner( this ) );
            testRunnerThread.start();
        } else
            Toast.makeText(
                    this,
                    "Test is still running",
                    Toast.LENGTH_SHORT).show();
    }

}