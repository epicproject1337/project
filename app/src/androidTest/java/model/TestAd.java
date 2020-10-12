package model;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import com.example.boket.model.Ad;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TestAd {
    private Context context;
    private FirebaseAuth mAuth;

    @Before
    public void init(){
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void CreateAd() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseApp.initializeApp(context);
        mAuth = FirebaseAuth.getInstance();
        Ad ad = new Ad(mAuth.getCurrentUser().getUid(), "test@gmail.com", "9789144090504", 120, "Giood", "göteborg", false);
        ad.save();
        Ad.getAdsByISBN("9789144090504", new Ad.GetAdsCallback() {
            @Override
            public void onGetAdsComplete(ArrayList<Ad> adList) {
                Ad loadedAd = adList.get(0);
                Log.d("LoadAD", "SUCCESS:" + loadedAd.toString());
                assertEquals(ad, loadedAd);
                lock.countDown();
            }
        });
        lock.await(1, TimeUnit.MINUTES);
    }
}
