package Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.ebanx.swipebtn.OnActiveListener;
import com.ebanx.swipebtn.SwipeButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.Builder;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.walshotbeta.walshotvbeta.C1420R;
import com.walshotbeta.walshotvbeta.MainActivity;

public class LoginFragment extends Fragment {
    private static final int RequestSignInCode = 7;
    private static final String TAG = AccountFragment.class.getSimpleName();
    AnimationDrawable animationDrawable;
    private ConstraintLayout constraintLayout;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private SwipeButton facebookSignIn;
    private SwipeButton googleSignIn;
    ImageButton hamButton;
    FirebaseAuth mAuth;
    private Context mContext;
    ImageButton optionsButton;
    private Dialog progressDialog;

    /* renamed from: Fragments.LoginFragment$3 */
    class C00533 implements OnClickListener {
        C00533() {
        }

        public void onClick(View v) {
            MainActivity.mDrawerLayout.openDrawer(MainActivity.mNavigationView);
        }
    }

    /* renamed from: Fragments.LoginFragment$1 */
    class C07951 implements OnActiveListener {
        C07951() {
        }

        public void onActive() {
            LoginFragment.this.UserSignInMethod();
        }
    }

    /* renamed from: Fragments.LoginFragment$2 */
    class C07962 implements OnActiveListener {
        C07962() {
        }

        public void onActive() {
            Toast.makeText(LoginFragment.this.mContext, "Not Available", 1).show();
        }
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(C1420R.layout.login_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getActivity();
        this.mAuth = FirebaseAuth.getInstance();
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = this.database.getReference().child("Google Users");
        this.googleSignIn = (SwipeButton) view.findViewById(C1420R.id.signInGooogleID);
        this.facebookSignIn = (SwipeButton) view.findViewById(C1420R.id.signInFacebookID);
        this.progressDialog = new Dialog(this.mContext);
        this.constraintLayout = (ConstraintLayout) view.findViewById(C1420R.id.constraintLayoutID);
        this.hamButton = (ImageButton) view.findViewById(C1420R.id.hamButtonID);
        this.optionsButton = (ImageButton) view.findViewById(C1420R.id.optionsButtonID);
        this.animationDrawable = (AnimationDrawable) this.constraintLayout.getBackground();
        this.animationDrawable.setEnterFadeDuration(1000);
        this.animationDrawable.setExitFadeDuration(3000);
        this.animationDrawable.start();
        MainActivity.searchViewToolbar.setVisibility(8);
        MainActivity.searchViewToolbar.animate().alpha(0.0f).start();
        MainActivity.toolbarMenu.setVisibility(8);
        MainActivity.toolbarMenu.animate().alpha(0.0f).start();
        MainActivity.fake.setVisibility(8);
        GoogleSignInOptions googleSignInOptions = new Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(C1420R.string.default_web_client_id)).requestEmail().requestId().requestProfile().build();
        this.googleSignIn.setOnActiveListener(new C07951());
        this.facebookSignIn.setOnActiveListener(new C07962());
        this.hamButton.setOnClickListener(new C00533());
    }

    public void UserSignInMethod() {
        startActivityForResult(Auth.GoogleSignInApi.getSignInIntent(MainActivity.googleApiClient), 7);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        this.progressDialog.setContentView(C1420R.layout.progress_dialog);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Signing in with Google:");
        stringBuilder.append(account.getId());
        Log.d(str, stringBuilder.toString());
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
        this.mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> AuthResultTask) {
                if (AuthResultTask.isSuccessful()) {
                    FirebaseUser user = LoginFragment.this.mAuth.getCurrentUser();
                    DatabaseReference currentUser = LoginFragment.this.databaseReference.child(LoginFragment.this.mAuth.getCurrentUser().getUid());
                    currentUser.child("First Name").setValue(firstNameString);
                    currentUser.child("Last Name").setValue(lastNameString);
                    currentUser.child("Email").setValue(emailString);
                    LoginFragment.this.progressDialog.dismiss();
                    MainActivity.mainLayout.setVisibility(8);
                    MainActivity.frameLayout.setVisibility(0);
                    LoginFragment.this.getFragmentManager().beginTransaction().setCustomAnimations(C1420R.anim.enter_from_right, C1420R.anim.exit_to_left).replace(C1420R.id.frameLayoutID, new AccountFragment()).commit();
                    Glide.with(LoginFragment.this.mContext).load(LoginFragment.this.mAuth.getCurrentUser().getPhotoUrl()).into(MainActivity.profilePic);
                    MainActivity.profileName.setText(LoginFragment.this.mAuth.getCurrentUser().getDisplayName());
                    MainActivity.profileEmail.setText(LoginFragment.this.mAuth.getCurrentUser().getEmail());
                    return;
                }
                Toast.makeText(LoginFragment.this.getActivity(), "Something Went Wrong", 1).show();
                LoginFragment.this.progressDialog.dismiss();
            }
        });
    }
}
