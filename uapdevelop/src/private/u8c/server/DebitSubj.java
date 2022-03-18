package u8c.server;
/*
 * 费用科目默认54010100499
 */
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.vo.bd.b02.AccsubjVO;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;

public class DebitSubj {
	public static String getDebitSubj(String subjCode,String unitCode,BaseDAO dao) throws DAOException{
		String strResult="54010100499";		
		String sql="select pk_glorgbook from bd_glorgbook where glorgbookcode='"+unitCode+"-0003'";
		String pk_glorgbook=(String)dao.executeQuery(sql, new ColumnProcessor());
		sql="select subjcode,endflag,pk_glorgbook from bd_accsubj where subjcode='"+subjCode+"' and endflag='Y' and pk_glorgbook='"+pk_glorgbook+"'";
		AccsubjVO accsubj=(AccsubjVO)dao.executeQuery(sql,new BeanProcessor(AccsubjVO.class));
		if (accsubj!=null){
			strResult=accsubj.getSubjcode();
			if (strResult.equals("")){
				strResult="54010100499";
			}
		}
		return strResult;
	}
	public static String checkDebitSubj(String subjCode,String unitCode,BaseDAO dao) throws DAOException{
		String strResult=subjCode+"54010100499";		
		String sql="select pk_glorgbook from bd_glorgbook where glorgbookcode='"+unitCode+"-0003'";
		String pk_glorgbook=(String)dao.executeQuery(sql, new ColumnProcessor());
		sql="select subjcode,endflag,pk_glorgbook from bd_accsubj where subjcode='"+subjCode+"' and endflag='Y' and pk_glorgbook='"+pk_glorgbook+"'";
		AccsubjVO accsubj=(AccsubjVO)dao.executeQuery(sql,new BeanProcessor(AccsubjVO.class));
		if (accsubj!=null){
			strResult=accsubj.getSubjcode();			
		}
		return strResult;
	}
}
