package com.akrog.tolomet;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.akrog.tolomet.presenters.MyToolbar;

import java.lang.reflect.Method;

/**
 * Created by gorka on 24/02/16.
 */
public abstract class ToolbarActivity extends ModelActivity {
    public void createView(int layoutResId) {
        setContentView(layoutResId);
        toolbar.initialize(this, null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        toolbar.save(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(menu);
        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        Log.i(getClass().getSimpleName(), "called");
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public void redraw() {
        toolbar.updateView();
    }

    private final MyToolbar toolbar = new MyToolbar();
}
