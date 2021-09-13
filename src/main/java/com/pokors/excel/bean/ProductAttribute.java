package com.pokors.excel.bean;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pokors.excel.annotation.ExcelColumn;

/**
 * 产品属性
 *
 * @author liuhp
 * @since 2021/9/10 17:30
 */
public class ProductAttribute implements Serializable {

    private static final long serialVersionUID = 8858812425876912940L;

    /**
     * 大类
     */
    @ExcelColumn(header = "大类")
    private String bigCategory;
    /**
     * productType
     */
    @ExcelColumn(header = "product_type")
    private String productType;
    /**
     * 一级属性
     */
    @ExcelColumn(header = "一级属性分类")
    private String firstLevelAttribute;
    /**
     * 二级属性
     */
    @ExcelColumn(header = "二级属性分类")
    private String secondAttribute;
    /**
     * 属性类型：重要属性、首选信息、可选信息
     */
    @ExcelColumn(header = "属性类型")
    private String attributeType;
    /**
     * 属性名
     */
    @ExcelColumn(header = "属性名")
    private String attributeName;
    /**
     * 属性值类型：填写框、填写框+选择枚举值、选择枚举值
     */
    @ExcelColumn(header = "属性值类型")
    private String attributeValueType;
    /**
     * US枚举值
     */
    @ExcelColumn(header = "US枚举值")
    private String usEnumValue;
    /**
     * CA枚举值
     */
    @ExcelColumn(header = "CA枚举值")
    private String caEnumValue;
    /**
     * MX枚举值
     */
    @ExcelColumn(header = "MX枚举值")
    private String mxEnumValue;
    /**
     * IT枚举值
     */
    @ExcelColumn(header = "IT枚举值")
    private String itEnumValue;
    /**
     * FR枚举值
     */
    @ExcelColumn(header = "FR枚举值")
    private String frEnumValue;
    /**
     * DE枚举值
     */
    @ExcelColumn(header = "DE枚举值")
    private String deEnumValue;
    /**
     * UK枚举值
     */
    @ExcelColumn(header = "UK枚举值")
    private String ukEnumValue;
    /**
     * ES枚举值
     */
    @ExcelColumn(header = "ES枚举值")
    private String esEnumValue;
    /**
     * JP枚举值
     */
    @ExcelColumn(header = "JP枚举值")
    private String jpEnumValue;
    /**
     * 字段说明
     */
    @ExcelColumn(header = "字段说明")
    private String attributeDescription;

    public String getBigCategory() {
        return bigCategory;
    }

    public void setBigCategory(String bigCategory) {
        this.bigCategory = bigCategory;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getFirstLevelAttribute() {
        return firstLevelAttribute;
    }

    public void setFirstLevelAttribute(String firstLevelAttribute) {
        this.firstLevelAttribute = firstLevelAttribute;
    }

    public String getSecondAttribute() {
        return secondAttribute;
    }

    public void setSecondAttribute(String secondAttribute) {
        this.secondAttribute = secondAttribute;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValueType() {
        return attributeValueType;
    }

    public void setAttributeValueType(String attributeValueType) {
        this.attributeValueType = attributeValueType;
    }

    public String getUsEnumValue() {
        return usEnumValue;
    }

    public void setUsEnumValue(String usEnumValue) {
        this.usEnumValue = usEnumValue;
    }

    public String getCaEnumValue() {
        return caEnumValue;
    }

    public void setCaEnumValue(String caEnumValue) {
        this.caEnumValue = caEnumValue;
    }

    public String getMxEnumValue() {
        return mxEnumValue;
    }

    public void setMxEnumValue(String mxEnumValue) {
        this.mxEnumValue = mxEnumValue;
    }

    public String getItEnumValue() {
        return itEnumValue;
    }

    public void setItEnumValue(String itEnumValue) {
        this.itEnumValue = itEnumValue;
    }

    public String getFrEnumValue() {
        return frEnumValue;
    }

    public void setFrEnumValue(String frEnumValue) {
        this.frEnumValue = frEnumValue;
    }

    public String getDeEnumValue() {
        return deEnumValue;
    }

    public void setDeEnumValue(String deEnumValue) {
        this.deEnumValue = deEnumValue;
    }

    public String getUkEnumValue() {
        return ukEnumValue;
    }

    public void setUkEnumValue(String ukEnumValue) {
        this.ukEnumValue = ukEnumValue;
    }

    public String getEsEnumValue() {
        return esEnumValue;
    }

    public void setEsEnumValue(String esEnumValue) {
        this.esEnumValue = esEnumValue;
    }

    public String getJpEnumValue() {
        return jpEnumValue;
    }

    public void setJpEnumValue(String jpEnumValue) {
        this.jpEnumValue = jpEnumValue;
    }

    public String getAttributeDescription() {
        return attributeDescription;
    }

    public void setAttributeDescription(String attributeDescription) {
        this.attributeDescription = attributeDescription;
    }

    @Override
    public String toString() {
        return "ProductAttribute{" + "bigCategory='" + bigCategory + '\'' + ", productType='" + productType + '\''
            + ", firstLevelAttribute='" + firstLevelAttribute + '\'' + ", secondAttribute='" + secondAttribute + '\''
            + ", attributeType='" + attributeType + '\'' + ", attributeName='" + attributeName + '\''
            + ", attributeValueType='" + attributeValueType + '\'' + ", usEnumValue='" + usEnumValue + '\''
            + ", caEnumValue='" + caEnumValue + '\'' + ", mxEnumValue='" + mxEnumValue + '\'' + ", itEnumValue='"
            + itEnumValue + '\'' + ", frEnumValue='" + frEnumValue + '\'' + ", deEnumValue='" + deEnumValue + '\''
            + ", ukEnumValue='" + ukEnumValue + '\'' + ", esEnumValue='" + esEnumValue + '\'' + ", jpEnumValue='"
            + jpEnumValue + '\'' + ", attributeDescription='" + attributeDescription + '\'' + '}';
    }
}