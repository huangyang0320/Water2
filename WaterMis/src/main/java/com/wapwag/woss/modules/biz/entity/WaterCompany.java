package com.wapwag.woss.modules.biz.entity;

import org.hibernate.validator.constraints.Length;

import com.wapwag.woss.common.persistence.DataEntity;

/**
 * 水司管理 
 * @author zhaom
 *
 */
public class WaterCompany extends DataEntity<WaterCompany>{
  
	private static final long serialVersionUID = -7676271140595010867L;

	//private String id;--父类 公共抽取

    private String companyName;

    private String parentId;

    private String regionalId;

    private String pinyin;

    private String memo;

    //private Date createDate;

    private String creator;

    //private Date updateDate;

    private String updator;

    //private String delFlag;
    
    private String type; //节点类型  1水司  2水务所  3 营业所

    @Length(min = 0, max = 50, message = "水司名称长度必须介于 0 和 50 之间")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getRegionalId() {
        return regionalId;
    }

    public void setRegionalId(String regionalId) {
        this.regionalId = regionalId == null ? null : regionalId.trim();
    }

    @Length(min = 0, max = 50, message = "拼音字母长度必须介于 0 和 50 之间")
    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin == null ? null : pinyin.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }
    
    @Length(min = 0, max = 50, message = "节点类型长度必须介于 0 和 50 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}