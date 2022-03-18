package u8c.server;

public class CjbfSubString {
	public static String getSubString(String str, int length) throws Exception {
		int i;	
		int n;	
		byte[] bytes = str.getBytes("Unicode");      //ʹ��Unicode�ַ������ַ��������byte����	
		i = 2;      //bytes��ǰ�����ֽ��Ǳ�־λ��bytes[0] = -2, bytes[1] = -1, �ʴӵڶ�λ��ʼ	
		n = 0;	
		for(i=2; i < bytes.length && n < length;i++) {	
			if(i % 2 == 1) {	
				n++;	
			} else {	
				if(bytes[i] != 0) {	
					n++;	
				}	
			}	
		}
	
		//ȥ���������
	
		if(i % 2 == 1) {	
			if(bytes[i-1] != 0) {	
				i = i -1;	
			} else {	
				i = i + 1;	
			}	
		}	 
	
		return new String(bytes, 0, i, "Unicode");
	}
}
