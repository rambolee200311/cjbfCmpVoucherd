package u8c.cmp.gl.task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import bsh.StringUtil;
import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.bs.trade.business.HYPubBO;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.gl.pubvoucher.VoucherVO;
import nc.vo.gl.pubvoucher.DetailVO;
import nc.vo.bd.b02.AccsubjVO;
public class CmpVoucherUpdateTask  implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
	private BaseDAO dao; 
	private BaseDAO getDao() {
		if (dao == null) {
			dao = new BaseDAO();
		}
		return dao;
	}
	
	
	@Override
	public String executeTask(BgWorkingContext param) throws BusinessException {
		String strResult="执行完成";
		// 拿到参数
		LinkedHashMap<String, Object> para = param.getKeyMap();
		UFDate voucherDate=param.getLoginDate();					
		String strYear=(String) para.get("year");
		String strPeriod=(String) para.get("period");
		HYPubBO dmo=new HYPubBO();
		Logger.init("cjbfTask");
		try{
			String sql="select year,period,pk_voucher,pk_glorgbook from gl_voucher"
					+" where year='"+strYear+"' and period='"+strPeriod+"'"
					+" and pk_glorgbook!='0001F8100000000003N5'"
					//+" and pk_glorgbook='0001F8100000000009PM'"
					+" and pk_system='XX'";
	
			ArrayList<VoucherVO> vos =(ArrayList<VoucherVO>) getDao().executeQuery(sql, new BeanListProcessor(VoucherVO.class));
			
			for(VoucherVO vo:vos){
				GlVouchUpdate.voucherSubjUpdate(vo.getPk_voucher(), getDao());
				
			}
		}catch(Exception e){
			strResult=e.getMessage();
			Logger.error(strResult,e);
		}
		return strResult;
	}
}
