package com.app.walshotgallery;

import android.app.Application;
import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;
import com.adobe.creativesdk.foundation.auth.IAdobeAuthClientCredentials;

public class CreativeApplication extends Application implements IAdobeAuthClientCredentials {
    private static final String CREATIVE_SDK_CLIENT_ID = "c9aac205a09e4771bc53edf914c6c63f";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "0e8d8f98-fe47-45ef-b0e5-f31126b6840c";
    private static final String CREATIVE_SDK_REDIRECT_URI = "ams+85b994e51c24181be01f9231eb28062a6663768e://adobeid/c9aac205a09e4771bc53edf914c6c63f";
    private static final String[] CREATIVE_SDK_SCOPES = new String[]{"johnwickrockx@gmail.com", "John", "india"};

    public void onCreate() {
        super.onCreate();
        AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());
    }

    public String getClientID() {
        return CREATIVE_SDK_CLIENT_ID;
    }

    public String getClientSecret() {
        return CREATIVE_SDK_CLIENT_SECRET;
    }

    public String[] getAdditionalScopesList() {
        return CREATIVE_SDK_SCOPES;
    }

    public String getRedirectURI() {
        return CREATIVE_SDK_REDIRECT_URI;
    }
}
