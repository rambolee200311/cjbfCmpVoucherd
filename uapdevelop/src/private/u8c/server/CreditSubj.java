package u8c.server;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.fi.arap.pubutil.RuntimeEnv;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.ColumnProcessor;
import nc.vo.bd.b02.AccsubjVO;

public class CreditSubj {
	private static String fileName=RuntimeEnv.getNCHome()
			+ File.separator+"resources"
			+ File.separator+"gltask"
			+File.separator+"creditsubj.xml";
	/*
	 * 20220401 ���㷽ʽ�Ƿ����
	 */
	public static int getBalanFlag(String balancode){
		int bFlag=0;//�Ƿ�����ƾ֤ 1�� 0��
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			Element e=(Element)document.selectSingleNode("//Data/balancode[text()='"+balancode+"']/..");
			if(e!=null){
				bFlag=1;
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bFlag;
	}
	/*
	 * ���㷽ʽ��Ӧ��˾
	 */
	public static String getUnitCode(String balancode){
		String strResult="001001";
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			//Element rootElement = document.getRootElement();
			Element e=(Element)document.selectSingleNode("//Data/balancode[text()='"+balancode+"']/..");
			if(e!=null){
				if (e.element("isunit").getText().equals("1")){
					strResult=e.element("unitcode").getText();
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strResult;
	}
	/*
	 * ���㷽ʽ��Ӧ��ƿ�Ŀ
	 */
	public static String getSubjCode(String balancode){
		String strResult="100201001";
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			Element e=(Element)document.selectSingleNode("//Data/balancode[text()='"+balancode+"']/..");
			if(e!=null){				
					strResult=e.element("subjcode").getText();
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strResult;
	}
	/*
	 *��ƿ�Ŀƾ֤���
	 */
	public static String getVouchertype(String creditSubjCode){
		String strResult="ת";
		SAXReader reader = new SAXReader();
		try {
			if (creditSubjCode.indexOf("1001")==0){
				strResult="��";
			}
			if (creditSubjCode.indexOf("1002")==0){
				strResult="��";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strResult;
	}
	/*
	 * ��������1
	 */
	public static String getAss1(String balancode){
		String strResult="";
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			Element e=(Element)document.selectSingleNode("//Data/balancode[text()='"+balancode+"']/..");
			if(e!=null){				
					strResult=e.element("ass1").getText();
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strResult;
	}
	/*
	 * ��������2
	 */
	public static String getAss2(String balancode){
		String strResult="";
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(fileName));
			Element e=(Element)document.selectSingleNode("//Data/balancode[text()='"+balancode+"']/..");
			if(e!=null){				
					strResult=e.element("ass2").getText();
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strResult;
	}
	/*
	 * ���㷽ʽ
	 */
	public static String getBalaType(String subjCode,String balaTypeCode){
		String strResult="0001F8100000000002JB";
		if (subjCode.indexOf("1002")==0){
			strResult="0001F8100000000002JB";
		}else{
			strResult=balaTypeCode;
		}
		return strResult;
	}
	/*
	 * �ֹ�˾��ƿ�Ŀ
	 */
	public static String getUnitPkaccsubj(String subjCode,String unitCode,BaseDAO dao) throws DAOException{
		String strResult=subjCode;
		String sql="select pk_glorgbook from bd_glorgbook where glorgbookcode='"+unitCode+"-0003'";
		String pk_glorgbook=(String)dao.executeQuery(sql, new ColumnProcessor());
		sql="select pk_accsubj,subjcode,endflag,pk_glorgbook from bd_accsubj where subjcode='"+subjCode+"' and endflag='Y' and pk_glorgbook='"+pk_glorgbook+"'";
		AccsubjVO accsubj=(AccsubjVO)dao.executeQuery(sql,new BeanProcessor(AccsubjVO.class));
		if (accsubj!=null){			
			if (accsubj.getPk_accsubj()!=null){
				strResult=accsubj.getPk_accsubj();
			}
		}
		return strResult;
	}
}
