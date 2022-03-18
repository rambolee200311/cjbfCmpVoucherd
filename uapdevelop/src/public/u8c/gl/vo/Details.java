package u8c.gl.vo;

import java.util.List;

public class Details {

    private String creditamount;
    private String creditquantity;
    private String debitamount;
    private String debitquantity;
    private int detailindex;
    private String excrate1;
    private String explanation;
    private String localcreditamount;
    private String localdebitamount;
    private String pk_accsubj;
    private String pk_currtype;
    private String price;
    private List<Ass> ass;
    private List<Cashflow> cashflow;
    private String checkstyle;
    private String checkdate;
    private String checkno; 
    
    public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getCheckno() {
		return checkno;
	}
	public void setCheckno(String checkno) {
		this.checkno = checkno;
	}
	public String getCheckstyle() {
		return checkstyle;
	}
	public void setCheckstyle(String checkstyle) {
		this.checkstyle = checkstyle;
	}
	public void setCreditamount(String creditamount) {
         this.creditamount = creditamount;
     }
     public String getCreditamount() {
         return creditamount;
     }

    public void setCreditquantity(String creditquantity) {
         this.creditquantity = creditquantity;
     }
     public String getCreditquantity() {
         return creditquantity;
     }

    public void setDebitamount(String debitamount) {
         this.debitamount = debitamount;
     }
     public String getDebitamount() {
         return debitamount;
     }

    public void setDebitquantity(String debitquantity) {
         this.debitquantity = debitquantity;
     }
     public String getDebitquantity() {
         return debitquantity;
     }

    public void setDetailindex(int detailindex) {
         this.detailindex = detailindex;
     }
     public int getDetailindex() {
         return detailindex;
     }

    public void setExcrate1(String excrate1) {
         this.excrate1 = excrate1;
     }
     public String getExcrate1() {
         return excrate1;
     }

    public void setExplanation(String explanation) {
         this.explanation = explanation;
     }
     public String getExplanation() {
         return explanation;
     }

    public void setLocalcreditamount(String localcreditamount) {
         this.localcreditamount = localcreditamount;
     }
     public String getLocalcreditamount() {
         return localcreditamount;
     }

    public void setLocaldebitamount(String localdebitamount) {
         this.localdebitamount = localdebitamount;
     }
     public String getLocaldebitamount() {
         return localdebitamount;
     }

    public void setPk_accsubj(String pk_accsubj) {
         this.pk_accsubj = pk_accsubj;
     }
     public String getPk_accsubj() {
         return pk_accsubj;
     }

    public void setPk_currtype(String pk_currtype) {
         this.pk_currtype = pk_currtype;
     }
     public String getPk_currtype() {
         return pk_currtype;
     }

    public void setPrice(String price) {
         this.price = price;
     }
     public String getPrice() {
         return price;
     }

    public void setAss(List<Ass> ass) {
         this.ass = ass;
     }
     public List<Ass> getAss() {
         return ass;
     }

    public void setCashflow(List<Cashflow> cashflow) {
         this.cashflow = cashflow;
     }
     public List<Cashflow> getCashflow() {
         return cashflow;
     }

}