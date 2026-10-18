package com.example.mycashier.Common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.mycashier.R;

public class NetworkChangeListener extends BroadcastReceiver {

    public enum PopupType {
        ALERT_DIALOG,
        BOTTOM_BANNER
    }

    private final Context context;
    private final ViewGroup rootView; // Needed for bottom banner
    private final PopupType popupType;
    @LayoutRes
    private final int popupLayout; // Layout for bottom banner or custom AlertDialog

    private View bannerView; // Only for bottom banner
    private AlertDialog alertDialog; // Only for AlertDialog

    public NetworkChangeListener(Context context, ViewGroup rootView, PopupType popupType, @LayoutRes int popupLayout) {
        this.context = context;
        this.rootView = rootView;
        this.popupType = popupType;
        this.popupLayout = popupLayout;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!NetworkDetails.isConnectedtoInternet(context)) {

            if (popupType == PopupType.ALERT_DIALOG) {
                showAlertDialog();
            } else if (popupType == PopupType.BOTTOM_BANNER) {
                showBanner();
            }

        } else {
            Toast.makeText(context, "Connected to Internet", Toast.LENGTH_SHORT).show();

            if (popupType == PopupType.ALERT_DIALOG) hideAlertDialog();
            else hideBanner();
        }
    }

    /** AlertDialog methods **/
    private void showAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(popupLayout, null);
        builder.setView(view);

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        AppCompatButton btnRetry = view.findViewById(R.id.btnInternetRetry);
        if (btnRetry != null) {
            btnRetry.setOnClickListener(v -> {
                alertDialog.dismiss();
                onReceive(context, null);
            });
        }
    }

    private void hideAlertDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    /** Bottom banner methods **/
    private void showBanner() {
        if (bannerView != null && bannerView.getParent() != null) return;

        bannerView = LayoutInflater.from(context).inflate(popupLayout, rootView, false);

//        AppCompatButton btnRetry = bannerView.findViewById(R.id.btnInternetRetry);
//        if (btnRetry != null) {
//            btnRetry.setOnClickListener(v -> {
//                hideBanner();
//                onReceive(context, null);
//            });
//        }

        // Set layout params to position at bottom
        CoordinatorLayout.LayoutParams params =
                new CoordinatorLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        params.gravity = android.view.Gravity.BOTTOM;
        bannerView.setLayoutParams(params);

        rootView.addView(bannerView);

        bannerView.setTranslationY(bannerView.getHeight());
        bannerView.animate().translationY(0).setDuration(300).start();
    }

    private void hideBanner() {
        if (bannerView != null && bannerView.getParent() != null) {
            bannerView.animate().translationY(bannerView.getHeight()).setDuration(300).withEndAction(() -> {
                rootView.removeView(bannerView);
                bannerView = null;
            }).start();
        }
    }
}
