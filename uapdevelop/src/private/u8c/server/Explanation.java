package u8c.server;
/*
 * 2022-04-20
 * explanation
 */
public class Explanation {
	public static String getExplanation(String deptName,String scomment,String custName,String zyx1,String digest,int iType){
		String strExplanation="";
		switch(iType){
		case 1:
			//银行存款
			//"列付"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")"
			strExplanation="列付"+deptName+" "+scomment+"("+custName+")"+"("+zyx1+")";
			break;
		case 2:
			//其他应付款\待转税额\一般计税
			//"列转"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"待转税额"+"("+vo.getZyx1()+")";
			strExplanation="列转"+deptName+" "+scomment+"("+custName+")"+"待转税额"+"("+zyx1+")";
			break;
		case 3:
			//成本类 应交税费\应交增值税\进项税额
			//"列确认"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"进项税额"+"("+vo.getZyx1()+")"
			strExplanation="列确认"+deptName+" "+scomment+"("+custName+")"+"进项税额"+"("+zyx1+")";
			break;
		case 4:
			//费用类 应交税费\应交增值税\进项税额
			//"列付"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"进项税额"+"("+vo.getZyx1()+")"
			strExplanation="列付"+deptName+" "+scomment+"("+custName+")"+"进项税额"+"("+zyx1+")";
			break;	
		case 5:
			//应付账款\一般计税\税额
			//"列付"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")"
			strExplanation="列付"+deptName+" "+scomment+"("+custName+")"+"("+zyx1+")";
			break;
		case 6:
			//应付账款\一般计税\成本
			//"列付"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")"
			strExplanation="列付"+deptName+" "+scomment+"("+custName+")"+"("+zyx1+")";
			break;
		case 7:
			//内部往来
			//"列付"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")"
			strExplanation="列付"+deptName+" "+scomment+"("+custName+")"+"("+zyx1+")";
			break;
		}
		return strExplanation;
	}
}
