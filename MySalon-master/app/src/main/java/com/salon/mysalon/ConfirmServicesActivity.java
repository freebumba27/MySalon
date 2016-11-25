package com.salon.mysalon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.salon.mysalon.utils.Constants;
import com.salon.mysalon.utils.ReusableClass;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.fabric.sdk.android.Fabric;

public class ConfirmServicesActivity extends AppCompatActivity {

    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.TextVIewService)
    TextView TextVIewService;
    @Bind(R.id.TextVIewTime)
    TextView TextVIewTime;
    @Bind(R.id.auth_button)
    DigitsAuthButton digitsButton;
    @Bind(R.id.TextViewContactNumber)
    TextView TextVIewContactNumber;
    @Bind(R.id.layoutContactNo)
    LinearLayout layoutContactNo;
    @Bind(R.id.TextViewName)
    EditText TextViewName;
    @Bind(R.id.layoutName)
    LinearLayout layoutName;
    @Bind(R.id.buttonBook)
    Button buttonBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Digit authentication
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.TWITTER_KEY, Constants.TWITTER_SECRET);
        Digits.Builder digitsBuilder = new Digits.Builder().withTheme(R.style.CustomDigitsTheme);
        Fabric.with(this, new TwitterCore(authConfig), digitsBuilder.build());

        setContentView(R.layout.activity_confirm_services);
        ButterKnife.bind(this);
        digitsButton.setText("Confirm with mobile no");
        digitsButton.setTextSize(14);

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String totalTime = intent.getStringExtra("ServiceTotalTime");
        String serviceName = intent.getStringExtra("ServiceName");

        TextVIewService.setText(serviceName);
        TextVIewTime.setText("Approx time - " + ReusableClass.getHoursMin(Integer.parseInt(totalTime)) + " hrs");

        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                layoutContactNo.setVisibility(View.VISIBLE);
                layoutName.setVisibility(View.VISIBLE);
                digitsButton.setVisibility(View.GONE);
                buttonBook.setVisibility(View.VISIBLE);

                TextVIewContactNumber.setText(phoneNumber);
            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });
    }

    public void booking(View view) {
        String name = TextViewName.getText().toString();
        if (TextUtils.isEmpty(name))
            TextViewName.setError("Please let us know your good name.");
        else {
            final SweetAlertDialog sDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);

            sDialog.setTitleText(this.getResources().getString(R.string.title))
                    .setContentText(this.getResources().getString(R.string.sub_title))
                    .setConfirmText(this.getResources().getString(R.string.ok))
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent intent = new Intent(ConfirmServicesActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();

            sDialog.setCancelable(false);

        }
    }
}
