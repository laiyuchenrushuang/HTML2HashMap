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
		// #ע���������ַһ�����������󣬱���okhttp����response������ʾ��html�Ĵ��룬���ɵ�hashmap����ֱ��ת����json�ַ���
		// #��ַ�������ӣ����Ե�url =
		// http://www.cqccms.com.cn/incoc/GSViewEbike!viewCocEbike.action?vinCode=117321900000001
		// ���Ե�url =
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

		// ��дExcel
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
		//����xlsx��ʽ���ļ�
		XlsxParse.readxlsxFile(filePath);

	}

	// ȥ��Excel�ķ���readExcel���÷�������ڲ���Ϊһ��File����
	private static void readExcel() throws BiffException, IOException {
		String path = "D:\\works\\sss.xls";
		File xlsFile = new File(path);
//		if (path.contains("xlsx")) {
//			xlsFile.renameTo(new File("D:\\works\\111.xls"));
//		}
		// ��ù���������
		Workbook workbook = Workbook.getWorkbook(xlsFile);
		// ������й�����
		Sheet[] sheets = workbook.getSheets();
		// ����������
		if (sheets != null) {
			for (Sheet sheet : sheets) {
				// �������
				int rows = sheet.getRows();
				// �������
				int cols = sheet.getColumns();
				System.out.println(rows + " " + cols);
				// ��ȡ����
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
		// ����һ��������
		WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
		// ����һ��������
		WritableSheet sheet = workbook.createSheet("sheet1", 0);
		// ���к�����д����
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				// ���������������
				sheet.addCell(new Label(col, row, "data" + row + col));
			}
		}
		workbook.write();
		workbook.close();
	}

}