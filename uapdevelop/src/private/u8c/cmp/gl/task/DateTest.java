package u8c.cmp.gl.task;

import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;

public class DateTest  implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	public String executeTask(BgWorkingContext param) throws BusinessException {
	 String strResult="";
	 UFDate  payDate=param.getLoginDate();
	 String year=String.valueOf( payDate.getYear());
	 String period=payDate.getStrMonth();
	 String day=payDate.getStrDay();
	 strResult=year+"-"+period+"-"+day;
	 return strResult;
	}
}
