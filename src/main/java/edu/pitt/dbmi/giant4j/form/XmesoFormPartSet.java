package edu.pitt.dbmi.giant4j.form;

import java.io.Serializable;

public class XmesoFormPartSet implements Serializable {

	private static final long serialVersionUID = 2131707872979379872L;

	private int numberOfParts = 10;
	private XmesoFormDataBean[] formData = new XmesoFormDataBean[numberOfParts];

	public static void main(String[] args) {
		XmesoFormPartSet formData = new XmesoFormPartSet();
		System.out.println(formData);
	}

	public XmesoFormPartSet() {
		for (int idx = 0; idx < formData.length; idx++) {
			formData[idx] = new XmesoFormDataBean();
		}
		resetFormData();
	}

	public void resetFormData() {
		for (int idx = 0; idx < formData.length; idx++) {
			formData[idx].resetFormData();
		}
	}
	
	public XmesoFormDataBean[] getFormData() {
		return formData;
	}

	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int idx = 0; idx < formData.length; idx++) {
			sb.append("\nPart " + idx + "\n");
			sb.append(formData[idx]);	
		}
		return sb.toString();
	}

}
