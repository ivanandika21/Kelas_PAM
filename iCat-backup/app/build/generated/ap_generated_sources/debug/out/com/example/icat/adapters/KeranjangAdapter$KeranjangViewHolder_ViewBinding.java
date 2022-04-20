// Generated code from Butter Knife. Do not modify!
package com.example.icat.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.icat.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class KeranjangAdapter$KeranjangViewHolder_ViewBinding implements Unbinder {
  private KeranjangAdapter.KeranjangViewHolder target;

  @UiThread
  public KeranjangAdapter$KeranjangViewHolder_ViewBinding(
      KeranjangAdapter.KeranjangViewHolder target, View source) {
    this.target = target;

    target.var_imageView = Utils.findRequiredViewAsType(source, R.id.id_imageView, "field 'var_imageView'", ImageView.class);
    target.var_namaItem = Utils.findRequiredViewAsType(source, R.id.id_namaItem, "field 'var_namaItem'", TextView.class);
    target.var_hargaItem = Utils.findRequiredViewAsType(source, R.id.id_hargaItem, "field 'var_hargaItem'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    KeranjangAdapter.KeranjangViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.var_imageView = null;
    target.var_namaItem = null;
    target.var_hargaItem = null;
  }
}
