package com.example.boket.model.user;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public final class OtherUser implements User {

    private final String uid;
    private final String email;
    private final String name;
    private final String location;


    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = OtherUser.class.getName();
    private static final String collection = "users";

    private OtherUser(String uid, String email, String name, String location) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.location = location;
    }

    @Override
    public String getUid() {
        return uid;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLocation() {
        return location;
    }

    public static void getUserDataByUid(String uid, LoadUserCallback callback){
        DocumentReference docRef = db.collection(collection).document(uid);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                FirebaseUserModel um  = documentSnapshot.toObject(FirebaseUserModel.class);
                callback.onLoadComplete(um);
            }
        });
    }

    public interface LoadUserCallback{
        void onLoadComplete(User user);
    }
}
