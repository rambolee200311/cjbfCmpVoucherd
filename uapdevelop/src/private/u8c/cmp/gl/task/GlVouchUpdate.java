package u8c.cmp.gl.task;

import java.util.ArrayList;
import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.BeanProcessor;
import nc.vo.bd.b02.AccsubjVO;
import nc.vo.gl.pubvoucher.DetailVO;

public class GlVouchUpdate {
	/*
	 * 更正分帐套会计科目
	 */
	public static void voucherSubjUpdate(String pk_voucher,BaseDAO dao) throws DAOException{
		String sql="select pk_glorgbook,pk_accsubj,pk_detail from gl_detail where pk_voucher='"+pk_voucher+"'";
		ArrayList<DetailVO> des =(ArrayList<DetailVO>) dao.executeQuery(sql, new BeanListProcessor(DetailVO.class));
		for(DetailVO de:des){
			sql="select pk_glorgbook,pk_accsubj,subjcode from bd_accsubj where pk_accsubj='"+de.getPk_accsubj()+"'";
			AccsubjVO accsubjFrom=(AccsubjVO)dao.executeQuery(sql,new BeanProcessor(AccsubjVO.class));
			if (!accsubjFrom.getPk_glorgbook().equals(de.getPk_glorgbook())){
				sql="select pk_glorgbook,pk_accsubj,subjcode from bd_accsubj"
						+" where subjcode='"+accsubjFrom.getSubjcode()+"' and pk_glorgbook='"+de.getPk_glorgbook()+"'";
				AccsubjVO accsubjTo=(AccsubjVO)dao.executeQuery(sql,new BeanProcessor(AccsubjVO.class));
				if (accsubjTo!=null){
					if (accsubjTo.getPk_accsubj()!=null){
						sql="update gl_detail set pk_accsubj='"+accsubjTo.getPk_accsubj()+"' where pk_detail='"+de.getPk_detail()+"'";
						dao.executeUpdate(sql);
					}
				}
			}
		}
	}
}
