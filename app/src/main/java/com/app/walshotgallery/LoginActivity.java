package com.app.walshotgallery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private static final int RequestSignInCode = 7;
    private static final String TAG = "LoginActivity";
    AnimationDrawable animationDrawable;
    private ConstraintLayout constraintLayout;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SwipeButton facebookSignIn;
    private GoogleApiClient googleApiClient;
    private SwipeButton googleSignIn;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    /* renamed from: com.walshotbeta.walshotvbeta.LoginActivity$1 */
    class C17711 implements OnConnectionFailedListener {
        C17711() {
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.LoginActivity$2 */
    class C17722 implements OnActiveListener {
        C17722() {
        }

        public void onActive() {
            LoginActivity.this.UserSignInMethod();
        }
    }

    /* renamed from: com.walshotbeta.walshotvbeta.LoginActivity$3 */
    class C17733 implements OnActiveListener {
        C17733() {
        }

        public void onActive() {
            Toast.makeText(LoginActivity.this, "Not Available", 1).show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1420R.layout.activity_login);
        this.toolbar = (Toolbar) findViewById(C1420R.id.loginToolbarID);
        this.toolbar.setTitle("Login Required");
        setSupportActionBar(this.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        this.database = FirebaseDatabase.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.databaseReference = this.database.getReference().child("Google Users");
        this.googleSignIn = (SwipeButton) findViewById(C1420R.id.signInGooogleID);
        this.facebookSignIn = (SwipeButton) findViewById(C1420R.id.signInFacebookID);
        this.progressDialog = new ProgressDialog(this);
        this.constraintLayout = (ConstraintLayout) findViewById(C1420R.id.constraintLayoutID);
        this.animationDrawable = (AnimationDrawable) this.constraintLayout.getBackground();
        this.animationDrawable.setEnterFadeDuration(1000);
        this.animationDrawable.setExitFadeDuration(3000);
        this.animationDrawable.start();
        this.googleApiClient = new Builder(this).enableAutoManage(this, new C17711()).addApi(Auth.GOOGLE_SIGN_IN_API, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(C1420R.string.default_web_client_id)).requestEmail().requestId().requestProfile().build()).build();
        this.googleSignIn.setOnActiveListener(new C17722());
        this.facebookSignIn.setOnActiveListener(new C17733());
    }

    public void UserSignInMethod() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(this.googleApiClient), 7);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 7) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (googleSignInResult.isSuccess()) {
                FirebaseUserAuth(googleSignInResult.getSignInAccount());
            }
        }
    }

    public void FirebaseUserAuth(GoogleSignInAccount account) {
        final String emailString = account.getEmail().toString().trim();
        final String firstNameString = account.getGivenName().toString().trim();
        final String lastNameString = account.getFamilyName().toString().trim();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Signing in with Google:");
        stringBuilder.append(account.getId());
        Log.d(str, stringBuilder.toString());
        this.progressDialog.setMessage("Authenticating...");
        this.progressDialog.show();
        this.mAuth.signInWithCredential(credential).addOnCompleteListener((Activity) this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> AuthResultTask) {
                if (AuthResultTask.isSuccessful()) {
                    FirebaseUser user = LoginActivity.this.mAuth.getCurrentUser();
                    DatabaseReference currentUser = LoginActivity.this.databaseReference.child(LoginActivity.this.mAuth.getCurrentUser().getUid());
                    currentUser.child("First Name").setValue(firstNameString);
                    currentUser.child("Last Name").setValue(lastNameString);
                    currentUser.child("Email").setValue(emailString);
                    LoginActivity.this.progressDialog.dismiss();
                    MainActivity.mNavigationView.setCheckedItem(C1420R.id.nav_galleryID);
                    LoginActivity.this.startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    return;
                }
                Toast.makeText(LoginActivity.this, "Something Went Wrong", 1).show();
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
