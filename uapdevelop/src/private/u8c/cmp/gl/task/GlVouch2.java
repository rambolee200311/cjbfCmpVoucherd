package u8c.cmp.gl.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.vo.bd.b04.DeptdocVO;
import nc.vo.bd.b06.PsndocVO;
import nc.vo.bd.b08.CustBasVO;
import nc.vo.bd.b19.BalatypeVO;
import nc.vo.bd.b28.CostsubjVO;
import nc.vo.bd.psndoc.PsnbasdocVO;
import nc.vo.ep.dj.DJZBHeaderVO;
import nc.vo.ep.dj.DJZBItemVO;
import nc.vo.pub.lang.UFDate;
import u8c.gl.vo.Ass;
import u8c.gl.vo.Cashflow;
import u8c.gl.vo.Details;
import u8c.gl.vo.Voucher;
import u8c.gl.vo.Vouchers;
import u8c.gl.vo.VoucherResult;
import u8c.server.CjbfSubString;
import u8c.server.CreditSubj;
import u8c.server.DebitSubj;
import com.alibaba.fastjson.JSON;

/*
 * 其他费用 对公  机关账套核算
 */
public class GlVouch2 {
	public static VoucherResult MakeVouch(DJZBHeaderVO vo,ArrayList<DJZBItemVO> vobs
			,CustBasVO custBasVO
			,DeptdocVO deptdocVO
			,PsndocVO psndocVO
			,CostsubjVO costsubjVO
			,BalatypeVO balatypeVO
			,String unitCode
			,String subjCode
			,String ass1
			,String ass2
			,UFDate payDate
			,String vouchType
			,BaseDAO dao){
		Logger.init("cjbfTask");
		VoucherResult voucherResult=new VoucherResult();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		Date date = new Date();  
		voucherResult.setDdate(dateFormat.format(date));
		voucherResult.setStatus("failed");
		String strResult="";
		String strExplanation="";
		String scomment="";
		List<Voucher> listVoucher=new ArrayList();
		Vouchers vouchers;
		try{
			//UFDate payDate=UFDate.getDate(vo.getPaydate()) ;
			String year=String.valueOf( payDate.getYear());
			String period=payDate.getStrMonth();
			String day=payDate.getStrDay();
			if (vo.getScomment()!=null){
				scomment=vo.getScomment();
			}
			Voucher voucher=new Voucher();
			voucher.setISDIFFLAG(false);
			voucher.setPk_corp(unitCode);
			voucher.setPk_glorgbook(unitCode+"-0003");
			voucher.setPk_vouchertype(vouchType);
			voucher.setVoucherkind("0");
			voucher.setPrepareddate(year+"-"+period+"-"+day);
			voucher.setYear(year);
			voucher.setPeriod(period);
			voucher.setPk_prepared("13501036623");
			voucher.setAttachment("1");
			List<Details> listDetails=new ArrayList();
			//strResult+=vo.getDjbh();
			Double sumJfbbje=(double) 0;
			
			DJZBItemVO vob=vobs.get(0);	
			
			Double jfbbje=vob.getJfbbje().doubleValue();//含税金额
				
			Double se=(double)0;
			if ((vo.getZyx11()!=null)&&(!vo.getZyx11().equals(""))){
				se=Double.parseDouble(vo.getZyx11());//税额
			}
			Double wsje=(double)(jfbbje-se);//不含税金额
			//credit 5 银行存款\北方账户\建行北环支行基本户36151
			strExplanation="列付"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")";
			Details credit5=new Details();
			credit5.setPk_accsubj(subjCode);
			credit5.setCreditamount(String.format("%.2f",jfbbje));
			credit5.setExplanation(strExplanation);
			credit5.setPk_currtype("CNY");
			credit5.setDetailindex(3);
			credit5.setCheckstyle(CreditSubj.getBalaType(subjCode,balatypeVO.getPk_balatype()));
			credit5.setCheckdate(vo.getPaydate().toString());
			/*
			String strCheckno=custBasVO.getCustname();
			if (custBasVO.getCustname().length()>10){
				//strCheckno=custBasVO.getCustname().substring(0,10);
				strCheckno=CjbfSubString.getSubString(custBasVO.getCustname(), 30);
			}*/
			//20220401 checkno=djbh			
			String strCheckno=vo.getDjbh();
			
			credit5.setCheckno(strCheckno);//(vo.getDjbh());
			//credit5.setCheckno(custBasVO.getCustname().substring(10));//(vo.getDjbh());
			if ((!ass1.equals(""))||(!ass2.equals(""))){
				List<Ass> assList5=new ArrayList();
				if ((ass1.equals("2"))){
					assList5.add(getAssDept(deptdocVO));
				}
				if ((ass2.equals("73"))){
					assList5.add(getAssCust(custBasVO));
				}
				credit5.setAss(assList5);
			}
			
			listDetails.add(credit5);	
				
			if (se!=0){
				//debit  3 应交税费\应交增值税\进项税额
				strExplanation="列付"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"进项税额"+"("+vo.getZyx1()+")";
				Details debit3=new Details();	
				debit3.setPk_accsubj("222101001");			
				debit3.setDebitamount(String.format("%.2f",se));			
				debit3.setExplanation(strExplanation);
				debit3.setPk_currtype("CNY");
				debit3.setDetailindex(2);
				List<Ass> assList3=new ArrayList();
				assList3.add(getAssDept(deptdocVO));
				//assList3.add(assCust);
				debit3.setAss(assList3);
				
				List<Cashflow> cashflowList3=new ArrayList();
				cashflowList3.add(getCashflow(String.format("%.2f",se)));
				debit3.setCashflow(cashflowList3);
				
				listDetails.add(debit3);
			}
			
			//debit  1 应付账款\一般计税\成本
			strExplanation="列付"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")";
			Details debit1=new Details();	
			
			String debitSubj=DebitSubj.getDebitSubj(vo.getZyx12(), unitCode, dao);			
			debit1.setPk_accsubj(debitSubj);		
			
			debit1.setDebitamount(String.format("%.2f",wsje));			
			debit1.setExplanation(strExplanation);
			debit1.setPk_currtype("CNY");
			debit1.setDetailindex(1);
			List<Ass> assList1=new ArrayList();			
			assList1.add(getAssDept(deptdocVO));			
			//assList1.add(getAssCust(custBasVO));
			debit1.setAss(assList1);
			
			List<Cashflow> cashflowList1=new ArrayList();
			cashflowList1.add(getCashflow(String.format("%.2f",wsje)));
			debit1.setCashflow(cashflowList1);
			
			listDetails.add(debit1);		
			
			voucher.setDetails(listDetails);
			listVoucher.add(voucher);
			
			vouchers =new Vouchers();
			vouchers.setVoucher(listVoucher);
			Logger.debug("GlVouch2 vouchers:"+JSON.toJSONString(vouchers));
			Log.getInstance("cjbfTask").debug("GlVouch2 vouchers:"+JSON.toJSONString(vouchers));
			voucherResult=GlVouchAPIlink.invokeAPI(JSON.toJSONString(vouchers),dao);
		
		}catch(Exception e){
			strResult=e.getMessage();
			voucherResult.setMessage(strResult);
			Logger.error("GlVouch2:"+e.getMessage(),e);
			Log.getInstance("cjbfTask").error("GlVouch2:"+e.getMessage(),e);
			e.printStackTrace();
		}
		
		return voucherResult;
	}
	
	private static Ass getAssDept(DeptdocVO deptdocVO){
		Ass assDept=new Ass();
		assDept.setChecktypecode("2");
		assDept.setCheckvaluecode(deptdocVO.getDeptcode());
		return assDept;
	}
	private static Ass getAssCust(CustBasVO custBasVO){
		Ass assCust=new Ass();
		assCust.setChecktypecode("73");
		assCust.setCheckvaluecode(custBasVO.getCustcode());
		return assCust;
	}
	private static Cashflow getCashflow(String money){
		Cashflow cashflow=new Cashflow();
		cashflow.setPk_currtype("CNY");
		cashflow.setPk_cashflow("1124");
		cashflow.setMoney(money);
		return cashflow;
	}
}
