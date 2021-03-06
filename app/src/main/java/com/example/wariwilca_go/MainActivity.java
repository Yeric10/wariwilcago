package com.example.wariwilca_go;


import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wariwilca_go.databinding.ActivityMainBinding;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private AdView mAdView;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appBarMain.fab.setVisibility(View.GONE);
        setSupportActionBar(binding.appBarMain.toolbar);


        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //MENU
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.reg_defensores,R.id.nav_recorrido, R.id.res_visita, R.id.busqueda_QR)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }




    private void initGoogleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("376476661021-sjvqc5gm62i0dku2r7o8e2485onhuq4m.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //TUTORIAL DE PRIMERA VEZ
        new Handler().post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                final View view = findViewById(R.id.action_login);
                final View view2 = findViewById(R.id.pruebaBTN);
                final Drawable menu = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_menu_icon2);
                Typeface prueba = Typeface.createFromAsset(getAssets(), "fonts/quicksand_medium.ttf");

                NavigationView navigationView = binding.navView;
                new TapTargetSequence(MainActivity.this)
                        .targets(
                                TapTarget.forView(view, "??BIENVENIDO!", "Es necesario hacer clic aqu?? para registrarte por GMAIL")
                                        // All options below are optional
                                        .outerCircleColor(R.color.Brow100)      // Specify a color for the outer circle
                                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                        .targetCircleColor(R.color.Brow300)   // Specify a color for the target circle
                                        .titleTextSize(30)                  // Specify the size (in sp) of the title text
                                        .titleTextColor(R.color.Brow500)      // Specify the color of the title text
                                        .textColor(R.color.Brow500)            // Specify a color for both the title and description text
                                        .textTypeface(prueba)  // Specify a typeface for the text
                                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                                        .tintTarget(true)                   // Whether to tint the target view's color
                                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                                        .targetRadius(60),
                                TapTarget.forView(view2, "ADEM??S", "Puedes hacer clic aqu?? para ver que puedes hacer en la aplicaci??n")
                                        // All options below are optional
                                        .outerCircleColor(R.color.Brow100)      // Specify a color for the outer circle
                                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                                        .targetCircleColor(R.color.Brow300)   // Specify a color for the target circle
                                        .titleTextSize(30)                  // Specify the size (in sp) of the title text
                                        .titleTextColor(R.color.Brow500)      // Specify the color of the title text
                                        .textColor(R.color.Brow500)            // Specify a color for both the title and description text
                                        .textTypeface(prueba)  // Specify a typeface for the text
                                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                                        .tintTarget(true)                   // Whether to tint the target view's color
                                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                                        .targetRadius(60)
                                        .icon(menu)
                                )
                        .listener(new TapTargetSequence.Listener() {
                            // This listener will tell us when interesting(tm) events happen in regards
                            // to the sequence
                            @Override
                            public void onSequenceFinish() {
                                // Yay
                            }

                            @Override
                            public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                                // Perform action for the current target
                            }

                            @Override
                            public void onSequenceCanceled(TapTarget lastTarget) {
                                // Boo
                            }
                        }).start();
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_login:
                initGoogleSignIn();
                ShowDialogFragment();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void ShowDialogFragment(){
        new MaterialAlertDialogBuilder(this)
                .setTitle("??Te damos la bienvenida!")
                .setMessage("Accede a tu cuenta para guardar tus puntuaciones")
                .setNegativeButton("Cancel",
                        (dialog, which) -> {
                            dialog.dismiss();
                        })
                .setPositiveButton("Iniciar Sesi??n",
                        (dialog, which) -> {
                            dialog.dismiss();
                            signIn();
                        })
                .show();
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            gamesGetUserInfo(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
    // [END auth_with_google]

    public static class Global {
        public static String playerName;
        public static Uri playerImg;
        public static String playerEmail;
    }

    private void gamesGetUserInfo(FirebaseUser user) {

        //NOMBRE
        Global.playerName = user.getDisplayName();
        TextView name = findViewById(R.id.userName);
        name.setVisibility(View.VISIBLE);
        name.setText(Global.playerName );

        //IMAGEN
        Global.playerImg = user.getPhotoUrl();
        ImageView imageView = findViewById(R.id.userFoto);
        Glide.with(this).load(Global.playerImg ).into(imageView);
        imageView.setVisibility(View.VISIBLE);

        //EMAIL
        Global.playerEmail = user.getEmail();
        TextView email = findViewById(R.id.userEmail);
        email.setText(Global.playerEmail);
        email.setVisibility(View.VISIBLE);
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}