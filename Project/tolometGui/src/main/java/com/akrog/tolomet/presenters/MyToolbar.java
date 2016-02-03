package com.akrog.tolomet.presenters;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;

import com.akrog.tolomet.AboutDialog;
import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Settings;

public class MyToolbar implements Toolbar.OnMenuItemClickListener, Presenter {
	private Tolomet tolomet;
	private Manager model;
	private Settings settings;
	private Toolbar toolbar;
	private MenuItem itemFavorite, itemRefresh, itemInfo, itemMap;
	private boolean isChecked;

	@Override
	public void initialize(Tolomet tolomet, Bundle bundle) {
		this.tolomet = tolomet;
		model = tolomet.getModel();
		settings = tolomet.getSettings();

		toolbar = (Toolbar)tolomet.findViewById(R.id.my_toolbar);
		toolbar.inflateMenu(R.menu.toolbar);
		Menu menu = toolbar.getMenu();
		itemFavorite = menu.findItem(R.id.favorite_item);
		itemRefresh = menu.findItem(R.id.refresh_item);
		itemInfo = menu.findItem(R.id.info_item);
		itemMap = menu.findItem(R.id.map_item);
		setFavorite(false);
		toolbar.setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		if( model.getCurrentStation().isSpecial() )
			return false;
		switch(item.getItemId()) {
			case R.id.refresh_item:
				tolomet.onRefresh();
				return true;
			case R.id.info_item:
				tolomet.onInfoUrl();
				return true;
			case R.id.settings_item:
				tolomet.onSettings();
				return true;
			case R.id.map_item:
				tolomet.onMapUrl();
				return true;
			case R.id.favorite_item:
				setFavorite(!isChecked);
				model.getCurrentStation().setFavorite(isChecked);
				settings.setFavorite(model.getCurrentStation().getCode(), isChecked);
				return true;
			case R.id.about_item:
				AboutDialog about = new AboutDialog(tolomet);
				about.setTitle(tolomet.getString(R.string.About));
				about.show();
				return true;
		}
		return false;
	}

	@Override
	public void updateView() {
		boolean enable = !model.getCurrentStation().isSpecial();
		itemRefresh.setEnabled(enable);
		itemInfo.setEnabled(enable);
		itemMap.setEnabled(enable);
		setFavorite(model.getCurrentStation().isFavorite());
		itemFavorite.setEnabled(enable);
	}

	@Override
	public void save(Bundle bundle) {	
	}

	private void setFavorite(boolean checked) {
		itemFavorite.setIcon(checked ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off);
		isChecked = checked;
	}
}
