import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Text {

	public static void main(String[] args) {
		// 测试的url = http://www.cqccms.com.cn/incoc/GSViewEbike!viewCocEbike.action?vinCode=117321900000001
		HashMap<String, String> data = new HashMap();

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
	}
}
