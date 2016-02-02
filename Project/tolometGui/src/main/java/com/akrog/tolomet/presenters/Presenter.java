package com.akrog.tolomet.presenters;

import android.os.Bundle;

import com.akrog.tolomet.Tolomet;

public interface Presenter {
	void initialize(Tolomet tolomet, Bundle bundle);
	void updateView();
	void save(Bundle bundle);
}
