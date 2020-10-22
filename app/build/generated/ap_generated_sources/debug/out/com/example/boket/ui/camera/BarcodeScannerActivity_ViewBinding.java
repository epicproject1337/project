// Generated code from Butter Knife. Do not modify!
package com.example.boket.ui.camera;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.boket.R;
import com.example.boket.cameraUtil.OverlayView;
import com.example.boket.cameraUtil.common.CameraSourcePreview;
import com.example.boket.cameraUtil.common.GraphicOverlay;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BarcodeScannerActivity_ViewBinding implements Unbinder {
  private BarcodeScannerActivity target;

  @UiThread
  public BarcodeScannerActivity_ViewBinding(BarcodeScannerActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BarcodeScannerActivity_ViewBinding(BarcodeScannerActivity target, View source) {
    this.target = target;

    target.barcodeOverlay = Utils.findRequiredViewAsType(source, R.id.barcodeOverlay, "field 'barcodeOverlay'", GraphicOverlay.class);
    target.preview = Utils.findRequiredViewAsType(source, R.id.preview, "field 'preview'", CameraSourcePreview.class);
    target.overlayView = Utils.findRequiredViewAsType(source, R.id.overlayView, "field 'overlayView'", OverlayView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BarcodeScannerActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.barcodeOverlay = null;
    target.preview = null;
    target.overlayView = null;
  }
}
