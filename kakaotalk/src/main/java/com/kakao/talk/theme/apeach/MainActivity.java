package com.kakao.talk.theme.apeach;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
    private static final String KAKAOTALK_SETTINGS_THEME_URI = "kakaotalk://settings/theme/";
    private static final String MARKET_URI = "market://details?id=";
    private static final String KAKAO_TALK_PACKAGE_NAME = "com.kakao.talk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(getResources().getColor(R.color.statusBarColor));
            } catch (Throwable ignored) {
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } catch (Throwable ignored) {
            }
        }

        final Button applyButton = (Button) findViewById(R.id.apply);
        applyButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(KAKAOTALK_SETTINGS_THEME_URI + getPackageName()));
                startActivity(intent);
                finish();
            }
        });

        final Button installButton = (Button) findViewById(R.id.market);
        installButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URI + KAKAO_TALK_PACKAGE_NAME));
                startActivity(intent);
                finish();
            }
        });

        try {
            getPackageManager().getPackageInfo(KAKAO_TALK_PACKAGE_NAME, 0);
            applyButton.setVisibility(View.VISIBLE);
            installButton.setVisibility(View.GONE);
        } catch (NameNotFoundException e) {
            applyButton.setVisibility(View.GONE);
            installButton.setVisibility(View.VISIBLE);
        }
    }
}
