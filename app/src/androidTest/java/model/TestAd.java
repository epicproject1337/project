package model;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import com.example.boket.model.Ad;
import com.example.boket.model.user.User;
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
    public void CreateAdThenArchive() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        FirebaseApp.initializeApp(context);
        User user = new MockUser();
        Ad ad = new Ad("bookname",user.getUid(), user.getEmail(), "9789144090504", 120, "Giood", user.getLocation(), false);
        ad.save();
        TimeUnit.SECONDS.sleep(1);
        Ad.getAdsByUser(user.getUid(), false, new Ad.GetAdsCallback() {
            @Override
            public void onGetAdsComplete(ArrayList<Ad> adList) {
                Ad loadedAd = adList.get(0);
                Log.d("onComplete", "SUCCESS:" + loadedAd.toString());
                Log.d("onComplete", "SUCCESS:" + ad.toString());
                assertEquals(ad, loadedAd);
                loadedAd.archiveAd();
                Ad.getAdsByUser(user.getUid(), true, new Ad.GetAdsCallback() {
                    @Override
                    public void onGetAdsComplete(ArrayList<Ad> adList) {
                        Ad loadedAd2 = adList.get(0);
                        Log.d("onComplete", "SUCCESS:" + loadedAd.toString());
                        assertEquals(loadedAd, loadedAd2);
                        lock.countDown();
                    }
                });
            }
        });
        TimeUnit.SECONDS.sleep(1);
        lock.await(1, TimeUnit.MINUTES);
    }

}
