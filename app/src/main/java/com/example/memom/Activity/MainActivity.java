package com.example.memom.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.memom.Dialog.AddEventFragment;
import com.example.memom.Fragment.CanvasFragment;
import com.example.memom.Fragment.MapFragment;
import com.example.memom.Fragment.MemoFragment;
import com.example.memom.Fragment.NoteFragment;
import com.example.memom.Model.User;
import com.example.memom.R;
import com.example.memom.Service.GoogleSignInService;
import com.example.memom.Service.UserService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Dictionary;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "My";
    public static User user;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseUser mUser;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ???????????????
        loadFragment(new MemoFragment());

        GoogleSignInService.signOut();
        checkLoginStatus();

        // ????????? Google ????????????
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("137360477785-vps4k1ddje1moqa0864sdgck8oq2guv6.apps.googleusercontent.com")
                .requestEmail()
                .build();
        Log.d(TAG, "GSO Initial Success");
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        init_navDrawer();
        init_toolbar();
        init_fabButotn();
    }

    private void init_fabButotn() {
        FloatingActionButton fab = findViewById(R.id.button_fab);
        Spinner spinner_eventType = findViewById(R.id.spinner_eventType);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new AddEventFragment(getSupportFragmentManager().beginTransaction());
                fragment.show(getSupportFragmentManager(), "AddEvent");

            }
        });
    }

//    public void datePicker(View v) {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);      //??????????????????????????????
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int day) {
//                String datetime = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
////                applydate.setText(datetime);   //?????????????????????????????????????????????
//            }
//        }, year, month, day).show();
//    }
//
//    public void timePicker(View v) {
//        Calendar calendar = Calendar.getInstance();
//        int hourOfDay = calendar.get(Calendar.HOUR);
//        int minute = calendar.get(Calendar.MINUTE);
//
//        new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                String datetime = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
////                applytime.setText(datetime);  //?????????????????????????????????????????????
//            }
//        }, hourOfDay, minute, true).show();
//
//    }

    private void init_toolbar() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        // ???toolbar??????APP???ActionBar
        setSupportActionBar(toolbar);

        // ???drawerLayout???toolbar?????????????????????????????????
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//                switch (id) {
//                    case R.id.action_search:
//                        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                        intent.putExtra(SearchManager.QUERY, "???????????????"); // query contains search string
//                        startActivity(intent);
//                        break;
//                    case R.id.action_shoppingcart:
//                        loadFragment(new ShoppingCartFragment());
//                        break;
//                }
//
//                return false;
//            }
//        });
    }

    private void init_navDrawer() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.View_navigation);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                int id = item.getItemId();
                Fragment fragment = null;
                switch (id) {
                    case R.id.memoFragment:
                        fragment = new MemoFragment();
                        break;
                    case R.id.noteFragment:
                        fragment = new NoteFragment();
                        break;
                    case R.id.mapFragment:
                        fragment = new MapFragment();
                        break;
                    case R.id.canvasFragment:
                        fragment = new CanvasFragment();
                        break;
                    case R.id.loginFragment:
                        signinWithGoogle(item);
//                        fragment = new LoginFragment();
                        return true;
                }
                loadFragment(fragment);
                return true;
            }
        });
    }

    private void signinWithGoogle(MenuItem item) {
        if (checkLoginStatus()) {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 200);
            item.setTitle("??????");
        } else {
            GoogleSignInService.signOut();
            Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            changeField();
            item.setTitle("??????");
            loadFragment(new MemoFragment());
        }

    }

    private boolean checkLoginStatus() {
        mUser = GoogleSignInService.getFirebaseUser();
//        changeField();
        return mUser == null;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("My", "On Google Sign In Activity Result");
        if (requestCode == 200) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // ???????????????????????????
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // ??????????????????????????????????????? Firebase Auth ??????
                GoogleSignInService.signInWithCredential(account.getIdToken());

                // ????????????????????????????????????????????????????????????
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // ?????? FirebaseUser
                        mUser = GoogleSignInService.getFirebaseUser();
                        Log.d(TAG, "Trying to get User");

                        if (mUser != null) {
                            TextView textView_username = (TextView) findViewById(R.id.textView_loginTitle);
                            textView_username.setText(mUser.getDisplayName());
                            Log.d(TAG, "Get User Success");
                            // ??????????????? uid
                            id = mUser.getUid();

                            // ???????????????????????????????????????
                            Dictionary dict_uid = new Hashtable();
                            dict_uid.put("uid", id);
                            Dictionary result = UserService.checkUserExist(dict_uid);
                            Boolean userExist = (Boolean) result.get("userExist");

                            // ?????????????????????????????????????????????
                            if (userExist) {
                                Dictionary data = UserService.getUser(dict_uid);
                                user = (User) data.get("user");
                                Log.d(TAG, "run: " + user.getName());
                            } else {
                                // ??????????????????
                                Dictionary data = new Hashtable();
                                data.put("uid", mUser.getUid());
                                data.put("name", mUser.getDisplayName());
                                data.put("email", mUser.getEmail());
                                data.put("memoindex", 1);
                                data.put("noteindex", 1);
                                UserService.addUser(data);
                            }
                            loadFragment(new MemoFragment());
                        }
                    }
                }, 2000);
//                Log.d(TAG, "Token: " + account.getIdToken());
                changeField();
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void changeField() {
        TextView textView_username = (TextView) findViewById(R.id.textView_loginTitle);
        if (mUser != null) {
            textView_username.setText(mUser.getDisplayName());
        } else {
            textView_username.setText("Hello! User.");
        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_frame, fragment).commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}