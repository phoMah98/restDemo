import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtility {

    private static XSSFWorkbook excelWorkbook;
    private static XSSFSheet excelSheet;
    private static XSSFRow row;
    private static XSSFCell cell;

    public static Object[][] getData(String FilePath, String SheetName,int StartRow,int StartCol) throws IOException {
        String[][] Array = null;

        try {
            FileInputStream excelFile = new FileInputStream(FilePath);
            excelWorkbook = new XSSFWorkbook(excelFile);
            excelSheet = excelWorkbook.getSheet(SheetName);

            int ci, cj;
            int totalRows = excelSheet.getLastRowNum();
            int totalCols = getColoumnNmuber();
            int ignoredTest= 0 ;
//            System.out.println("cols:"+totalCols);
//            System.out.println("rows:"+totalRows);

            for(int i = 0; i<=totalRows;i++){
                if("no".equalsIgnoreCase(getCellData(i,0))) ignoredTest++;
            }

//            int arrayRows = excelSheet.getLastRowNum()+ (StartRow+1);
//            System.out.println("ignored test "+ ignoredTest);
//            System.out.println("Arrow rows "+ arrayRows);

            Array = new String[totalRows-ignoredTest][totalCols+1];
            ci = 0;
            for (int i = StartRow; i <= totalRows; i++) {
                if("no".equalsIgnoreCase(getCellData(i,0)))
                    continue;
                cj = 0;
                for (int j = StartCol; j <= totalCols; j++) {

                    Array[ci][cj] = getCellData(i, j);

                    System.out.println(Array[ci][cj]);;cj++;
                }
                ci++;
            }
        } catch (FileNotFoundException e) {

            System.out.println("Could not read the Excel sheet");

            e.printStackTrace();

        } catch (IOException e) {

            System.out.println("Could not read the Excel sheet");

            e.printStackTrace();

        }
        return Array;
    }

    public static String getCellData(int rowNum, int colNum) {

        try {
            cell = excelSheet.getRow(rowNum).getCell(colNum);
            if (cell != null) {
                CellType dataType = cell.getCellType();
                if (dataType == CellType.FORMULA) {
                    return "";
                } else if (dataType == CellType.STRING) {

                    String CellData = cell.getStringCellValue();

                    return CellData;

                } else if (dataType == CellType.NUMERIC) {
                    int CellData_n = (int) cell.getNumericCellValue();
                    String CellData_s = String.valueOf(CellData_n);
                    return CellData_s;
                }

            } else return "blank";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw (e);
        }
        return "";
    }

    public static void setCellData(String Result, int rowNum, int colNum, String Filepath) throws Exception {

        try {

            row = excelSheet.getRow(rowNum);

            cell = row.getCell(colNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            if (cell == null) {

                cell = row.createCell(colNum);

            }
            cell.setCellValue(Result);

            // Constant variables Test Data path and Test Data file name

            FileOutputStream fileOut = new FileOutputStream(Filepath);

            excelWorkbook.write(fileOut);

            fileOut.flush();

            fileOut.close();

        } catch (Exception e) {

            throw (e);

        }

    }

    public static int getColoumnNmuber() {

        Boolean isBlank = false;
        String cellData;
        int cols = 1;
        while (!isBlank) {

            cellData = getCellData(1, cols);
            if (cellData == "blank")
                isBlank = true;
            else cols += 1;
        }
        return cols - 1;

    }


    public static void main(String[] args) throws IOException {
        String[][] Array;
        Array = (String[][]) ExcelUtility.getData("C:\\Users\\BaraaAr\\IdeaProjects\\RestApi\\src\\test\\java\\testData\\testPostEmp.xlsx", "Sheet1",1,0);
        System.out.println("***********");
        for(int i=0;i<Array.length;i++){
            for (int j=0;j<Array[0].length;j++){
                System.out.println(Array[i][j]);
            }
        }

}
    }



