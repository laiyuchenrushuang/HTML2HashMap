import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Text {

	public static void main(String[] args) {

		// # HTML2HashMap
		// #注意里面的网址一般用网络请求，比如okhttp，在response里面显示是html的代码，生成的hashmap可以直接转化成json字符串
		// #网址测试连接：测试的url =
		// http://www.cqccms.com.cn/incoc/GSViewEbike!viewCocEbike.action?vinCode=117321900000001
		// 测试的url =
		// http://www.cqccms.com.cn/incoc/GSViewEbike!viewCocEbike.action?vinCode=117321900000001
		LinkedHashMap<String, String> data = new LinkedHashMap();

		String html = LYHTML.getHtmlString();
		Document doc = Jsoup.parse(html);
		Elements rows = doc.select("[class=biaotifontblue]").select("td");
		Elements rows1 = doc.select("[style=font-size: 14px]");

		int num = 0;
		for (int i = 1; i <= rows.size() - 2; i = 2 + i) {
			data.put(rows.get(i).html(), rows1.get(num).html());
			num++;
		}
		System.out.println(data.toString());
		System.out.println(
				"-------------------------------------------------------------------------------------------------------------------------------------------------------------");

		// 读写Excel
		try {
			readExcel();
			
			writeExcel();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		System.out.println("=======================================================================================================");
		String filePath= "D:\\works\\111.xlsx";
		//解析xlsx格式的文件
		XlsxParse.readxlsxFile(filePath);

	}

	// 去读Excel的方法readExcel，该方法的入口参数为一个File对象
	private static void readExcel() throws BiffException, IOException {
		String path = "D:\\works\\sss.xls";
		File xlsFile = new File(path);
//		if (path.contains("xlsx")) {
//			xlsFile.renameTo(new File("D:\\works\\111.xls"));
//		}
		// 获得工作簿对象
		Workbook workbook = Workbook.getWorkbook(xlsFile);
		// 获得所有工作表
		Sheet[] sheets = workbook.getSheets();
		// 遍历工作表
		if (sheets != null) {
			for (Sheet sheet : sheets) {
				// 获得行数
				int rows = sheet.getRows();
				// 获得列数
				int cols = sheet.getColumns();
				System.out.println(rows + " " + cols);
				// 读取数据
				for (int row = 0; row < rows; row++) {
					for (int col = 0; col < cols; col++) {
						Cell cell = sheet.getCell(col, row);
						System.out.print(cell.getContents() + " ");
					}
					System.out.println();
				}
			}
		}
		workbook.close();
	}

	private static void writeExcel() throws IOException, RowsExceededException, WriteException {

		File xlsFile = new File("D:\\works\\sss1.xls");
		// 创建一个工作簿
		WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
		// 创建一个工作表
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		// 向行和列中写数据
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				// 向工作表中添加数据
				sheet.addCell(new Label(col, row, "data" + row + col));
			}
		}
		workbook.write();
		workbook.close();
	}

}