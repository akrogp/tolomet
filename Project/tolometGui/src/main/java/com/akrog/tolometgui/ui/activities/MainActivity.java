package com.akrog.tolometgui.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui.BuildConfig;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.DbTolomet;
import com.akrog.tolometgui.ui.fragments.AboutFragment;
import com.akrog.tolometgui.ui.fragments.ChartsFragment;
import com.akrog.tolometgui.ui.fragments.HelpFragment;
import com.akrog.tolometgui.ui.fragments.InfoFragment;
import com.akrog.tolometgui.ui.fragments.MapFragment;
import com.akrog.tolometgui.ui.fragments.ProviderFragment;
import com.akrog.tolometgui.ui.fragments.SettingsContainerFragment;
import com.akrog.tolometgui.ui.fragments.ToolbarFragment;
import com.akrog.tolometgui.ui.fragments.UpdateFragment;
import com.akrog.tolometgui.ui.services.StorageService;
import com.akrog.tolometgui.ui.viewmodels.MainViewModel;
import com.akrog.tolometgui.ui.views.AndroidUtils;
import com.google.android.material.navigation.NavigationView;

import java.io.File;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.util.Consumer;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends ToolbarActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String EXTRA_STATION = "com.akrog.tolomet.ui.activities.MainActivity.station";
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

        navigate(settings.getScreen());

        if( !settings.isIntroAccepted() )
            startActivity(new Intent(this, IntroActivity.class));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if( intent != null )
            setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_STATION);
        if( id != null ) {
            intent.removeExtra(EXTRA_STATION);
            Station station = model.findStation(id);
            model.selectStation(station);
        }
    }

    @Override
    protected void updateMenu(Station station) {
        super.updateMenu(station);
        /*if( fragment.useStation() ) {
            MenuItem shareItem = menu.findItem(R.id.share_item);
            setEnabled(shareItem, model.checkStation());
        }*/
    }

    public void navigate(int navId) {
        MenuItem menuItem = navView.getMenu().findItem(navId);
        if( menuItem != null ) {
            menuItem.setChecked(true);
            onNavigationItemSelected(menuItem);
        }
    }

    private boolean loadFrament(int id) {
        boolean ok = true;
        if (id == R.id.nav_charts)
            loadFragment(new ChartsFragment());
        else if (id == R.id.nav_info)
            loadFragment(new InfoFragment());
        else if (id == R.id.nav_origin)
            loadFragment(new ProviderFragment());
        else if (id == R.id.nav_maps)
            loadFragment(new MapFragment());
        else if (id == R.id.nav_settings)
            loadFragment(new SettingsContainerFragment());
        else if (id == R.id.nav_discover)
            loadFragment(new UpdateFragment());
        else if (id == R.id.nav_help)
            loadFragment(new HelpFragment());
        else if (id == R.id.nav_about)
            loadFragment(new AboutFragment());
        else if (id == R.id.nav_report ) {
            onReportItem();
            ok = false;
        }
        else
            ok = false;
        return ok;
    }

    private void loadFragment(ToolbarFragment fragment) {
        showStationMenu(fragment.useStation());
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
    public void onSettingsChanged(String key) {
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
        int id = item.getItemId();
        boolean ok = loadFrament(id);
        if( ok )
            settings.saveScreen(id);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return ok;
    }

    private void onReportItem() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
            "mailto","akrog.apps@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT,
            getString(R.string.ReportSubject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, String.format(
            "%s\n\n%s\n%s v%s (%d)\nAndroid %s (%d)\nPhone %s (%s)",
            getString(R.string.ReportGreetings),
            getString(R.string.ReportInfo),
            getString(R.string.app_name), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE,
            Build.VERSION.RELEASE, Build.VERSION.SDK_INT,
            Build.MANUFACTURER, Build.MODEL
        ));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.ReportApp)));
    }

    protected void saveScreenshot(String name, Consumer<File> onScreenShotReady) {
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.storage_rationale,
            () -> fragment.getBitmap(bitmap -> {
                if( bitmap == null )
                    return;
                File file = AndroidUtils.saveScreenShot(bitmap, Bitmap.CompressFormat.PNG, 85, name);
                if( file == null )
                    return;
                onScreenShotReady.accept(file);
            }), null );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Station station = model.getCurrentStation();
        if (id == R.id.share_item) {
            String name = String.format("%s_%d.png",
                fragment.useStation() && model.checkStation() ? station.toString() : getString(R.string.app_name),
                System.currentTimeMillis());
            saveScreenshot(name, file -> onScreenshot(file));
        }
        return super.onOptionsItemSelected(item);
    }

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
