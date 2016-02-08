package com.akrog.tolomet.presenters;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.akrog.tolomet.AboutDialog;
import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.SettingsActivity;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Settings;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Locale;

public class MyToolbar implements Toolbar.OnMenuItemClickListener, Presenter {
	private Tolomet tolomet;
	private Manager model;
	private Settings settings;
	private Toolbar toolbar;
	private MenuItem itemFavorite;
	private final HashSet<MenuItem> stationItems = new HashSet<>();
	private boolean isChecked;

	@Override
	public void initialize(Tolomet tolomet, Bundle bundle) {
		this.tolomet = tolomet;
		model = tolomet.getModel();
		settings = tolomet.getSettings();

		toolbar = (Toolbar)tolomet.findViewById(R.id.my_toolbar);
		tolomet.setSupportActionBar(toolbar);
		tolomet.getSupportActionBar().setDisplayShowTitleEnabled(false);
		/*toolbar.inflateMenu(R.menu.toolbar);
		setMenu(toolbar.getMenu());
		setFavorite(false);*/
		toolbar.setOnMenuItemClickListener(this);
	}

	public void inflateMenu(Menu menu) {
		stationItems.clear();
		tolomet.getMenuInflater().inflate(R.menu.toolbar,menu);
		itemFavorite = menu.findItem(R.id.favorite_item);
		stationItems.add(itemFavorite);
		stationItems.add(menu.findItem(R.id.refresh_item));
		stationItems.add(menu.findItem(R.id.info_item));
		stationItems.add(menu.findItem(R.id.map_item));
		stationItems.add(menu.findItem(R.id.share_item));
		stationItems.add(menu.findItem(R.id.whatsapp_item));
		for( int i = 0; i < menu.size(); i++ )
			setAlpha(menu.getItem(i));
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.favorite_item:
				onFavoriteItem();
				return true;
			case R.id.refresh_item:
				tolomet.onRefresh();
				return true;
			case R.id.info_item:
				onInfoItem();
				return true;
			case R.id.map_item:
				onMapItem();
				return true;
			case R.id.share_item:
				onShareItem();
				return true;
			case R.id.whatsapp_item:
				onWhatsappItem();
				return true;
			case R.id.settings_item:
				onSettingsItem();
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
		setFavorite(!isChecked);
		model.getCurrentStation().setFavorite(isChecked);
		settings.setFavorite(model.getCurrentStation().getCode(), isChecked);
	}

	private void onInfoItem() {
		if( !tolomet.alertNetwork() )
			tolomet.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getInforUrl())));
	}

	private void onMapItem() {
		if( tolomet.alertNetwork() )
			return;
		String url = String.format(
				Locale.ENGLISH,
				"http://maps.google.com/maps?q=loc:%f,%f",
				model.getCurrentStation().getLatitude(), model.getCurrentStation().getLongitude()
		);
		tolomet.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}

	private void onShareItem() {
		File file = saveScreenShot(getScreenShot());
		if( file != null )
			shareScreenShot(file);
	}

	private void onWhatsappItem() {
		File waFile = saveScreenShot(getScreenShot());
		if( waFile != null )
			whatsappScreenShot(waFile);
	}

	private void onSettingsItem() {
		tolomet.startActivityForResult(
				new Intent(tolomet, SettingsActivity.class), Tolomet.SETTINGS_REQUEST);
	}

	private void onAboutItem() {
		AboutDialog about = new AboutDialog(tolomet);
		about.setTitle(tolomet.getString(R.string.About));
		about.show();
	}

	private void onReportItem() {
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
				"mailto","akrog.apps@gmail.com", null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT,
				tolomet.getString(R.string.ReportSubject));
		emailIntent.putExtra(Intent.EXTRA_TEXT, String.format(
				"%s\n\n%s\nAndroid %s (%d)\nPhone %s (%s)",
				tolomet.getString(R.string.ReportGreetings),
				tolomet.getString(R.string.ReportInfo),
				Build.VERSION.RELEASE, Build.VERSION.SDK_INT,
				Build.MANUFACTURER, Build.MODEL
		));
		tolomet.startActivity(Intent.createChooser(emailIntent, tolomet.getString(R.string.ReportApp)));
	}

	@Override
	public void updateView() {
		if( stationItems.isEmpty() )
			return;
		boolean enable = !model.getCurrentStation().isSpecial();
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
		//itemFavorite.setIcon(checked ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
		itemFavorite.setIcon(checked ? R.drawable.ic_favorite : R.drawable.ic_favorite_outline);
		isChecked = checked;
		setAlpha(itemFavorite);
	}

	private Bitmap getScreenShot() {
		return getScreenShot(tolomet.getWindow().getDecorView());
	}

	private Bitmap getScreenShot(View view) {
		boolean cache = view.isDrawingCacheEnabled();
       	view.setDrawingCacheEnabled(true);
		Bitmap bmpCache = view.getDrawingCache();
		Rect frame = new Rect();
		view.getWindowVisibleDisplayFrame(frame);
		Bitmap bitmap = Bitmap.createBitmap(
				bmpCache,
				frame.left,frame.top,frame.width(),frame.height(),
				null,true
		);
       	view.setDrawingCacheEnabled(cache);
       	return bitmap;
 	}

	private File saveScreenShot(Bitmap bm) {
		String name = String.format("%s_%d.png", model.getCurrentStation().toString(), System.currentTimeMillis());
		return saveScreenShot(bm,name);
	}

	private File saveScreenShot(Bitmap bm, String fileName) {
		File pix;
		if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO )
			pix = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		else
			pix = Environment.getExternalStorageDirectory();
		File dir = new File(pix, "Screenshots");
		if(!dir.exists())
			dir.mkdirs();
		File file = new File(dir, fileName);
		try {
			FileOutputStream fOut = new FileOutputStream(file);
			bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
			//bm.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
			fOut.flush();
			fOut.close();
		} catch (Exception e) {
			return null;
		}
		return file;
	}

	private Intent getScreenShotIntent(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, tolomet.getString(R.string.ShareSubject));
		intent.putExtra(android.content.Intent.EXTRA_TEXT, String.format(
			"%s %s!", tolomet.getString(R.string.ShareText), model.getCurrentStation().getName()));
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		return intent;
	}

	private void shareScreenShot(File file) {
		Intent intent = getScreenShotIntent(file);
		tolomet.startActivity(Intent.createChooser(intent, tolomet.getString(R.string.ShareApp)));
	}

	private void whatsappScreenShot(File file) {
		PackageManager pm = tolomet.getPackageManager();
		try {
			Intent waIntent = getScreenShotIntent(file);
			pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
			waIntent.setPackage("com.whatsapp");
			tolomet.startActivity(Intent.createChooser(waIntent, tolomet.getString(R.string.ShareApp)));
		} catch (PackageManager.NameNotFoundException e) {
			Toast.makeText(tolomet, tolomet.getString(R.string.NoWhatsApp), Toast.LENGTH_SHORT).show();
		}
	}

	private void setAlpha( MenuItem item ) {
		setAlpha(item.getIcon(), item.isEnabled());
	}

	private void setAlpha( Drawable drawable, boolean enabled ) {
		drawable.setAlpha(enabled?0x8A:0x42);
	}
}
