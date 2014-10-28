package com.akrog.tolomet.io;

import jxl.Sheet;
import jxl.Workbook;

public class ExcelDownloader extends Downloader {
	@Override
	protected String parseInput(java.io.InputStream is) throws Exception {
		Workbook workbook = Workbook.getWorkbook(is);
		Sheet sheet = workbook.getSheet(0);
		int x, y;
		StringBuilder line;
		StringBuilder result = new StringBuilder();
		for( y = 0; y < sheet.getRows(); y++ ) {
			if( isCancelled() )
				return null;
			line = new StringBuilder();
			for( x = 0; x < sheet.getColumns(); x++ ) {
				if( x > 0 )
					line.append('|');
				line.append(sheet.getCell(x, y).getContents());
			}
			result.append(line);
			if( usingLinebreak )
				result.append("\n");
		}
		workbook.close();
		return result.toString();
	}
}
