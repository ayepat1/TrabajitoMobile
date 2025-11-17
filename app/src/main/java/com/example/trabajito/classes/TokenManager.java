package com.example.trabajito.classes;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import java.io.IOException;
import java.security.GeneralSecurityException;

public final class TokenManager {
    private static final String PREFS_NAME = "secure_prefs";
    private static final String KEY_TOKEN = "auth_token";
    private static SharedPreferences prefs;

    private TokenManager() {}

    public static synchronized void init(Context context) {
        if (prefs != null) return;
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            prefs = EncryptedSharedPreferences.create(
                    context,
                    PREFS_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Error inicializando TokenManager", e);
        }
    }

    public static void saveToken(String token) {
        if (prefs == null) throw new IllegalStateException("TokenManager no inicializado");
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public static String getToken() {
        if (prefs == null) throw new IllegalStateException("TokenManager no inicializado");
        return prefs.getString(KEY_TOKEN, null);
    }

    public static void clearToken() {
        if (prefs == null) throw new IllegalStateException("TokenManager no inicializado");
        prefs.edit().remove(KEY_TOKEN).apply();
    }
}
