package com.akrog.tolomet.ui.presenters;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.akrog.tolomet.ui.dialogs.AboutDialog;
import com.akrog.tolomet.ui.activities.AppSettingsActivity;
import com.akrog.tolomet.ui.activities.BaseActivity;
import com.akrog.tolomet.ui.activities.ChartsActivity;
import com.akrog.tolomet.ui.activities.HelpActivity;
import com.akrog.tolomet.ui.activities.InfoActivity;
import com.akrog.tolomet.ui.activities.MapActivity;
import com.akrog.tolomet.viewmodel.Model;
import com.akrog.tolomet.ui.activities.ProviderActivity;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.viewmodel.AppSettings;
import com.akrog.tolomet.ui.view.AndroidUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyToolbar implements Toolbar.OnMenuItemClickListener, Presenter, GoogleMap.SnapshotReadyCallback {
	private enum ShareOptions {GENERIC, WHATSAPP};

	@Override
	public void initialize(BaseActivity activity, Bundle bundle) {
		this.activity = activity;
		settings = activity.getSettings();

		toolbar = (Toolbar)activity.findViewById(R.id.my_toolbar);
		activity.setSupportActionBar(toolbar);
		toolbar.setOnMenuItemClickListener(this);
	}

	public void setButtons(int... enabledButtons) {
		if( enabledButtons.length == 0 )
			return;
		this.enabledButtons = new HashSet<>();
		for( int i = 0; i < enabledButtons.length; i++ )
			this.enabledButtons.add(enabledButtons[i]);
	}

	public void inflateMenu(Menu menu) {
		updateMenuItems(menu);

		stationItems.clear();
		addStationItem(menu, R.id.favorite_item);
		addStationItem(menu, R.id.refresh_item);
		addStationItem(menu, R.id.info_item);
		if( !Tolomet.supportsMap() )
			addStationItem(menu, R.id.map_item);
		addStationItem(menu, R.id.origin_item);
		addStationItem(menu, R.id.share_item);
		addStationItem(menu, R.id.whatsapp_item);
		addStationItem(menu, R.id.fly_item);

		for( int i = 0; i < menu.size(); i++ )
			setAlpha(menu.getItem(i));

		itemFavorite = menu.findItem(R.id.favorite_item);
		itemMode = menu.findItem(R.id.fly_item);
		setScreenMode(settings.isFlying());
	}

	private void updateMenuItems(Menu menu) {
		activity.getMenuInflater().inflate(R.menu.toolbar, menu);
		if( enabledButtons != null ) {
			List<Integer> items = new ArrayList<>();
			for (int i = 0; i < menu.size(); i++) {
				int item = menu.getItem(i).getItemId();
				if( !enabledButtons.contains(item) )
					items.add(item);
			}
			for( Integer item : items )
				menu.removeItem(item);
		}
	}

	private void addStationItem(Menu menu, int id) {
		MenuItem item = menu.findItem(id);
		if( item == null )
			return;
		stationItems.add(item);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.favorite_item:
				onFavoriteItem();
				return true;
			case R.id.refresh_item:
				activity.onRefresh();
				return true;
			case R.id.charts_item:
				onChartsItem();
				return true;
			case R.id.info_item:
				onInfoItem();
				return true;
			case R.id.origin_item:
				onOriginItem();
				return true;
			case R.id.browser_item:
				onBrowserItem();
				return true;
			case R.id.map_item:
				onMapItem();
				return true;
			case R.id.share_item:
				shareOption = ShareOptions.GENERIC;
				activity.getScreenShot(this);
				return true;
			case R.id.whatsapp_item:
				shareOption = ShareOptions.WHATSAPP;
				activity.getScreenShot(this);
				return true;
			case R.id.fly_item:
				setScreenMode(!isFlying);
				break;
			case R.id.settings_item:
				onSettingsItem();
				return true;
            case R.id.help_item:
                onHelpItem();
                return true;
			case R.id.about_item:
				onAboutItem();
				return true;
			case R.id.report_item:
				onReportItem();
				return true;
		}
		return false;
	}

	private void onFavoriteItem() {
		setFavorite(!isFavorite);
		activity.onFavorite(isFavorite);
	}

	private void onChartsItem() {
		Intent intent = new Intent(activity, ChartsActivity.class);
		activity.startActivity(intent);
	}

	private void onInfoItem() {
		if( !activity.alertNetwork() )
			activity.startActivity(new Intent(activity, InfoActivity.class));
	}

	private void onOriginItem() {
		if( !activity.alertNetwork() )
			activity.startActivity(new Intent(activity, ProviderActivity.class));
	}

	private void onBrowserItem() {
		if( !activity.alertNetwork() )
			activity.onBrowser();
	}

	private void onMapItem() {
		if( activity.alertNetwork() )
			return;
		Station station = model.getCurrentStation();
		if( !Tolomet.supportsMap() ) {
			String url = MapActivity.getUrl(station.getLatitude(),station.getLongitude());
			activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		} else {
			Intent intent = new Intent(activity, MapActivity.class);
			activity.startActivityForResult(intent, BaseActivity.MAP_REQUEST);
		}
	}

	private void onSettingsItem() {
		activity.startActivityForResult(
				new Intent(activity, AppSettingsActivity.class), BaseActivity.SETTINGS_REQUEST);
	}

    private void onHelpItem() {
        activity.startActivity(new Intent(activity, HelpActivity.class));
    }

	private void onAboutItem() {
		AboutDialog about = new AboutDialog(activity);
		about.setTitle(activity.getString(R.string.About));
		about.show();
	}

	private void onReportItem() {
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto","akrog.apps@gmail.com", null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT,
				activity.getString(R.string.ReportSubject));
		emailIntent.putExtra(Intent.EXTRA_TEXT, String.format(
				"%s\n\n%s\nAndroid %s (%d)\nPhone %s (%s)",
				activity.getString(R.string.ReportGreetings),
				activity.getString(R.string.ReportInfo),
				Build.VERSION.RELEASE, Build.VERSION.SDK_INT,
				Build.MANUFACTURER, Build.MODEL
		));
		activity.startActivity(Intent.createChooser(emailIntent, activity.getString(R.string.ReportApp)));
	}

	@Override
	public void updateView() {
		if( stationItems.isEmpty() )
			return;
		boolean enable = model.getCurrentStation() != null && !model.getCurrentStation().isSpecial();
		for( MenuItem item : stationItems ) {
			item.setEnabled(enable);
			setAlpha(item);
		}
		setFavorite(model.getCurrentStation().isFavorite());
	}

	@Override
	public void save(Bundle bundle) {	
	}

	private void setFavorite(boolean checked) {
		if( itemFavorite == null )
			return;
		//itemFavorite.setIcon(checked ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
		itemFavorite.setIcon(checked ? R.drawable.ic_favorite : R.drawable.ic_favorite_outline);
		isFavorite = checked;
		setAlpha(itemFavorite);
	}


	private void setScreenMode(boolean flying) {
		if( itemMode == null )
			return;
		settings.setFlying(flying);
		isFlying = flying;
		if( isFlying ) {
			itemMode.setIcon(R.drawable.ic_land_mode);
			itemMode.setTitle(R.string.LandMode);
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			settings.setUpdateMode(AppSettings.AUTO_UPDATES);
			Toast.makeText(activity,R.string.Takeoff,Toast.LENGTH_SHORT).show();
			flyNotified = true;
		} else {
			itemMode.setIcon(R.drawable.ic_flight_mode);
			itemMode.setTitle(R.string.FlyMode);
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			settings.setUpdateMode(AppSettings.SMART_UPDATES);
			if( flyNotified == true ) {
				Toast.makeText(activity, R.string.Landed, Toast.LENGTH_SHORT).show();
				flyNotified = false;
			}
		}
		activity.onSettingsChanged();
	}

	@Override
	public void onSnapshotReady(Bitmap bitmap) {
		String name = String.format("%s_%d.png", model.getCurrentStation().toString(), System.currentTimeMillis());
		//File file = saveScreenShot(bitmap, Bitmap.CompressFormat.JPEG, 90, name);
		final File file = AndroidUtils.saveScreenShot(bitmap, Bitmap.CompressFormat.PNG, 85, name);
		if( file == null )
		    return;
        getScreenShotIntent(file, new IntentListener() {
            @Override
            public void run(Intent intent) {
                switch( shareOption ) {
                    case WHATSAPP: whatsappScreenShot(intent); break;
                    default: shareScreenShot(intent); break;
                }
            }
        });
	}

	private void getScreenShotIntent(File file, final IntentListener listener ) {
		final Intent intent = new Intent();
        final String text = activity.getScreenShotText();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, activity.getScreenShotSubject());
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        //intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(activity, Tolomet.FILE_PROVIDER_AUTHORITY, file));
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		String relLink = activity.getRelativeLink();
		if( relLink == null )
		    listener.run(intent);
		else FirebaseDynamicLinks.getInstance().createDynamicLink()
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
            });
	}

	private void shareScreenShot(Intent intent) {
		activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.ShareApp)));
	}

	private void whatsappScreenShot(Intent waIntent) {
		PackageManager pm = activity.getPackageManager();
		try {
			pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
			waIntent.setPackage("com.whatsapp");
			activity.startActivity(Intent.createChooser(waIntent, activity.getString(R.string.ShareApp)));
		} catch (PackageManager.NameNotFoundException e) {
			Toast.makeText(activity, activity.getString(R.string.NoWhatsApp), Toast.LENGTH_SHORT).show();
		}
	}

	private void setAlpha( MenuItem item ) {
		setAlpha(item.getIcon(), item.isEnabled());
	}

	private void setAlpha( Drawable drawable, boolean enabled ) {
		drawable.setAlpha(enabled ? 0x8A : 0x42);
		//drawable.setAlpha(enabled?0xFF:0x42);
	}

    @Override
	public void setEnabled(boolean enabled) {
		Menu menu = toolbar.getMenu();
		for( int i = 0; i < menu.size(); i++ )
			menu.getItem(i).setEnabled(enabled);
	}

    @Override
    public void onSettingsChanged() {
    }

    private interface IntentListener {
        void run(Intent intent);
    }

    private BaseActivity activity;
	private final Model model = Model.getInstance();
	private AppSettings settings;
	private Toolbar toolbar;
	private MenuItem itemFavorite, itemMode;
	private final HashSet<MenuItem> stationItems = new HashSet<>();
	private boolean isFavorite, isFlying, flyNotified = false;
	private Set<Integer> enabledButtons;
	private ShareOptions shareOption;
}
