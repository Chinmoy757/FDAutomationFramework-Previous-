package TestNG;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import Utility.Excel;

public class DataProviders {
	
	@DataProvider(name = "datas1")
	public static Object[][] createData1() throws Exception {
		//return combine(getFrom(), getTo());
		Excel ex = new Excel();
		String[][] Records = ex.xlReadSheet("src/Resource/Test_Condition_Records.xlsx", "Single Record");
		return Records;
		/*return new Object[][]{
			{"11/23/2017","11/23/2017"},
			{"04/01/2017","04/01/2017"},
			{"04/01/2017","04/20/2017"},
			{"04/01/2017","05/02/2017"},
			{"04/01/2017","05/10/2017"},
			{"04/01/2017","05/15/2017"},
			{"04/01/2017","05/30/2017"},
			{"04/01/2017","06/06/2017"},
			{"04/01/2017","06/10/2017"},
			{"04/01/2017","06/30/2017"},
			{"04/01/2017","07/05/2017"},
			{"04/01/2017","07/10/2017"},
			{"04/01/2017","07/15/2017"},
			{"04/01/2017","07/30/2017"},
			{"04/01/2017","08/10/2017"},
			{"04/01/2017","08/30/2017"},
			{"04/01/2017","09/30/2017"},
			{"04/01/2017","10/01/2017"},
			{"04/01/2017","10/05/2017"},
			{"04/01/2017","10/10/2017"},
			{"04/01/2017","10/20/2017"},
		};*/
	}
}
