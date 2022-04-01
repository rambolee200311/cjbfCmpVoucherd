package u8c.cmp.gl.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.bs.dao.BaseDAO;
import nc.bs.logging.Log;
import nc.bs.logging.Logger;
import nc.vo.bd.CorpVO;
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

import com.alibaba.fastjson.JSON;

/*
 * ���Ϸѡ��˹��ѡ���е�ѡ�רҵ�ְ���  �����׺���
 */
public class GlVouch4 {
	public static VoucherResult MakeVouch(DJZBHeaderVO vo,ArrayList<DJZBItemVO> vobs
			,CustBasVO custBasVO
			,DeptdocVO deptdocVO
			,PsndocVO psndocVO
			,CostsubjVO costsubjVO
			,BalatypeVO balatypeVO
			,CorpVO corpvo
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
			String year=String.valueOf( payDate.getYear());
			String period=payDate.getStrMonth();
			String day=payDate.getStrDay();
			if (vo.getScomment()!=null){
				scomment=vo.getScomment();
			}
			/*
			 * �ڲ�����ƾ֤
			 */
			Voucher voucherNbwl=new Voucher();
			voucherNbwl.setISDIFFLAG(false);
			voucherNbwl.setPk_corp("001001");
			voucherNbwl.setPk_glorgbook("001001-0003");
			voucherNbwl.setPk_vouchertype(vouchType);
			voucherNbwl.setVoucherkind("0");
			voucherNbwl.setPrepareddate(year+"-"+period+"-"+day);
			voucherNbwl.setYear(year);
			voucherNbwl.setPeriod(period);
			voucherNbwl.setPk_prepared("13501036623");
			voucherNbwl.setAttachment("1");
			List<Details> listDetailsNbwl=new ArrayList();
			//strResult+=vo.getDjbh();
			Double sumJfbbje=(double) 0;
			
			DJZBItemVO vob=vobs.get(0);	
			
			Double jfbbje=vob.getJfbbje().doubleValue();//��˰���
				
			Double se=(double)0;
			if ((vo.getZyx11()!=null)&&(!vo.getZyx11().equals(""))){
				se=Double.parseDouble(vo.getZyx11());//˰��
			}
			Double wsje=(double)(jfbbje-se);//����˰���
			//credit 5 �� ���д��\�����˻�\���б���֧�л�����36151
			strExplanation="�и�"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")";
			Details credit5=new Details();
			credit5.setPk_accsubj(subjCode);
			credit5.setCreditamount(String.format("%.2f",jfbbje));
			credit5.setExplanation(strExplanation);
			credit5.setPk_currtype("CNY");
			credit5.setDetailindex(3);
			credit5.setCheckstyle(CreditSubj.getBalaType(subjCode,balatypeVO.getPk_balatype()));
			credit5.setCheckdate(vo.getPaydate().toString());
			/*String strCheckno=custBasVO.getCustname();
			if (custBasVO.getCustname().length()>10){
				//strCheckno=custBasVO.getCustname().substring(0,10);
				strCheckno=CjbfSubString.getSubString(custBasVO.getCustname(), 30);
			}*/
			//20220401 checkno=djbh
			String strCheckno=vo.getDjbh();
			credit5.setCheckno(strCheckno);//(vo.getDjbh());
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
			listDetailsNbwl.add(credit5);	
				
			
			
			//debit  1 ��  �ڲ�����
			strExplanation="�и�"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")";
			Details debit1=new Details();	
			debit1.setPk_accsubj("117102");			
			debit1.setDebitamount(String.format("%.2f",jfbbje));			
			debit1.setExplanation(strExplanation);
			debit1.setPk_currtype("CNY");
			debit1.setDetailindex(1);
			
			List<Ass> assList1=new ArrayList();			
			assList1.add(getAssDept(deptdocVO));			
			assList1.add(getAssCust(corpvo));
			debit1.setAss(assList1);
			
			List<Cashflow> cashflowList1=new ArrayList();
			cashflowList1.add(getCashflow(String.format("%.2f",jfbbje)));
			debit1.setCashflow(cashflowList1);
			
			listDetailsNbwl.add(debit1);		
			
			voucherNbwl.setDetails(listDetailsNbwl);
			listVoucher.add(voucherNbwl);
			
			
			/*
			 * ������ƾ֤
			*/
			Voucher voucherFztpz=new Voucher();
			voucherFztpz.setISDIFFLAG(false);
			voucherFztpz.setPk_corp(corpvo.getUnitcode());
			voucherFztpz.setPk_glorgbook(corpvo.getUnitcode()+"-0003");
			voucherFztpz.setPk_vouchertype("ת");
			voucherFztpz.setVoucherkind("0");
			voucherFztpz.setPrepareddate(year+"-"+period+"-"+day);
			voucherFztpz.setYear(year);
			voucherFztpz.setPeriod(period);
			voucherFztpz.setPk_prepared("13501036623");
			voucherFztpz.setAttachment("1");
			List<Details> listDetailsFztpz=new ArrayList();
			
			//�� �ڲ�����
			strExplanation="�и�"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")";
			Details credit15=new Details();
			credit15.setPk_accsubj("117102");
			//credit15.setPk_accsubj(CreditSubj.getUnitPkaccsubj("117102",corpvo.getUnitcode(),dao));
			credit15.setCreditamount(String.format("%.2f",jfbbje));
			credit15.setExplanation(strExplanation);
			credit15.setPk_currtype("CNY");
			credit15.setDetailindex(5);
			
			List<Ass> assList15=new ArrayList();			
			assList15.add(getAssDept(deptdocVO));			
			assList15.add(getAssCustJG());
			credit15.setAss(assList15);
			
			listDetailsFztpz.add(credit15);
			if (se!=0){
				//�� ����Ӧ����\��ת˰��\һ���˰\
				Details credit14=new Details();
				credit14.setPk_accsubj("224110001");
				//credit14.setPk_accsubj(CreditSubj.getUnitPkaccsubj("224110001",corpvo.getUnitcode(),dao));
				credit14.setCreditamount(String.format("%.2f",se));
				credit14.setExplanation(strExplanation);
				credit14.setPk_currtype("CNY");
				credit14.setDetailindex(4);
				List<Ass> assList14=new ArrayList();
				assList14.add(getAssDept(deptdocVO));				
				//assList4.add(assCust);
				credit14.setAss(assList14);	
				
				listDetailsFztpz.add(credit14);
				
				//�� Ӧ��˰��\Ӧ����ֵ˰\����˰��
				Details debit13=new Details();
				debit13.setPk_accsubj("222101001");		
				//debit13.setPk_accsubj(CreditSubj.getUnitPkaccsubj("222101001",corpvo.getUnitcode(),dao));
				debit13.setDebitamount(String.format("%.2f",se));			
				debit13.setExplanation(strExplanation);
				debit13.setPk_currtype("CNY");
				debit13.setDetailindex(3);
				List<Ass> assList13=new ArrayList();
				assList13.add(getAssDept(deptdocVO));
				//assList3.add(assCust);
				debit13.setAss(assList13);
				
				listDetailsFztpz.add(debit13);
				
				//�� Ӧ���˿�\һ���˰\˰��
				Details debit12=new Details();
				debit12.setPk_accsubj("220202002");		
				//debit12.setPk_accsubj(CreditSubj.getUnitPkaccsubj("220202002",corpvo.getUnitcode(),dao));
				debit12.setDebitamount(String.format("%.2f",se));			
				debit12.setExplanation(strExplanation);
				debit12.setPk_currtype("CNY");
				debit12.setDetailindex(2);
				List<Ass> assList12=new ArrayList();
				assList12.add(getAssDept(deptdocVO));			
				assList12.add(getAssCust(custBasVO));
				debit12.setAss(assList12);
				
				/*List<Cashflow> cashflowList12=new ArrayList();
				cashflowList12.add(getCashflow(String.format("%.2f",se)));
				debit12.setCashflow(cashflowList12);*/
				
				listDetailsFztpz.add(debit12);
			}
			//�� Ӧ���˿�\һ���˰\�ɱ�
			Details debit11=new Details();
			debit11.setPk_accsubj("220202001");			
			//debit11.setPk_accsubj(CreditSubj.getUnitPkaccsubj("220202001",corpvo.getUnitcode(),dao));
			debit11.setDebitamount(String.format("%.2f",wsje));			
			debit11.setExplanation(strExplanation);
			debit11.setPk_currtype("CNY");
			debit11.setDetailindex(1);
			List<Ass> assList11=new ArrayList();			
			assList11.add(getAssDept(deptdocVO));			
			assList11.add(getAssCust(custBasVO));
			debit11.setAss(assList11);
			
			/*List<Cashflow> cashflowList11=new ArrayList();
			cashflowList11.add(getCashflow(String.format("%.2f",wsje)));
			debit11.setCashflow(cashflowList11);*/
			
			listDetailsFztpz.add(debit11);
			
			voucherFztpz.setDetails(listDetailsFztpz);
			listVoucher.add(voucherFztpz);
			
			vouchers =new Vouchers();
			vouchers.setVoucher(listVoucher);
			Logger.debug("GlVouch4 vouchers:"+JSON.toJSONString(vouchers));
			Log.getInstance("cjbfTask").debug("GlVouch4 vouchers:"+JSON.toJSONString(vouchers));
			voucherResult=GlVouchAPIlink.invokeAPI(JSON.toJSONString(vouchers),dao);
		
		}catch(Exception e){
			strResult=e.getMessage();
			voucherResult.setMessage(strResult);
			Logger.error("GlVouch4:"+e.getMessage(),e);
			Log.getInstance("cjbfTask").error("GlVouch4:"+e.getMessage(),e);
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
	private static Ass getAssCust(CorpVO corpvo){
		Ass assCust=new Ass();
		assCust.setChecktypecode("73");
		assCust.setCheckvaluecode(corpvo.getUnitcode());
		return assCust;
	}
	private static Ass getAssCustJG(){
		Ass assCust=new Ass();
		assCust.setChecktypecode("73");
		assCust.setCheckvaluecode("001001");
		return assCust;
	}
	private static Cashflow getCashflow(String money){
		Cashflow cashflow=new Cashflow();
		cashflow.setPk_currtype("CNY");
		cashflow.setPk_cashflow("1121");
		cashflow.setMoney(money);
		return cashflow;
	}
	
}
