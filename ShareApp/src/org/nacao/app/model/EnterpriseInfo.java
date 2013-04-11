package org.nacao.app.model;

public class EnterpriseInfo  implements java.io.Serializable {
	
  	private String codeId;
    private String codeCn;
    private String industryId;
    private String addressname;
    private String workUnit;
    private String finishdate;
    
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getCodeCn() {
		return codeCn;
	}
	public void setCodeCn(String codeCn) {
		this.codeCn = codeCn;
	}
	public String getIndustryId() {
		return industryId;
	}
	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}
	public String getAddressname() {
		return addressname;
	}
	public void setAddressname(String addressname) {
		this.addressname = addressname;
	}
	public String getFinishdate() {
		return finishdate;
	}
	public void setFinishdate(String finishdate) {
		this.finishdate = finishdate;
	}
	
}