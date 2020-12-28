package com.neefull.fsp.web.sms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: chengchengchu
 * @Date: 2020/11/24  10:30
 */

@TableName(value = "sms_detail")
public class Detail implements Serializable {

    private static final long serialVersionUID = 4285670559647893856L;

    /**
     *  主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     *  药厂DO号
     */
    @TableField(value = "roche_delivery")
    private String rocheDelivery;
    /**
     *  DN号
     */
    @TableField(value = "delivery")
    private String delivery;
    /**
     *  DN行项目号
     */
    @TableField(value = "delivery_item")
    private String deliveryItem;
    /**
     *  药厂DO行项目号
     */
    @TableField(value = "roche_delivery_item")
    private String rocheDeliveryItem;
    /**
     *  药厂DO行数
     */
    @TableField(value = "roche_delivery_item_code")
    private String rocheDeliveryItemCode;
    /**
     *  物料代码
     */
    @TableField(value = "material")
    private String material;
    /**
     *  药厂物料代码
     */
    @TableField(value = "roche_material")
    private String rocheMaterial;
    /**
     *  物料描述
     */
    @TableField(value = "material_description")
    private String materialDescription;
    /**
     *  物料英文描述
     */
    @TableField(value = "material_description_en")
    private String materialDescriptionEn;
    /**
     *  药厂物料描述
     */
    @TableField(value = "roche_material_description")
    private String rocheMaterialDescription;
    /**
     *  plant
     */
    @TableField(value = "plant")
    private String plant;
    /**
     *  sap批号
     */
    @TableField(value = "batch")
    private String batch;
    /**
     *  药厂批次号
     */
    @TableField(value = "roche_batch")
    private String rocheBatch;
    /**
     *  序列号
     */
    @TableField(value = "serial_number")
    private String serialNumber;
    /**
     *  数量
     */
    @TableField(value = "quantity")
    private String quantity;
    /**
     *  已扫数量
     */
    @TableField(value = "scan_quantity")
    private String scanQuantity;

    /**
     *  单位
     */
    @TableField(value = "uom")
    private String uom;
    /**
     *  药厂单位
     */
    @TableField(value = "roche_uom")
    private String rocheUom;
    /**
     *  有效期
     */
    @TableField(value = "expiry_date")
    private String expiryDate;
    /**
     *  是否批次管理
     */
    @TableField(value = "if_batch_management")
    private String ifBatchManagement;
    /**
     *  是否序列号管理
     */
    @TableField(value = "if_serial_number_management")
    private String ifSerialNumberManagement;
    /**
     *  是否有效期管理
     */
    @TableField(value = "if_expiry_date_management")
    private String ifExpiryDateManagement;
    /**
     *  存储条件
     */
    @TableField(value = "save_mode")
    private String saveMode;
    /**
     *  存储条件描述
     */
    @TableField(value = "save_mode_description")
    private String saveModeDescription;

    @TableField(value = "guid")
    private String guid;

    @TableField(value = "dummy_code")
    private String dummyCode;

    @TableField(value = "dummy_desc")
    private String dummyDesc;

    @TableField(value = "dummy_is_batch_management")
    private String dummyIsBatchManagement;

    @TableField(value = "dummy_is_serial_number_management")
    private String dummyIsSerialNumberManagement;

    @TableField(value = "dummy_is_expriy_date_management")
    private String dummyIsExpriyDateManagement;

    @TableField(value = "dummy_roche_batch")
    private String dummyRocheBatch;

    @TableField(value = "dummy_serial_number")
    private String dummySerialNumber;

    @TableField(value = "dummy_expiry_date")
    private String dummyExpiryDate;

    @TableField(value = "must_scan_mode")
    private String mustScanMode;
    /**
     *  错误信息
     */
    @TableField(value = "error_message")
    private String errorMessage;
    /**
     *  状态   1有问题
     */
    @TableField(value = "status")
    private String status;
    /**
     *  创建日期
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
    /**
     *  更新日期
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRocheDelivery() {
        return rocheDelivery;
    }

    public void setRocheDelivery(String rocheDelivery) {
        this.rocheDelivery = rocheDelivery;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getDeliveryItem() {
        return deliveryItem;
    }

    public void setDeliveryItem(String deliveryItem) {
        this.deliveryItem = deliveryItem;
    }

    public String getRocheDeliveryItem() {
        return rocheDeliveryItem;
    }

    public void setRocheDeliveryItem(String rocheDeliveryItem) {
        this.rocheDeliveryItem = rocheDeliveryItem;
    }

    public String getRocheDeliveryItemCode() {
        return rocheDeliveryItemCode;
    }

    public void setRocheDeliveryItemCode(String rocheDeliveryItemCode) {
        this.rocheDeliveryItemCode = rocheDeliveryItemCode;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getRocheMaterial() {
        return rocheMaterial;
    }

    public void setRocheMaterial(String rocheMaterial) {
        this.rocheMaterial = rocheMaterial;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getMaterialDescriptionEn() {
        return materialDescriptionEn;
    }

    public void setMaterialDescriptionEn(String materialDescriptionEn) {
        this.materialDescriptionEn = materialDescriptionEn;
    }

    public String getRocheMaterialDescription() {
        return rocheMaterialDescription;
    }

    public void setRocheMaterialDescription(String rocheMaterialDescription) {
        this.rocheMaterialDescription = rocheMaterialDescription;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getRocheBatch() {
        return rocheBatch;
    }

    public void setRocheBatch(String rocheBatch) {
        this.rocheBatch = rocheBatch;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getScanQuantity() {
        return scanQuantity;
    }

    public void setScanQuantity(String scanQuantity) {
        this.scanQuantity = scanQuantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getRocheUom() {
        return rocheUom;
    }

    public void setRocheUom(String rocheUom) {
        this.rocheUom = rocheUom;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getIfBatchManagement() {
        return ifBatchManagement;
    }

    public void setIfBatchManagement(String ifBatchManagement) {
        this.ifBatchManagement = ifBatchManagement;
    }

    public String getIfSerialNumberManagement() {
        return ifSerialNumberManagement;
    }

    public void setIfSerialNumberManagement(String ifSerialNumberManagement) {
        this.ifSerialNumberManagement = ifSerialNumberManagement;
    }

    public String getIfExpiryDateManagement() {
        return ifExpiryDateManagement;
    }

    public void setIfExpiryDateManagement(String ifExpiryDateManagement) {
        this.ifExpiryDateManagement = ifExpiryDateManagement;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getSaveModeDescription() {
        return saveModeDescription;
    }

    public void setSaveModeDescription(String saveModeDescription) {
        this.saveModeDescription = saveModeDescription;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDummyCode() {
        return dummyCode;
    }

    public void setDummyCode(String dummyCode) {
        this.dummyCode = dummyCode;
    }

    public String getDummyDesc() {
        return dummyDesc;
    }

    public void setDummyDesc(String dummyDesc) {
        this.dummyDesc = dummyDesc;
    }

    public String getDummyIsBatchManagement() {
        return dummyIsBatchManagement;
    }

    public void setDummyIsBatchManagement(String dummyIsBatchManagement) {
        this.dummyIsBatchManagement = dummyIsBatchManagement;
    }

    public String getDummyIsSerialNumberManagement() {
        return dummyIsSerialNumberManagement;
    }

    public void setDummyIsSerialNumberManagement(String dummyIsSerialNumberManagement) {
        this.dummyIsSerialNumberManagement = dummyIsSerialNumberManagement;
    }

    public String getDummyIsExpriyDateManagement() {
        return dummyIsExpriyDateManagement;
    }

    public void setDummyIsExpriyDateManagement(String dummyIsExpriyDateManagement) {
        this.dummyIsExpriyDateManagement = dummyIsExpriyDateManagement;
    }

    public String getDummyRocheBatch() {
        return dummyRocheBatch;
    }

    public void setDummyRocheBatch(String dummyRocheBatch) {
        this.dummyRocheBatch = dummyRocheBatch;
    }

    public String getDummySerialNumber() {
        return dummySerialNumber;
    }

    public void setDummySerialNumber(String dummySerialNumber) {
        this.dummySerialNumber = dummySerialNumber;
    }

    public String getDummyExpiryDate() {
        return dummyExpiryDate;
    }

    public void setDummyExpiryDate(String dummyExpiryDate) {
        this.dummyExpiryDate = dummyExpiryDate;
    }

    public String getMustScanMode() {
        return mustScanMode;
    }

    public void setMustScanMode(String mustScanMode) {
        this.mustScanMode = mustScanMode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Detail detail = (Detail) object;
        return Objects.equals(material, detail.material) &&
                Objects.equals(batch, detail.batch) &&
                Objects.equals(quantity, detail.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, batch, quantity);
    }
}
