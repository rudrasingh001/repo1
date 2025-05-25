//package com.booksWagon.utils;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.FileInputStream;
//import java.util.*;
//
//public class ExcelUtil {
//
//    public static List<Map<String, String>> getData(String filePath, String sheetName) {
//        List<Map<String, String>> dataList = new ArrayList<>();
//
//        try (FileInputStream fis = new FileInputStream(filePath);
//             Workbook workbook = new XSSFWorkbook(fis)) {
//
//            Sheet sheet = workbook.getSheet(sheetName);
//            Row headerRow = sheet.getRow(0);
//
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                Map<String, String> rowData = new HashMap<>();
//                Row row = sheet.getRow(i);
//
//                for (int j = 0; j < row.getLastCellNum(); j++) {
//                    String key = headerRow.getCell(j).getStringCellValue();
//                    String value = row.getCell(j) != null ? row.getCell(j).toString() : "";
//                    rowData.put(key.trim(), value.trim());
//                }
//
//                dataList.add(rowData);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return dataList;
//    }
//}




package com.booksWagon.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class ExcelUtil {

    // Existing method to read data
    public static List<Map<String, String>> getData(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Map<String, String> rowData = new HashMap<>();
                Row row = sheet.getRow(i);

                for (int j = 0; j < row.getLastCellNum(); j++) {
                    String key = headerRow.getCell(j).getStringCellValue();
                    String value = row.getCell(j) != null ? row.getCell(j).toString() : "";
                    rowData.put(key.trim(), value.trim());
                }

                dataList.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }

    // ✅ New method to write data into a specific cell
    public static void setCellData(String filePath, String sheetName, int rowIndex, String columnName, String result) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);

            // Find the column index for the column name (e.g., "actualResult")
            int colNum = -1;
            for (Cell cell : headerRow) {
                if (cell.getStringCellValue().trim().equalsIgnoreCase(columnName.trim())) {
                    colNum = cell.getColumnIndex();
                    break;
                }
            }

            // If column doesn't exist, create it at the end
            if (colNum == -1) {
                colNum = headerRow.getLastCellNum();
                headerRow.createCell(colNum).setCellValue(columnName);
            }

            // Get or create the row
            Row row = sheet.getRow(rowIndex);
            if (row == null) row = sheet.createRow(rowIndex);

            // Write result to the correct cell
            Cell cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);
            cell.setCellValue(result);

            // Write changes back to file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

