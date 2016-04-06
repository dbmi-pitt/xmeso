package edu.pitt.dbmi.giant4j.form;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class XmesoFormDataBean implements Serializable {

	private static final long serialVersionUID = 2131707872979379872L;

	private String surgicalProcedure;
	private String histologicType;
	private String tumorSite;
	private String tumorConfiguration;
	private String tumorDifferentiation;
	private String sizeX;
	private String sizeY;
	private String sizeZ;
	private String sizeMax;

	public static void main(String[] args) {
		XmesoFormDataBean formData = new XmesoFormDataBean();
		formData.setSurgicalProcedure("PROC:UNKNOWN");
		formData.setHistologicType("HIST_TYPE:UNKNOWN");
		formData.setTumorSite("TUMOR_SITE:UNKNOWN");
		formData.setTumorConfiguration("TUMOR_CONFIG:UNKNOWN");
		formData.setTumorDifferentiation("ANA|TUMOR_DIFF:NA");
		formData.setSizeX("-1.0");
		formData.setSizeY("-1.0");
		formData.setSizeZ("-1.0");
		formData.setSizeMax("-1.0");
		System.out.println(formData);
	}

	public XmesoFormDataBean() {
		resetFormData();
	}

	public void resetFormData() {
		setSurgicalProcedure("PROC:UNKNOWN");
		setHistologicType("HIST_TYPE:UNKNOWN");
		setTumorSite("TUMOR_SITE:UNKNOWN");
		setTumorConfiguration("TUMOR_CONFIG:UNKNOWN");
		setTumorDifferentiation("ANA|TUMOR_DIFF:NA");
		setSizeX("-1.0");
		setSizeY("-1.0");
		setSizeZ("-1.0");
		setSizeMax("-1.0");
	}

	public String getSurgicalProcedure() {
		return surgicalProcedure;
	}

	public void setSurgicalProcedure(String surgicalProcedure) {
		this.surgicalProcedure = surgicalProcedure;
	}

	public String getHistologicType() {
		return histologicType;
	}

	public void setHistologicType(String histologicType) {
		this.histologicType = histologicType;
	}

	public String getTumorSite() {
		return tumorSite;
	}

	public void setTumorSite(String tumorSite) {
		this.tumorSite = tumorSite;
	}

	public String getTumorConfiguration() {
		return tumorConfiguration;
	}

	public void setTumorConfiguration(String tumorConfiguration) {
		this.tumorConfiguration = tumorConfiguration;
	}

	public String getTumorDifferentiation() {
		return tumorDifferentiation;
	}

	public void setTumorDifferentiation(String tumorDifferentiation) {
		this.tumorDifferentiation = tumorDifferentiation;
	}

	public String getSizeX() {
		return sizeX;
	}

	public void setSizeX(String sizeX) {
		this.sizeX = sizeX;
	}

	public String getSizeY() {
		return sizeY;
	}

	public void setSizeY(String sizeY) {
		this.sizeY = sizeY;
	}

	public String getSizeZ() {
		return sizeZ;
	}

	public void setSizeZ(String sizeZ) {
		this.sizeZ = sizeZ;
	}

	public String getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(String sizeMax) {
		this.sizeMax = sizeMax;
	}

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
