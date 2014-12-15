package com.akrog.tolomet;

public class Header {
	private int findIndex(String string, String[] cells) {
		for( int i = 0; i < cells.length; i++ )
			if( cells[i].contains(string) )
				return i;
		return -1;
	}
	
	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}
	
	public void findDate(String string, String[] cells) {
		date = findIndex(string, cells);
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
	
	public void findDir(String string, String[] cells) {
		dir = findIndex(string, cells);
	}

	public int getMed() {
		return med;
	}

	public void setMed(int med) {
		this.med = med;
	}
	
	public void findMed(String string, String[] cells) {
		med = findIndex(string, cells);
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
	public void findMax(String string, String[] cells) {
		max = findIndex(string, cells);
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}
	
	public void findTemp(String string, String[] cells) {
		temp = findIndex(string, cells);
	}

	public int getHum() {
		return hum;
	}

	public void setHum(int hum) {
		this.hum = hum;
	}
	
	public void findHum(String string, String[] cells) {
		hum = findIndex(string, cells);
	}

	public int getPres() {
		return pres;
	}

	public void setPres(int pres) {
		this.pres = pres;
	}
	
	public void findPres(String string, String[] cells) {
		pres = findIndex(string, cells);
	}

	private int date = -1, dir = -1, med = -1, max = -1, temp = -1, hum = -1, pres = -1;
		
}
