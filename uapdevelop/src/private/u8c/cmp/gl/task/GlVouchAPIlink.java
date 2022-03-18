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
			// 第一步：提交到API				
			// 服务器访问地址及端口,例如 http://ip:port
			String serviceUrl = "https://api.yonyoucloud.com/apis/u8c/gl/voucher_insert";
			// 使用U8cloud系统中设置，具体节点路径为：
			// 应用集成 - 系统集成平台 - 系统信息设置
			// 设置信息中具体属性的对照关系如下：
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("authoration", "apicode"); // 档案翻译方式，枚举值为：编码请录入 code， 名称请录入 name， 主键请录入 pk
			map.put("system", "1"); // 系统编码
			map.put("apicode", "503ef32a946f4a89897e9563fc043121"); // 用户
			map.put("trantype", "code"); // 密码1qazWSX，需要 MD5 加密后录入				
			String strTemp=HttpURLConnectionDemo.operator(serviceUrl, map,postData);
			
			// 第二步：处理结果	
			JSONObject jsonResult =JSON.parseObject(strTemp);
			DataResponse dataResponse=JSON.toJavaObject(jsonResult,DataResponse.class);
			if (dataResponse.getStatus().equals("success")){// 正常的返回
				//strResult="";
				List<Voucher> voucherList=JSON.parseArray(dataResponse.getData(),Voucher.class);
				for(Voucher voucher:voucherList){
					strResult+=voucher.getCorp_name()+voucher.getVouchertype_name()+voucher.getYear()+voucher.getPeriod()+"-"+voucher.getNo()+";";
					//更正凭证科目
					GlVouchUpdate.voucherSubjUpdate(voucher.getPk_voucher(), dao);
				}
				voucherResult.setStatus("success");
				
			}else{// 异常的返回
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
