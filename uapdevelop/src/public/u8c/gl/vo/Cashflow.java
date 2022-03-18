package u8c.gl.vo;
public class Cashflow {

    private String pk_cashflow;
    private String pk_currtype;
    private String money;
    public void setPk_cashflow(String pk_cashflow) {
         this.pk_cashflow = pk_cashflow;
     }
     public String getPk_cashflow() {
         return pk_cashflow;
     }

    public void setPk_currtype(String pk_currtype) {
         this.pk_currtype = pk_currtype;
     }
     public String getPk_currtype() {
         return pk_currtype;
     }

    public void setMoney(String money) {
         this.money = money;
     }
     public String getMoney() {
         return money;
     }

}