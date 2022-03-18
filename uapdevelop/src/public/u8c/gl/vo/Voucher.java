package u8c.gl.vo;

import java.util.List;

public class Voucher {

    private boolean ISDIFFLAG;
    private String attachment;//附单据数
    private String explanation;//凭证摘要
    private String no;//凭证号
    private String pk_corp;//公司
    private String pk_glorgbook;//账簿
    private String pk_prepared;//制单人编码
    private String pk_vouchertype;//凭证类别简称
    private String prepareddate;//制单日期（格式示例：2021-04-27））
    private String voucherkind;  //凭证类型
    private String year;
    private String period;
    private String hasCashflowModified;//凭证是否手动修改了现金流量分析（如果传现金流量此属性传Y，如果没有现金流量分析，此属性不传或传N）    
    private String vouchertype_code;
    private String vouchertype_name;
    private String glorgbook_code;
    private String glorgbook_name;
    private String prepared_code;
    private String prepared_name;
    private Double totaldebit;
    private Double totalcredit;
    private String pk_system;
    private boolean signflag;
    private boolean discardflag;
    private String corp_code;
    private String corp_name;
    private String pk_voucher;
    
    public String getPk_voucher() {
		return pk_voucher;
	}
	public void setPk_voucher(String pk_voucher) {
		this.pk_voucher = pk_voucher;
	}
	public String getVouchertype_code() {
		return vouchertype_code;
	}
	public void setVouchertype_code(String vouchertype_code) {
		this.vouchertype_code = vouchertype_code;
	}
	public String getVouchertype_name() {
		return vouchertype_name;
	}
	public void setVouchertype_name(String vouchertype_name) {
		this.vouchertype_name = vouchertype_name;
	}
	public String getGlorgbook_code() {
		return glorgbook_code;
	}
	public void setGlorgbook_code(String glorgbook_code) {
		this.glorgbook_code = glorgbook_code;
	}
	public String getGlorgbook_name() {
		return glorgbook_name;
	}
	public void setGlorgbook_name(String glorgbook_name) {
		this.glorgbook_name = glorgbook_name;
	}
	public String getPrepared_code() {
		return prepared_code;
	}
	public void setPrepared_code(String prepared_code) {
		this.prepared_code = prepared_code;
	}
	public String getPrepared_name() {
		return prepared_name;
	}
	public void setPrepared_name(String prepared_name) {
		this.prepared_name = prepared_name;
	}
	public Double getTotaldebit() {
		return totaldebit;
	}
	public void setTotaldebit(Double totaldebit) {
		this.totaldebit = totaldebit;
	}
	public Double getTotalcredit() {
		return totalcredit;
	}
	public void setTotalcredit(Double totalcredit) {
		this.totalcredit = totalcredit;
	}
	public String getPk_system() {
		return pk_system;
	}
	public void setPk_system(String pk_system) {
		this.pk_system = pk_system;
	}
	public boolean isSignflag() {
		return signflag;
	}
	public void setSignflag(boolean signflag) {
		this.signflag = signflag;
	}
	public boolean isDiscardflag() {
		return discardflag;
	}
	public void setDiscardflag(boolean discardflag) {
		this.discardflag = discardflag;
	}
	public String getCorp_code() {
		return corp_code;
	}
	public void setCorp_code(String corp_code) {
		this.corp_code = corp_code;
	}
	public String getCorp_name() {
		return corp_name;
	}
	public void setCorp_name(String corp_name) {
		this.corp_name = corp_name;
	}
	public String getHasCashflowModified() {
		return hasCashflowModified;
	}
	public void setHasCashflowModified(String hasCashflowModified) {
		this.hasCashflowModified = hasCashflowModified;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	private List<Details> details;
    public List<Details> getDetails() {
		return details;
	}
	public void setDetails(List<Details> details) {
		this.details = details;
	}
	public void setISDIFFLAG(boolean ISDIFFLAG) {
         this.ISDIFFLAG = ISDIFFLAG;
     }
     public boolean getISDIFFLAG() {
         return ISDIFFLAG;
     }

    public void setAttachment(String attachment) {
         this.attachment = attachment;
     }
     public String getAttachment() {
         return attachment;
     }

    public void setExplanation(String explanation) {
         this.explanation = explanation;
     }
     public String getExplanation() {
         return explanation;
     }

    public void setNo(String no) {
         this.no = no;
     }
     public String getNo() {
         return no;
     }

    public void setPk_corp(String pk_corp) {
         this.pk_corp = pk_corp;
     }
     public String getPk_corp() {
         return pk_corp;
     }

    public void setPk_glorgbook(String pk_glorgbook) {
         this.pk_glorgbook = pk_glorgbook;
     }
     public String getPk_glorgbook() {
         return pk_glorgbook;
     }

    public void setPk_prepared(String pk_prepared) {
         this.pk_prepared = pk_prepared;
     }
     public String getPk_prepared() {
         return pk_prepared;
     }

    public void setPk_vouchertype(String pk_vouchertype) {
         this.pk_vouchertype = pk_vouchertype;
     }
     public String getPk_vouchertype() {
         return pk_vouchertype;
     }

    public void setPrepareddate(String prepareddate) {
         this.prepareddate = prepareddate;
     }
     public String getPrepareddate() {
         return prepareddate;
     }

    public void setVoucherkind(String voucherkind) {
         this.voucherkind = voucherkind;
     }
     public String getVoucherkind() {
         return voucherkind;
     }

}