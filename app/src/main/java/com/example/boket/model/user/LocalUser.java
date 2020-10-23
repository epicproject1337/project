package com.example.boket.model.user;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

//TODO: move all user "logic" and firebase auth calls to a User Model.
public class LocalUser implements User {

    private String uid;
    private String email;
    private String name;
    private String location;

    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = LocalUser.class.getName();
    private static final String collection = "users";
    private static LocalUser currentUser = null;

    private LocalUser(String uid, String email, String name, String location) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    private void setLocation(String loc) {
        this.location = loc;
    }

    private void setName(String name) {
        this.name = name;
    }


    public static void signup(String name, String email, String emailConfirm, String password, String passwordConfirm, String location, SignupCallback callback) {

        //confirm email
        if (!email.equals(emailConfirm)) {
            callback.onSignupFailed("Email does not match");
            return;
        }
        //confirm password
        if (!password.equals(passwordConfirm)) {
            callback.onSignupFailed("Password does not match");
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            //Load user locally
                            LocalUser u = new LocalUser(mAuth.getUid(), email, name, location);
                            currentUser = u;
                            //Call to update name and other info in Firebase
                            updateName(name);
                            updateDb();
                            //Return current user
                            callback.onSignupComplete(LocalUser.currentUser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            callback.onSignupFailed("Signup failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    public static void login(String email, String password, LoginCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            DocumentReference docRef = db.collection(collection).document(user.getUid());
                            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    FirebaseUserModel um = documentSnapshot.toObject(FirebaseUserModel.class);
                                    LocalUser localUser = new LocalUser(mAuth.getUid(), mAuth.getCurrentUser().getEmail(), mAuth.getCurrentUser().getDisplayName(), um.getLocation());
                                    setCurrentUser(localUser);
                                    callback.onLoginComplete(localUser);
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            callback.onLoginFailed("Login failed: " + task.getException().getMessage());
                        }
                    }
                });
    }

    private static void updateDb() {
        if (currentUser == null)
            return;
        //Create a new FirebaseUserModel for easier database insert/update.
        FirebaseUserModel um = new FirebaseUserModel(currentUser.uid, currentUser.email, currentUser.name, currentUser.location);
        db.collection(collection).document(currentUser.getUid()).set(um).addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    public static void updateName(String name) {
        //Make sure there is a user logged in
        if (currentUser == null)
            return;

        //Update name locally
        currentUser.setName(name);

        //Update name in Firebase Auth
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        FirebaseUser user = mAuth.getCurrentUser();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User profile updated.");

                        }
                    }
                });
        //Update Firebase Firestore.
        updateDb();

    }

    public static void updateLocation(String location) {
        //Make sure there is a user logged in
        if (currentUser == null)
            return;

        //Update location locally
        currentUser.setLocation(location);

        //Update Firebase Firestore.
        updateDb();

    }

    public static void signout() {
        mAuth.signOut();
        currentUser = null;
    }

    public static LocalUser getCurrentUser() {
        if (LocalUser.currentUser == null) {
            FirebaseUser u = mAuth.getCurrentUser();
            if (u != null) {
                //TODO, need to load location
                LocalUser localUser = new LocalUser(u.getUid(), u.getEmail(), u.getDisplayName(), null);
                LocalUser.setCurrentUser(localUser);
                return localUser;
            } else {
                return null;
            }
        } else {
            return LocalUser.currentUser;
        }
    }

    private static void setCurrentUser(LocalUser u) {
        LocalUser.currentUser = u;
    }

    public interface SignupCallback {
        void onSignupComplete(LocalUser user);
        void onSignupFailed(String message);
    }

    public interface LoginCallback {
        void onLoginComplete(LocalUser user);
        void onLoginFailed(String message);
    }

}