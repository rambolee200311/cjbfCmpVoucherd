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
			//���д��
			//"�и�"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")"
			strExplanation="�и�"+deptName+" "+scomment+"("+custName+")"+"("+zyx1+")";
			break;
		case 2:
			//����Ӧ����\��ת˰��\һ���˰
			//"��ת"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"��ת˰��"+"("+vo.getZyx1()+")";
			strExplanation="��ת"+deptName+" "+scomment+"("+custName+")"+"��ת˰��"+"("+zyx1+")";
			break;
		case 3:
			//�ɱ��� Ӧ��˰��\Ӧ����ֵ˰\����˰��
			//"��ȷ��"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"����˰��"+"("+vo.getZyx1()+")"
			strExplanation="��ȷ��"+deptName+" "+scomment+"("+custName+")"+"����˰��"+"("+zyx1+")";
			break;
		case 4:
			//������ Ӧ��˰��\Ӧ����ֵ˰\����˰��
			//"�и�"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"����˰��"+"("+vo.getZyx1()+")"
			strExplanation="�и�"+deptName+" "+scomment+"("+custName+")"+"����˰��"+"("+zyx1+")";
			break;	
		case 5:
			//Ӧ���˿�\һ���˰\˰��
			//"�и�"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")"
			strExplanation="�и�"+deptName+" "+scomment+"("+custName+")"+"("+zyx1+")";
			break;
		case 6:
			//Ӧ���˿�\һ���˰\�ɱ�
			//"�и�"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")"
			strExplanation="�и�"+deptName+" "+scomment+"("+custName+")"+"("+zyx1+")";
			break;
		case 7:
			//�ڲ�����
			//"�и�"+deptdocVO.getDeptname()+" "+scomment+"("+custBasVO.getCustname()+")"+"("+vo.getZyx1()+")"
			strExplanation="�и�"+deptName+" "+scomment+"("+custName+")"+"("+zyx1+")";
			break;
		}
		return strExplanation;
	}
}
