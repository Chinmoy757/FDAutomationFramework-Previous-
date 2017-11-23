package Utility;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class ExcelUtility {
	
	public static void main(String args[]){
		
	}
	
	public static boolean matchRow(XSSFRow row1, XSSFRow row2){
		if((row1 == null) && (row2 == null)){
			return true;
		}
		else if((row1 == null) || (row2 == null)){
			return false;
		}
		boolean rowMatch = true;
		int firstCell1OfRow1 = row1.getFirstCellNum();
		int lastCell1ofRow1 = row1.getLastCellNum();
		int firstCell1OfRow2 = row2.getFirstCellNum();
		int lastCell1ofRow2 = row2.getLastCellNum();
		if((lastCell1ofRow1 - firstCell1OfRow1)==(lastCell1ofRow2-firstCell1OfRow2)){
			for(int i=firstCell1OfRow1 ; i<=lastCell1ofRow1; i++){
				XSSFCell cell1 = row1.getCell(i);
				XSSFCell cell2 = row2.getCell(i);
				if(!compareCells(cell1, cell2)){
					System.out.println("Cell - "+i+" mismatches");
					rowMatch = false;
					break;
				}
				else{
					System.out.println("Cell - "+i+" matches");
				}
			}
		}
		else{
			System.out.println("Length not equal");
			return false;
		}
		return rowMatch;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean compareCells(XSSFCell cell1, XSSFCell cell2){
		if((cell1 == null) && (cell2 == null)){
			return true;
		}
		else if((cell1 == null) || (cell2 == null)){
			return false;
		}
		boolean cellMatch = false;
		int type1 = cell1.getCellType();
		int type2 = cell2.getCellType();
		if(type1 == type2){
			if(cell1.getCellStyle() == cell2.getCellStyle()){
				switch(type1){
					case XSSFCell.CELL_TYPE_BLANK:
						if(cell2.getCellType() == XSSFCell.CELL_TYPE_BLANK){
							cellMatch = true;
						}
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						if(cell1.getBooleanCellValue() == cell2.getBooleanCellValue()){
							cellMatch = true;
						}
						break;
					case XSSFCell.CELL_TYPE_ERROR:
						if(cell1.getErrorCellValue() == cell2.getErrorCellValue()){
							cellMatch = true;
						}
						break;
					case XSSFCell.CELL_TYPE_FORMULA:
						if(cell1.getCellFormula() == cell2.getCellFormula()){
							cellMatch = true;
						}
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						if(cell1.getNumericCellValue() == cell2.getNumericCellValue()){
							cellMatch = true;
						}
						break;
					case XSSFCell.CELL_TYPE_STRING:
						if(cell1.getStringCellValue().equals(cell2.getStringCellValue())){
							cellMatch = true;
						}
						break;
				}
			}
			else{
				return false;
			}
		}else{
			return false;
		}
		return cellMatch;
	}
}
