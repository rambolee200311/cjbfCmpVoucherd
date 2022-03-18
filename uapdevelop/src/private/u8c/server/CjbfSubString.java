package u8c.server;

public class CjbfSubString {
	public static String getSubString(String str, int length) throws Exception {
		int i;	
		int n;	
		byte[] bytes = str.getBytes("Unicode");      //使用Unicode字符集将字符串编码成byte序列	
		i = 2;      //bytes的前两个字节是标志位，bytes[0] = -2, bytes[1] = -1, 故从第二位开始	
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
	
		//去掉半个汉字
	
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
