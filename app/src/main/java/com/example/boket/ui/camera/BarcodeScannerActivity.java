package com.example.boket.ui.camera;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.boket.MainActivity;
import com.example.boket.R;
import com.example.boket.cameraUtil.BarcodeScanningProcessor;
import com.example.boket.cameraUtil.BarcodeScanningProcessor.BarcodeResultListener;
import com.example.boket.cameraUtil.OverlayView;
import com.example.boket.cameraUtil.common.BarcodeScanner;
import com.example.boket.cameraUtil.common.CameraSource;
import com.example.boket.cameraUtil.common.CameraSourcePreview;
import com.example.boket.cameraUtil.common.FrameMetadata;
import com.example.boket.cameraUtil.common.GraphicOverlay;
import com.example.boket.controller.Search;
import com.example.boket.model.Book;
import com.example.boket.ui.addAd.AddAdActivity;
import com.example.boket.ui.bookSeller.BooksellersFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.boket.cameraUtil.common.BarcodeScanner.Constants.KEY_CAMERA_PERMISSION_GRANTED;
import static com.example.boket.cameraUtil.common.BarcodeScanner.Constants.PERMISSION_REQUEST_CAMERA;

/**
 * @author Alexander Jyborn
 *
 * Class the represents the camera activity combining the camera with the overlay to get the GUI correct,
 * aswell as using the camerautil library to determine if barcode is valid.
 *
 * @since 2020-09-10
 */
public class BarcodeScannerActivity extends AppCompatActivity {

    String TAG = "BarcodeScannerActivity";

    @BindView(R.id.barcodeOverlay)
    GraphicOverlay barcodeOverlay;
    @BindView(R.id.preview)
    CameraSourcePreview preview;
    @BindView(R.id.overlayView)
    OverlayView overlayView;

    BarcodeScanningProcessor barcodeScanningProcessor;

    private CameraSource mCameraSource = null;

    boolean isCalled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getWindow() != null) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            Log.e(TAG, "Barcode scanner could not go into fullscreen mode!");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        ButterKnife.bind(this);

        if (preview != null)
            if (preview.isPermissionGranted(true, mMessageSender))
                new Thread(mMessageSender).start();
    }


    private void createCameraSource() {

        // To initialise the detector

        FirebaseVisionBarcodeDetectorOptions options =
                new FirebaseVisionBarcodeDetectorOptions.Builder()
                        .setBarcodeFormats(FirebaseVisionBarcode.FORMAT_ALL_FORMATS)
                        .build();

        FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options);


        // To connect the camera resource with the detector

        mCameraSource = new CameraSource(this, barcodeOverlay);
        mCameraSource.setFacing(CameraSource.CAMERA_FACING_BACK);

        barcodeScanningProcessor = new BarcodeScanningProcessor(detector);
        barcodeScanningProcessor.setBarcodeResultListener(getBarcodeResultListener());

        mCameraSource.setMachineLearningFrameProcessor(barcodeScanningProcessor);

        startCameraSource();
    }

    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());

        Log.d(TAG, "startCameraSource: " + code);

        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, PERMISSION_REQUEST_CAMERA);
            dlg.show();
        }

        if (mCameraSource != null && preview != null && barcodeOverlay != null) {
            try {
                Log.d(TAG, "startCameraSource: ");
                preview.start(mCameraSource, barcodeOverlay);
            } catch (IOException e) {
                Log.d(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        } else
            Log.d(TAG, "startCameraSource: not started");

    }

    /**
     * Requests camera permission when starting the camera
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        Log.d(TAG, "onRequestPermissionsResult: " + requestCode);
        preview.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (preview != null)
            preview.stop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isCalled = true;
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Log.d(TAG, "handleMessage: ");

            if (preview != null)
                createCameraSource();

        }
    };

    private final Runnable mMessageSender = () -> {
        Log.d(TAG, "mMessageSender: ");
        Message msg = mHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_CAMERA_PERMISSION_GRANTED, false);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    };

    /**
     * Returns the BarcoderesultListener that determines if the image from the camera is a barcode or not
     *
     * @return resultlistener
     */
    public BarcodeResultListener getBarcodeResultListener() {

        return new BarcodeResultListener() {
            @Override
            public void onSuccess(@Nullable Bitmap originalCameraImage, @NonNull List<FirebaseVisionBarcode> barcodes, @NonNull FrameMetadata frameMetadata, @NonNull GraphicOverlay graphicOverlay) {
                String isbn = "";

                for (FirebaseVisionBarcode barCode : barcodes)
                {
                    isbn = barCode.getRawValue();
                }

                if (BarcodeScanner.isValidISBN13(isbn)) {
                    String finalIsbn = isbn;
                    new Book(isbn, new Book.OnLoadCallback() {
                        @Override
                        public void onLoadComplete(Book book) {
                            if (book != null) {
                                Bundle bundle = new Bundle();
                                bundle.putString("isbn", finalIsbn);

                                Bundle prevPageBundle = getIntent().getExtras();
                                String prevPage = prevPageBundle.getString("PrevPage");

                                if(prevPage.equals("searchFragment")){
                                    BooksellersFragment booksellersFragment = new BooksellersFragment();
                                    booksellersFragment.setArguments(bundle);
                                    getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.nav_host_fragment, booksellersFragment)
                                            .addToBackStack(null)
                                            .commit();
                                }
                                else if(prevPage.equals("searchAddAdActivity")) {
                                    Intent intent = new Intent(BarcodeScannerActivity.this, AddAdActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(BarcodeScannerActivity.this, "BOK FINNS EJ I DATABASEN", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
