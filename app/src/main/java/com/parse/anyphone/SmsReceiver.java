package com.parse.anyphone;

/**
 * Created by Siddharth on 3/13/16.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Ravi on 09/07/15.
 */
public class SmsReceiver extends BroadcastReceiver {
    public static final String OTP_DELIMITER = " is";
    public static final String SMS_ORIGIN = "+1585-300-4260";
    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,
                "You must enter the 4 digit code texted to your phone number.",
                Toast.LENGTH_LONG).show();

        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

                    // if the SMS is not from our gateway, ignore the message
                   /* if (!senderAddress.toLowerCase().contains(SMS_ORIGIN.toLowerCase())) {
                        return;
                    }*/

                    // verification code from sms
                    String verificationCode = getVerificationCode(message);

                    Log.e(TAG, "OTP received: " + verificationCode);

                  Intent otpIntent = new Intent(context, LoginActivity.class);
                   otpIntent.putExtra("otp", verificationCode);
                    context.startActivity(otpIntent);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Getting the OTP from sms message body
     * ':' is the separator of OTP from the message
     *
     * @param message
     * @return
     */
    private String getVerificationCode(String message) {
        String code = null;
        int index = message.indexOf(OTP_DELIMITER);

        if (index != -1) {
            int start = index + 2;
            int length = 4;
            code = message.substring(start, start + length);

            return code;
        }

        return code;
    }
}