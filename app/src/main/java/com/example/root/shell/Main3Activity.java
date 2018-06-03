package com.example.root.shell;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main3Activity extends Activity {
    EditText input;
    Button btn;
    TextView out;
    String command;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        PackageManager p = getPackageManager();
        ComponentName componentName = new ComponentName(this, com.example.root.shell.MainActivity.class);
        // activity which is first time open in manifiest file which is declare as
// <category android:name="android.intent.category.LAUNCHER" />
        //p.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,  PackageManager.DONT_KILL_APP);
        input = (EditText)findViewById(R.id.txt);
        btn = (Button)findViewById(R.id.btn);
        out = (TextView)findViewById(R.id.out);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ShellExecuter exe = new ShellExecuter();
                command = input.getText().toString();

                String outp = exe.Executer(command);
                out.setText(outp);
                Log.d("Output", outp);
            }
        });


    }
}
