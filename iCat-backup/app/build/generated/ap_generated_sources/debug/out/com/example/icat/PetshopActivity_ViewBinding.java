// Generated code from Butter Knife. Do not modify!
package com.example.icat;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nex3z.notificationbadge.NotificationBadge;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PetshopActivity_ViewBinding implements Unbinder {
  private PetshopActivity target;

  @UiThread
  public PetshopActivity_ViewBinding(PetshopActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PetshopActivity_ViewBinding(PetshopActivity target, View source) {
    this.target = target;

    target.var_recyclerPetshop = Utils.findRequiredViewAsType(source, R.id.id_recyclerPetshop, "field 'var_recyclerPetshop'", RecyclerView.class);
    target.var_mainLayoutPetshop = Utils.findRequiredViewAsType(source, R.id.id_mainLayoutPetshop, "field 'var_mainLayoutPetshop'", LinearLayout.class);
    target.var_badge = Utils.findRequiredViewAsType(source, R.id.id_badge, "field 'var_badge'", NotificationBadge.class);
    target.var_btnKeranjang = Utils.findRequiredViewAsType(source, R.id.id_btnKeranjang, "field 'var_btnKeranjang'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PetshopActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.var_recyclerPetshop = null;
    target.var_mainLayoutPetshop = null;
    target.var_badge = null;
    target.var_btnKeranjang = null;
  }
}
