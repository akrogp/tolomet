package com.akrog.tolometgui2.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.akrog.tolometgui2.BuildConfig;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.DbTolomet;
import com.akrog.tolometgui2.ui.fragments.ChartsFragment;
import com.akrog.tolometgui2.ui.fragments.InfoFragment;
import com.akrog.tolometgui2.ui.fragments.MapFragment;
import com.akrog.tolometgui2.ui.fragments.ProviderFragment;
import com.akrog.tolometgui2.ui.fragments.ToolbarFragment;
import com.akrog.tolometgui2.ui.services.StorageService;
import com.akrog.tolometgui2.ui.viewmodels.MainViewModel;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends ToolbarActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navView;
    private MainViewModel model;
    private ToolbarFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.selectStation(settings.loadStation());
        //model.liveCurrentStation().observe(this, station -> {});

        Toolbar toolbar = configureToolbar();
        configureDrawer(toolbar);

        navigate(R.id.nav_charts);

        if( !settings.isIntroAccepted() )
            startActivity(new Intent(this, IntroActivity.class));
    }

    public void navigate(int navId) {
        MenuItem menuItem = navView.getMenu().findItem(navId);
        if( menuItem != null ) {
            menuItem.setChecked(true);
            onNavigationItemSelected(menuItem);
        }
    }

    private void loadFragment(ToolbarFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_layout, fragment);
        fragmentTransaction.commit();
        this.fragment = fragment;
    }

    @Override
    protected void onPause() {
        super.onPause();
        settings.saveStation(model.getCurrentStation());
    }

    private void configureDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationIcon(R.drawable.ic_toolbar);

        View headerView = navView.getHeaderView(0);
        TextView textVersion = headerView.findViewById(R.id.textVersion);
        textVersion.setText(String.format("(v%s - db%d)", BuildConfig.VERSION_NAME, DbTolomet.getInstance().getVersion()));
    }

    @Override
    public void onSettingsChanged() {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_charts)
            loadFragment(new ChartsFragment());
        else if (id == R.id.nav_info)
            loadFragment(new InfoFragment());
        else if (id == R.id.nav_origin)
            loadFragment(new ProviderFragment());
        else if (id == R.id.nav_maps)
            loadFragment(new MapFragment());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onScreenshot(File file) {
        final Intent intent = new Intent();
        final String text = fragment.getScreenshotText();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, fragment.getScreenshotSubject());
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        //intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(this, StorageService.FILE_PROVIDER_AUTHORITY, file));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        String relLink = fragment.getRelativeLink();
        if( relLink == null )
            shareScreenShot(intent);
        /*else FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse(String.format("https://tolomet-gae.appspot.com/app/%s", relLink)))
                .setDynamicLinkDomain("ekc2m.app.goo.gl")
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                        .setMinimumVersion(550)
                        .build())
                .buildShortDynamicLink()
                .addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if( task.isSuccessful() )
                            intent.putExtra(android.content.Intent.EXTRA_TEXT, String.format(
                                    "%s\n\n%s",
                                    text,
                                    task.getResult().getShortLink().toString()));
                        else
                            task.getException().printStackTrace();
                        listener.run(intent);
                    }
                });*/
    }

    private void shareScreenShot(Intent intent) {
        startActivity(Intent.createChooser(intent, getString(R.string.ShareApp)));
    }
}
