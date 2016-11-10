package com.akrog.tolomet.gae.v1;

import java.util.ArrayList;
import java.util.List;

public class Motd {
	private String version;
	private List<String> changes;
	private String motd;
	private long stamp;
	
	public Motd() {
		changes = new ArrayList<String>();
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMotd() {
		return motd;
	}

	public void setMotd(String motd) {
		this.motd = motd;
	}

	public List<String> getChanges() {
		return changes;
	}		

	public void setChanges(List<String> changes) {
		this.changes = changes;
	}
	
	public void addChange(String change) {
		this.changes.add(change);
	}

	public long getStamp() {
		return stamp;
	}

	public void setStamp(long stamp) {
		this.stamp = stamp;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if( version != null ) {
			builder.append("v|"+version+"||");
			if( changes != null )
				for( String str : changes )
					builder.append("c|"+str+"||");
		}
		if( motd != null ) {
			builder.append("m|"+motd+"||");
			builder.append("s|"+stamp+"||");
		}
		return builder.toString();
	}
	
	static public Motd getInstance( String str ) {
		String[] lines = str.split("\\|\\|");
		String[] fields;
		Motd motd = new Motd();
		for( String line : lines ) {
			fields = line.split("\\|");
			if( fields.length != 2 )
				continue;
			switch( fields[0].charAt(0) ) {
				case 'v':
					motd.version = fields[1];
					break;
				case 'c':
					if( motd.changes == null )
						motd.changes = new ArrayList<String>();
					motd.changes.add(fields[1]);
					break;
				case 'm':
					motd.motd = fields[1];
					break;
				case 's':
					motd.stamp = Long.parseLong(fields[1]);
					break;
			}
		}
		return motd;
	}
}