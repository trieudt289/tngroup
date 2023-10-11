package com.example.myapplication.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.fragment.AcountFragment;
import com.example.myapplication.fragment.AddUserFragment;
import com.example.myapplication.fragment.ChangePassFragment;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.fragment.ListStaffFragment;
import com.example.myapplication.fragment.NotificationFragment;
import com.example.myapplication.fragment.SmsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private NavigationView drawer_navigation;
    public static final int HOME_FRAGMENT = 0;
    public static final int NOTIFICATION_FRAGMENT = 1;
    public static final int ADDSTAFF_FRAGMENT = 2;
    public static final int LISTSTAFF_FRAGMENT = 3;
    public static final int SMS_FRAGMENT = 4;
    public static final int ACCOUNT_FRAGMENT = 5;
    public static final int CHANGEPASS_FRAGMENT = 6;
    public static final int SIGNOUT_FRAGMENT = 7;


    private static int mCurrentFragment = HOME_FRAGMENT;

    private TextView txtEmail;
    private TextView txtName;
    private ImageView imgAvata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Khai báo
        addControls();
        setSupportActionBar(toolbar);
        // Tạo nút bấm drawer
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        // Tạo sự kiện
        addEvents();
        replaceFragment(new HomeFragment());
        drawer_navigation.getMenu().findItem(R.id.dr_Home).setChecked(true);
        drawer_navigation.setNavigationItemSelectedListener(this);
        InforUser();
    }

    private void addEvents() {
        // Khởi tạo fragment tương ứng cho bottom navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.bot_Home) {
                openHomeFragment();
                bottomNavigationView.getMenu().findItem(R.id.bot_Home).setChecked(true);

            } else if (id == R.id.bot_Notification) {
                openNotification();
                bottomNavigationView.getMenu().findItem(R.id.bot_Notification).setChecked(true);

            } else if (id == R.id.bot_addUser) {
                openAddStaff();
                bottomNavigationView.getMenu().findItem(R.id.bot_addUser).setChecked(true);


            } else if (id == R.id.bot_liststaff) {
                bottomNavigationView.getMenu().findItem(R.id.bot_liststaff).setChecked(true);


                openListStaff();
            }
            return true;
        });
    }

    // ... Các phương thức khác giữ nguyên

    //sự kiện khi click vào icon Home
    private void openHomeFragment(){
        if(HOME_FRAGMENT!=mCurrentFragment){
            replaceFragment(new HomeFragment());
            mCurrentFragment=HOME_FRAGMENT;
        }
    }
    //sự kiện khi click vào icon Notification
    private void openNotification(){
        if (HOME_FRAGMENT!=mCurrentFragment){
            replaceFragment(new NotificationFragment());
            mCurrentFragment=NOTIFICATION_FRAGMENT;
        }
    }
    //sự kiện khi click vào icon Add Staff
    private void openAddStaff(){
        if(ADDSTAFF_FRAGMENT!=mCurrentFragment){
            replaceFragment(new AddUserFragment());
            mCurrentFragment=ADDSTAFF_FRAGMENT;
        }
    }
    //sự kiện khi click vào icon list staff
    private void openListStaff(){
        if(LISTSTAFF_FRAGMENT!=mCurrentFragment){
            replaceFragment(new ListStaffFragment());
            mCurrentFragment=LISTSTAFF_FRAGMENT;
        }
    }
    //sự kiện khi click vào icon sms
    private void openSMS(){
        if(SMS_FRAGMENT!=mCurrentFragment){
            replaceFragment(new SmsFragment());
            mCurrentFragment=SMS_FRAGMENT;
        }
    }
    //sự kiện khi click vào icon Account
    private void openAccount(){
        if(ACCOUNT_FRAGMENT!=mCurrentFragment){
            replaceFragment(new AcountFragment());
            mCurrentFragment=ACCOUNT_FRAGMENT   ;
        }
    }
    //sự kiện khi click vào icon list staff
    private void openChangpass(){
        if(CHANGEPASS_FRAGMENT!=mCurrentFragment){
            replaceFragment(new ChangePassFragment());
            mCurrentFragment=CHANGEPASS_FRAGMENT;
        }
    }   
    
    //khởi tạo L
    private void addControls() {
        drawer_navigation=findViewById(R.id.drawer_navigation);
        drawerLayout=findViewById(R.id.drawer);
        toolbar=findViewById(R.id.too_bar);
        bottomNavigationView=findViewById(R.id.bottom_nav);
        txtEmail=drawer_navigation.getHeaderView(0).findViewById(R.id.txtGmail);
        txtName=drawer_navigation.getHeaderView(0).findViewById(R.id.txtTen);
        imgAvata=drawer_navigation.getHeaderView(0).findViewById(R.id.img_avt);
    }
    // khởi tạo fragment
    private void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frContent,fragment);
        fragmentTransaction.commit();

    }


    //sự kiện click drawer navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.dr_Home){
            openHomeFragment();
            bottomNavigationView.getMenu().findItem(R.id.bot_Home).setChecked(true);

        } else if (id==R.id.dr_Sms) {
            openSMS();
            drawer_navigation.getMenu().findItem(R.id.dr_Sms).setChecked(true);

        }
        else if (id==R.id.dr_Account) {
            openAccount();
            drawer_navigation.getMenu().findItem(R.id.dr_Account).setChecked(true);

        }
        else if (id==R.id.dr_Changepass) {
            openChangpass();
            drawer_navigation.getMenu().findItem(R.id.dr_Changepass).setChecked(true);

        } else if (id==R.id.dr_Signout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void InforUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            if(name==null){
                txtName.setVisibility(View.GONE);
            }else {
                txtName.setVisibility(View.VISIBLE);
                txtName.setText(name);
            }

            txtEmail.setText(email);

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }

}
