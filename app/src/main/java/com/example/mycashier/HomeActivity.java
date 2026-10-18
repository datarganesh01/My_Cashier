package com.example.mycashier;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mycashier.Common.BaseActivity;
import com.example.mycashier.Fragments.AnalyticsFragment;
import com.example.mycashier.Fragments.ChatsFragment;
import com.example.mycashier.Fragments.EntertainmentFragment;
import com.example.mycashier.Fragments.HistoryFragment;
import com.example.mycashier.Fragments.HomeFragment;
import com.example.mycashier.Fragments.SearchFragment;
import com.example.mycashier.Fragments.TransactionsFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

public class HomeActivity extends BaseActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {


    public boolean doubletap = false;
    SharedPreferences preference;
    SharedPreferences.Editor editor;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        preference = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor =  preference.edit();

        bottomNavigationView = findViewById(R.id.homebottomnavview);
        bottomNavigationView.setOnNavigationItemSelectedListener(HomeActivity.this);
        bottomNavigationView.setSelectedItemId(R.id.homehome);


        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // 👈 MUST match web client ID from google-services.json
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);




        boolean isfirsttime = preference.getBoolean("isfirsttime",true);

        if (isfirsttime)
        {
            welcome();
        }
    }


    public void welcome() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("Welcome to My Cashier");
        ad.setMessage("Your Personnel Expense Tracker💵 \n \n 🖲️Keep records of your transactions and expenses");
        ad.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                editor.putBoolean("isfirsttime",false).commit();
            }
        }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menulocation)
        {
            Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menuMyProfile)
        {
            //Toast.makeText(HomeActivity.this, "Your Profile ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(HomeActivity.this,MyProfileActivity.class);
            startActivity(intent);



        } else if (item.getItemId()==R.id.menuNotification)
        {
            Toast.makeText(HomeActivity.this, "Notification Enabled", Toast.LENGTH_SHORT).show();


        } else if (item.getItemId()==R.id.menuSettings)
        {
            Toast.makeText(HomeActivity.this, "Settings ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this,SettingsActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.menuQRCode) {

            Intent intent = new Intent(HomeActivity.this,MyQRCodeActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.menuTaptoScan) {

            Intent intent = new Intent(HomeActivity.this,MyQRCodeScannerActivity.class);
            startActivity(intent);

        } else if (item.getItemId()==R.id.menuContactUs)
        {
            Toast.makeText(HomeActivity.this, "Contact Us", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this,ContactUsActivity.class);
            startActivity(intent);

        } else if (item.getItemId()==R.id.menuTextDetection)
        {
            Toast.makeText(HomeActivity.this, "Text Detection", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(HomeActivity.this,TextDetectionActivity.class);
            startActivity(intent);
            

        } else if (item.getItemId()==R.id.menuAboutUs)
        {
            Toast.makeText(HomeActivity.this, "Want to know About Us..?", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(HomeActivity.this,AboutUsActivity.class);
            startActivity(intent);

//            AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
//            ad.setTitle("About My Cashier");
//            ad.setMessage("Version : 1.0.0 \n \nThis app is build for to help you managing your Expenses");
//            ad.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            }).create().show();
//

        }
        else if (item.getItemId()==R.id.menuLogout)
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(HomeActivity.this);
            adb.setTitle("Logout");
            adb.setMessage("Do you really want to Logout?");

            adb.setPositiveButton("Cancle", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });

            adb.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent lout = new Intent(HomeActivity.this,LoginActivity.class);
                    googleSignInClient.signOut();
                    editor.putBoolean("islogin",false).commit();
                    startActivity(lout);
                    finish();
                }
            }).create().show();

            return true;    //laterly added


        }

        return true;
    }

    //@SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {



        if (doubletap){
            finishAffinity();
        }
        else{

            Toast.makeText(HomeActivity.this, "Press again to Exit!", Toast.LENGTH_SHORT).show();

            doubletap=true;
            Handler h1 = new Handler();
            h1.postDelayed(new Runnable() {
                @Override
                public void run() {

                    doubletap=false;

                }
            },2000);

        }

//        super.onBackPressed();

    }

    HomeFragment homeFragment = new HomeFragment();
    TransactionsFragment transactionsFragment = new TransactionsFragment();
    SearchFragment searchFragment = new SearchFragment();
    HistoryFragment historyFragment = new HistoryFragment();
    ChatsFragment chatsFragment = new ChatsFragment();
    EntertainmentFragment entertainmentFragment = new EntertainmentFragment();
    AnalyticsFragment analyticsFragment = new AnalyticsFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {




        if (item.getItemId() == R.id.homehome)
        {
            getSupportActionBar().show();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,homeFragment).commit();
        } else if (item.getItemId() == R.id.hometransaction)
        {
            getSupportActionBar().show();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,transactionsFragment).commit();
        }
        else if (item.getItemId() == R.id.homesearchmenu)
        {
            getSupportActionBar().show();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,searchFragment).commit();
        }
        else if (item.getItemId() == R.id.homereport)
        {
            getSupportActionBar().show();
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,analyticsFragment).commit();
        }
        else if (item.getItemId() == R.id.homeentertainment)
        {


            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,entertainmentFragment).commit();
            getSupportActionBar().hide();
            //bottomNavigationView.setVisibility(BottomNavigationView.GONE);
        }
        return true;
    }

    public void switchToFragment(int index) {
        BottomNavigationView nav = findViewById(R.id.homebottomnavview); // Adjust ID if needed
        nav.setSelectedItemId(nav.getMenu().getItem(index).getItemId());
    }



}