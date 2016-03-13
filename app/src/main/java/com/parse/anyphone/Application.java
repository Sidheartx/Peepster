package com.parse.anyphone;

import com.parse.Parse;

/**
 * Created by Madhav Chhura on 7/1/15.
 */
public class Application extends android.app.Application {
    public static final String INTENT_OTP = "otp";
    @Override
    public void onCreate(){
        super.onCreate();

        //Add below your Parse project application and client keys.
        Parse.initialize(this, "uLnlKp6WTfWClRaCkPiOapBKAyEQfTysrgAJKl5t", "ZmvgpQtQuNDKZ39DrZNMABqc0jHpj09qBNzmoqGs");
    }
}