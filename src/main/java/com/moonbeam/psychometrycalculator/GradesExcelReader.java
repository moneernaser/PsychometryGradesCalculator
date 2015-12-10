package com.moonbeam.psychometrycalculator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GradesExcelReader {

	private static final String FILE_PATH = "path_to_excel_file";

	public static void main(String args[]) {

		run();
	}

	private static void run() {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(FILE_PATH);

			// Using XSSF for xlsx format, for xls use HSSF
			Workbook workbook = new XSSFWorkbook(fis);

			int numberOfSheets = workbook.getNumberOfSheets();

			// looping over each workbook sheet
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				rowIterator.next();
				// iterating over each row
				while (rowIterator.hasNext()) {

					Row row = rowIterator.next();
					PsychomeryExam psychomeryExam = new PsychomeryExam();
					psychomeryExam.setEngSections(2);
					psychomeryExam.setQuanSections(2);
					psychomeryExam.setVerbalSections(2);
					psychomeryExam.setVerbalAns((int) row.getCell(3).getNumericCellValue());
					psychomeryExam.setEngAns((int) row.getCell(4).getNumericCellValue());
					psychomeryExam.setQuanAns((int) row.getCell(5).getNumericCellValue());
					row.createCell(6).setCellValue(psychomeryExam.getVerbalScore());
					row.createCell(7).setCellValue(psychomeryExam.getEngScore());
					row.createCell(8).setCellValue(psychomeryExam.getQuanScore());
					row.createCell(9).setCellValue(
							psychomeryExam.calculateGeneralScore(psychomeryExam.getQuanScore(), psychomeryExam.getVerbalScore(), psychomeryExam.getEngScore())
									.toString());

				}
			}

			try {
				FileOutputStream fos = new FileOutputStream(FILE_PATH);
				workbook.write(fos);
				fos.close();
				System.out.println(FILE_PATH + " is successfully written");

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
