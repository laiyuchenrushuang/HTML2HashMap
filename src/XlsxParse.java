import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class XlsxParse {

	public static void readxlsxFile(String filePath) {

		Workbook wb = null;
        if(filePath==null){
            return ;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                 wb = new HSSFWorkbook(is);
            }else if(".xlsx".equals(extString)){
                 wb = new XSSFWorkbook(is);
            }else{
                 wb = null;
                 return;
            }
            
          //用来存放表中数据
            List list = new ArrayList<Map<String,String>>();
            //获取第一个sheet
            Sheet sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            Row row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            Map<String,String> map = new LinkedHashMap<String,String>();
            for (int i = 0;i<rownum;i++) {
            	Row rowi = sheet.getRow(i);
            	for (int j = 0;j<colnum;j++) {
            		map.put(rowi.getCell(0).toString(), rowi.getCell(j).toString());
            	}
            }
            list.add(map);
            System.out.println(list.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
