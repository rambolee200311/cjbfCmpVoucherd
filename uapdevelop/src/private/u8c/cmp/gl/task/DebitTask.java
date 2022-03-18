package u8c.cmp.gl.task;

import java.util.LinkedHashMap;
import nc.bs.dao.BaseDAO;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.vo.pub.BusinessException;
import u8c.server.DebitSubj;

public class DebitTask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	private BaseDAO dao; 
	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult="";
		// ÄÃµ½²ÎÊý
		LinkedHashMap<String, Object> para = param.getKeyMap();
		String subjCode = (String) para.get("temp");
		strResult=DebitSubj.getDebitSubj(subjCode, "001001", getDao());
		return strResult;
	}

}
