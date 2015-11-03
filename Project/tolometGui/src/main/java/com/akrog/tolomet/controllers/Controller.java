package com.akrog.tolomet.controllers;

import android.os.Bundle;

import com.akrog.tolomet.Tolomet;

public interface Controller {
	void initialize(Tolomet tolomet, Bundle bundle);
	void redraw();
	void save(Bundle bundle);
}
