package u8c.cmp.gl.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.bs.dao.BaseDAO;

import u8c.gl.vo.DataResponse;
import u8c.gl.vo.Voucher;
import u8c.server.HttpURLConnectionDemo;
import u8c.gl.vo.VoucherResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class GlVouchAPIlink {
	public static VoucherResult invokeAPI(String postData,BaseDAO dao){
		VoucherResult voucherResult=new VoucherResult();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		Date date = new Date();  
		voucherResult.setDdate(dateFormat.format(date));
		voucherResult.setStatus("failed");
		String strResult="";
		try{
			// ��һ�����ύ��API				
			// ���������ʵ�ַ���˿�,���� http://ip:port
			String serviceUrl = "https://api.yonyoucloud.com/apis/u8c/gl/voucher_insert";
			// ʹ��U8cloudϵͳ�����ã�����ڵ�·��Ϊ��
			// Ӧ�ü��� - ϵͳ����ƽ̨ - ϵͳ��Ϣ����
			// ������Ϣ�о������ԵĶ��չ�ϵ���£�
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("authoration", "apicode"); // �������뷽ʽ��ö��ֵΪ��������¼�� code�� ������¼�� name�� ������¼�� pk
			map.put("system", "1"); // ϵͳ����
			map.put("apicode", "503ef32a946f4a89897e9563fc043121"); // �û�
			map.put("trantype", "code"); // ����1qazWSX����Ҫ MD5 ���ܺ�¼��				
			String strTemp=HttpURLConnectionDemo.operator(serviceUrl, map,postData);
			
			// �ڶ�����������	
			JSONObject jsonResult =JSON.parseObject(strTemp);
			DataResponse dataResponse=JSON.toJavaObject(jsonResult,DataResponse.class);
			if (dataResponse.getStatus().equals("success")){// �����ķ���
				//strResult="";
				List<Voucher> voucherList=JSON.parseArray(dataResponse.getData(),Voucher.class);
				for(Voucher voucher:voucherList){
					strResult+=voucher.getCorp_name()+voucher.getVouchertype_name()+voucher.getYear()+voucher.getPeriod()+"-"+voucher.getNo()+";";
					//����ƾ֤��Ŀ
					GlVouchUpdate.voucherSubjUpdate(voucher.getPk_voucher(), dao);
				}
				voucherResult.setStatus("success");
				
			}else{// �쳣�ķ���
				//postResult.setStatus(dataResponse.getStatus());
				voucherResult.setStatus("failed");
				strResult+=dataResponse.getErrorcode()+"-"+dataResponse.getErrormsg()+";";
			}
		}catch(Exception e){
			strResult=e.getMessage();
			e.printStackTrace();
		}
		voucherResult.setMessage(strResult);
		return voucherResult;
	}
}
