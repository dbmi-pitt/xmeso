package edu.pitt.dbmi.giant4j.form;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class FormDataBean implements Serializable {

	private static final long serialVersionUID = 2131707872979379872L;

	private String primaryDisease;
	private String metastaticSiteOne;
	private String metastaticSiteTwo;
	private String metastaticSiteThree;
	private String size;
	private String tnmModifier;
	private String tnmTumor;
	private String tnmNode;
	private String tnmMetastasis;
	private String stage;
	private String erStatus;
	private String prStatus;
	private String her2NeuStatus;

	public static void main(String[] args) {
		FormDataBean formData = new FormDataBean();
		formData.setPrimaryDisease("DCIS");
		formData.setMetastaticSiteOne("Lung");
		formData.setMetastaticSiteTwo("Kidney");
		formData.setMetastaticSiteThree("Liver");
		formData.setSize("Tumor_Less_Than_or_Equal_to_2_0_Centimeters");
		formData.setTnmModifier("p_modifier");
		formData.setTnmTumor("Tis_Stage_Finding");
		formData.setTnmNode("N0_Stage_Finding");
		formData.setTnmMetastasis("M0_Stage_Finding");
		formData.setStage("Stage_0_Breast_Cancer");
		formData.setErStatus("Positive");
		formData.setPrStatus("Positive");
		formData.setHer2NeuStatus("Indeterminate");
		System.out.println(formData);
	}

	public FormDataBean() {
		resetFormData();
	}

	public void resetFormData() {
		setPrimaryDisease("NA");
		setMetastaticSiteOne("NA");
		setMetastaticSiteTwo("NA");
		setMetastaticSiteThree("NA");
		setSize("Tumor_Greater_Than_or_Equal_to_2_1_Centimeters");
		setTnmModifier("DCIS");
		setTnmTumor("Any_T");
		setTnmNode("N0_Stage_Finding");
		setTnmMetastasis("M0_Stage_Finding");
		setStage("Stage_0_Breast_Cancer");
		setErStatus("Equivocal");
		setPrStatus("Equivocal");
		setHer2NeuStatus("Equivocal");
	}

	public String getPrimaryDisease() {
		return primaryDisease;
	}

	public void setPrimaryDisease(String primaryDisease) {
		this.primaryDisease = primaryDisease;
	}

	public String getMetastaticSiteOne() {
		return metastaticSiteOne;
	}

	public void setMetastaticSiteOne(String metastaticSiteOne) {
		this.metastaticSiteOne = metastaticSiteOne;
	}

	public String getMetastaticSiteTwo() {
		return metastaticSiteTwo;
	}

	public void setMetastaticSiteTwo(String metastaticSiteTwo) {
		this.metastaticSiteTwo = metastaticSiteTwo;
	}

	public String getMetastaticSiteThree() {
		return metastaticSiteThree;
	}

	public void setMetastaticSiteThree(String metastaticSiteThree) {
		this.metastaticSiteThree = metastaticSiteThree;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getTnmModifier() {
		return tnmModifier;
	}

	public void setTnmModifier(String tnmModifier) {
		this.tnmModifier = tnmModifier;
	}

	public String getTnmTumor() {
		return tnmTumor;
	}

	public void setTnmTumor(String tnmTumor) {
		this.tnmTumor = tnmTumor;
	}

	public String getTnmNode() {
		return tnmNode;
	}

	public void setTnmNode(String tnmNode) {
		this.tnmNode = tnmNode;
	}

	public String getTnmMetastasis() {
		return tnmMetastasis;
	}

	public void setTnmMetastasis(String tnmMetastasis) {
		this.tnmMetastasis = tnmMetastasis;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getErStatus() {
		return erStatus;
	}

	public void setErStatus(String erStatus) {
		this.erStatus = erStatus;
	}

	public String getPrStatus() {
		return prStatus;
	}

	public void setPrStatus(String prStatus) {
		this.prStatus = prStatus;
	}

	public String getHer2NeuStatus() {
		return her2NeuStatus;
	}

	public void setHer2NeuStatus(String her2NeuStatus) {
		this.her2NeuStatus = her2NeuStatus;
	}

	@SuppressWarnings("unchecked")
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		try {
			Map<String, String> beanMap = BeanUtils.describe(this);
			for (String key : beanMap.keySet()) {
				String value = beanMap.get(key);
				sb.append(key + " ==> " + value + "\n");
			}

		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
