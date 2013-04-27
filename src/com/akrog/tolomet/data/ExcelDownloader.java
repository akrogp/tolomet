package com.akrog.tolomet.data;

import jxl.Sheet;
import jxl.Workbook;

import com.akrog.tolomet.Tolomet;

public class ExcelDownloader extends Downloader {
	public ExcelDownloader(Tolomet tolomet, WindProvider provider, String desc) {
		super(tolomet, provider, desc);
	}
	
	public ExcelDownloader(Tolomet tolomet, WindProvider provider) {
		super(tolomet, provider);
	}
	
	@Override
	protected String parseInput(java.io.InputStream is) throws Exception {
		Workbook workbook = Workbook.getWorkbook(is);
		Sheet sheet = workbook.getSheet(0);
		int x, y;
		StringBuilder line;
		StringBuilder result = new StringBuilder();
		for( y = 0; y < sheet.getRows(); y++ ) {
			line = new StringBuilder();
			for( x = 0; x < sheet.getColumns(); x++ ) {
				if( x > 0 )
					line.append('|');
				line.append(sheet.getCell(x, y).getContents());
			}
			result.append(line);
			result.append("\n");
		}
		workbook.close();
		return result.toString();
	}
}
