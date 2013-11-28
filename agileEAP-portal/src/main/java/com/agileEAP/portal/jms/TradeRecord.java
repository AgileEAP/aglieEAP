package com.agileEAP.portal.jms;

public class TradeRecord implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5358459143727155509L;
	private String addressIp;   //秤地址
	private String managerCode; //经营主体备案号  ---> 零售商编码
	private String saleDate;
	private String saleTime;    //saleDate+saleTime ----> 销售日期
	private String tranCode;    //追溯号码	--> 来源交易凭证号
	private String saleCode;    //下环节追溯号码   --> 零售凭证号
	private String weight;      //重量（g）
	private String goodsCode;   //商品代码   ----> 商品编码
	private String plu;         //PLU号
	private String batchCode;   //批次码
	private String price;       //销售金额（抹零前） ---> 单价（分）
	private String afterPrice;  //销售金额（抹零后）
	private String isSuccess;   //是否成功 (不需要)
	private String purchaseCode;//购买者编号
	private String purchaseName;//购买者名称
	public String getAddressIp() {
		return addressIp;
	}
	public void setAddressIp(String addressIp) {
		this.addressIp = addressIp;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getSaleCode() {
		return saleCode;
	}
	public void setSaleCode(String saleCode) {
		this.saleCode = saleCode;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getPlu() {
		return plu;
	}
	public void setPlu(String plu) {
		this.plu = plu;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAfterPrice() {
		return afterPrice;
	}
	public void setAfterPrice(String afterPrice) {
		this.afterPrice = afterPrice;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getPurchaseCode() {
		return purchaseCode;
	}
	public void setPurchaseCode(String purchaseCode) {
		this.purchaseCode = purchaseCode;
	}
	public String getPurchaseName() {
		return purchaseName;
	}
	public void setPurchaseName(String purchaseName) {
		this.purchaseName = purchaseName;
	}
}
