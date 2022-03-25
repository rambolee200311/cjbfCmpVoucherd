package u8c.cmp.gl.task;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import u8c.gl.vo.VoucherResult;
import nc.bs.dao.BaseDAO;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.bs.pub.taskcenter.BgWorkingContext;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.bd.b08.CubasdocVO;
import nc.bs.trade.business.HYPubBO;
import nc.fi.arap.pubutil.RuntimeEnv;
import nc.vo.bd.b04.DeptdocVO;
import nc.vo.bd.b06.PsndocVO;
import nc.vo.bd.psndoc.PsnbasdocVO;
import nc.vo.bd.b19.BalatypeVO;
import nc.vo.bd.b28.CostsubjVO;
import nc.vo.bd.CorpVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import u8c.gl.vo.*;
import u8c.server.CjbfSubString;
import u8c.server.CreditSubj;
import u8c.server.HttpURLConnectionDemo;
import u8c.server.DebitSubj;

public class CmpbillVoucherTask implements nc.bs.pub.taskcenter.IBackgroundWorkPlugin{
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
		String vouchid="";
		String dept="";
		String corp="";
		HYPubBO dmo=new HYPubBO();
		Logger.init("cjbfTask");
		String strLogger="";
		int bFlag=1;//是否生成凭证 1是 0否
		try{
			// 拿到参数
			LinkedHashMap<String, Object> para = param.getKeyMap();
			UFDate voucherDate=param.getLoginDate();
			String strBillType = (String) para.get("billtype");
			String strDjbh=(String) para.get("djbh");
			String strDjrq=(String) para.get("djrq");
			String sql="select paydate,effectdate,vouchid,djbh,djrq,djlxbm,djzt,bbje,fj,scomment,zyx1,zyx2,zyx3,zyx4,zyx5,zyx6,zyx7,zyx8,zyx9,zyx10,zyx11,zyx12,zyx13,zyx14,zyx15"
					+" from cmp_busibill"
					+" where zzzt=1 and djdl='fj' and djlxbm='D5' and dr=0 and dwbm='1009' and paydate='"+strDjrq+"'";	
			if ((strDjbh!=null)&&(!strDjbh.equals(""))){
				sql+=" and djbh='"+strDjbh+"'";
			}
			/*if ((strDjrq!=null)&&(!strDjrq.equals(""))){
				sql+=" and djrq='"+strDjrq+"'";
			}*/
			if (strBillType.equals("D5")){
				//主表vo
				ArrayList<DJZBHeaderVO> vos =(ArrayList<DJZBHeaderVO>) getDao().executeQuery(sql, new BeanListProcessor(DJZBHeaderVO.class));
				
				for(DJZBHeaderVO vo : vos){
					bFlag=1;
					//已生成过凭证不重复生成
					if (vo.getZyx15()!=null){
						if(vo.getZyx15().equals("success")){						
							bFlag=0;
						}
						if(vo.getZyx15().equals("noneed")){						
							bFlag=0;
						}
					}
					strLogger="";
					VoucherResult voucherResult=new VoucherResult();
					vouchid=vo.getVouchid();
					sql="select pj_jsfs,szxmid,deptid,hbbm,ywybm,jfbbje,dfbbje,payflag,skyhmc,skyhdz,skyhzh,fkyhmc,fkyhdz,fkyhzh"
							+" from dbo.cmp_busibill_b"
							+" where vouchid='"+vouchid+"';";
					ArrayList<DJZBItemVO> vobs =(ArrayList<DJZBItemVO>) getDao().executeQuery(sql, new BeanListProcessor(DJZBItemVO.class));
					strLogger+= "单据编号："+vo.getDjbh()+" "+vo.getZyx1()+" "+vo.getZyx2()+" "+vo.getZyx3();
					//customer base doc
					CustBasVO custBasVO=new CustBasVO();
					if ((vobs.get(0).getHbbm()!=null)&&(!vobs.get(0).getHbbm().equals(""))){
						sql="select pk_corp,pk_cubasdoc,custcode,custname"
								+" from bd_cubasdoc"+
								" where pk_cubasdoc='"+vobs.get(0).getHbbm()+"';";					
						custBasVO=(CustBasVO)getDao().executeQuery(sql,new BeanProcessor(CustBasVO.class));
						//CubasdocVO cubasdocVO=(CubasdocVO)dmo.queryByPrimaryKey(CubasdocVO.class, vobs.get(0).getHbbm());	
						//CubasdocVO cubasdocVO=(CubasdocVO)getDao().executeQuery(sql,new BeanProcessor(CubasdocVO.class));
						//预交某某税不生成凭证
						/*if ((custBasVO.getCustname().indexOf("预交")>=0)
							&&(custBasVO.getCustname().indexOf("税")>=0)){
							bFlag=0;
						}*/
						if (custBasVO.getCustname().indexOf("预交")>=0){
								bFlag=0;
							}
					}
					//department
					DeptdocVO deptdocVO=(DeptdocVO)dmo.queryByPrimaryKey(DeptdocVO.class, vobs.get(0).getDeptid());		
					CorpVO corpvo=new CorpVO();
					if ((deptdocVO.getDef2()!=null)&&(!deptdocVO.getDef2().equals(""))){
						corpvo=(CorpVO)dmo.queryByPrimaryKey(CorpVO.class, deptdocVO.getDef2());	
					}
					
					
					//person					
					//PsnbasdocVO psnbasVO=new PsnbasdocVO();
					PsndocVO psndocVO=new PsndocVO();
					if ((vobs.get(0).getYwybm()!=null)&&(!vobs.get(0).getYwybm().equals(""))){
						//psnbasVO=(PsnbasdocVO)dmo.queryByPrimaryKey(PsnbasdocVO.class, vobs.get(0).getYwybm());
						psndocVO=(PsndocVO)dmo.queryByPrimaryKey(PsndocVO.class, vobs.get(0).getYwybm());
					
					}
					//Cost subject
					CostsubjVO costsubjVO=(CostsubjVO)dmo.queryByPrimaryKey(CostsubjVO.class, vobs.get(0).getSzxmid());
					
					//Balance type
					BalatypeVO balatypeVO=(BalatypeVO)dmo.queryByPrimaryKey(BalatypeVO.class, vobs.get(0).getPj_jsfs());
					/*
					 * 20220325 付款单上结算方式  00900已线下支付的 不生成凭证
					 */
					if (balatypeVO.getBalancode().equals("00900")){
						bFlag=0;
					}
					
					String unitCode="001001";
					String subjCode="";
					String vouchType="";
					String ass1="";
					String ass2="";
					
					unitCode=CreditSubj.getUnitCode(balatypeVO.getBalancode());
					//corpvo
					if (unitCode.equals("")){
						if (corpvo.getUnitcode()!=null){
							unitCode=corpvo.getUnitcode();
						}else{
							bFlag=0;
						}
					}
					subjCode=CreditSubj.getSubjCode(balatypeVO.getBalancode());
					vouchType=CreditSubj.getVouchertype(subjCode);
					ass1=CreditSubj.getAss1(balatypeVO.getBalancode());
					ass2=CreditSubj.getAss2(balatypeVO.getBalancode());
					if (bFlag==1){
						if (unitCode.equals("001001")){	//机关核算和分公司核算
							if ((deptdocVO.getDef2()!=null)&&(!deptdocVO.getDef2().equals(""))){//分帐套
								if (costsubjVO.getCostcode()!="QTFY"){
								//材料费、人工费、机械费、专业分包费  分帐套核算
									voucherResult=GlVouch4.MakeVouch(vo, vobs,custBasVO,deptdocVO,psndocVO,costsubjVO,balatypeVO,corpvo,subjCode,ass1,ass2,voucherDate,vouchType,getDao());
								}else{	
									if ((vobs.get(0).getYwybm()!=null)&&(!vobs.get(0).getYwybm().equals(""))){
										//其他费用 对私  分帐套核算
										voucherResult=GlVouch6.MakeVouch(vo, vobs,custBasVO,deptdocVO,psndocVO,costsubjVO,balatypeVO,corpvo,subjCode,ass1,ass2,voucherDate,vouchType,getDao());
									}else{
										//其他费用 对公  分帐套核算
										voucherResult=GlVouch5.MakeVouch(vo, vobs,custBasVO,deptdocVO,psndocVO,costsubjVO,balatypeVO,corpvo,subjCode,ass1,ass2,voucherDate,vouchType,getDao());								
									}
								}
							}else{//机关
								if (costsubjVO.getCostcode()!="QTFY"){
									//材料费、人工费、机械费、专业分包费  机关账套核算
									voucherResult=GlVouch1.MakeVouch(vo, vobs,custBasVO,deptdocVO,psndocVO,costsubjVO,balatypeVO,unitCode,subjCode,ass1,ass2,voucherDate,vouchType,getDao());
								}else{
									if ((vobs.get(0).getYwybm()!=null)&&(!vobs.get(0).getYwybm().equals(""))){
										//其他费用 对私  机关账套核算
										voucherResult=GlVouch3.MakeVouch(vo, vobs,custBasVO,deptdocVO,psndocVO,costsubjVO,balatypeVO,unitCode,subjCode,ass1,ass2,voucherDate,vouchType,getDao());
									}else{
										//其他费用 对公  机关账套核算
										voucherResult=GlVouch2.MakeVouch(vo, vobs,custBasVO,deptdocVO,psndocVO,costsubjVO,balatypeVO,unitCode,subjCode,ass1,ass2,voucherDate,vouchType,getDao());								
									}
								}
							}
						}else{//仅分公司核算
							if (costsubjVO.getCostcode()!="QTFY"){
								//材料费、人工费、机械费、专业分包费  分公司账套核算
								voucherResult=GlVouch1.MakeVouch(vo, vobs,custBasVO,deptdocVO,psndocVO,costsubjVO,balatypeVO,unitCode,subjCode,ass1,ass2,voucherDate,vouchType,getDao());
							}else{
								if ((vobs.get(0).getYwybm()!=null)&&(!vobs.get(0).getYwybm().equals(""))){
									//其他费用 对私 分公司账套核算
									voucherResult=GlVouch3.MakeVouch(vo, vobs,custBasVO,deptdocVO,psndocVO,costsubjVO,balatypeVO,unitCode,subjCode,ass1,ass2,voucherDate,vouchType,getDao());
								}else{
									//其他费用 对公  分公司账套核算
									voucherResult=GlVouch2.MakeVouch(vo, vobs,custBasVO,deptdocVO,psndocVO,costsubjVO,balatypeVO,unitCode,subjCode,ass1,ass2,voucherDate,vouchType,getDao());								
								}
							}
						}
						
						//凭证结果
						strLogger+=" 凭证结果:" +JSON.toJSONString(voucherResult);
						Logger.debug(strLogger);
						Log.getInstance("cjbfTask").debug(strResult);
						String strTemp=CjbfSubString.getSubString(voucherResult.getMessage(),100);
											
						sql="update cmp_busibill set zyx13='"+strTemp+"',zyx14='"+voucherResult.getDdate()+"',zyx15='"+voucherResult.getStatus()+"' where vouchid='"+vouchid+"'";					
						getDao().executeUpdate(sql);
					}
					strResult="执行完成";
				}
				
			}
			
		}catch(Exception e){
			Logger.error("CmpbillVoucherTask:"+e.getMessage(),e);
			Log.getInstance("cjbfTask").error("CmpbillVoucherTask:"+e.getMessage(),e);
			strResult=e.getMessage();
			e.printStackTrace();
		}
		return strResult;
	
	}
}
