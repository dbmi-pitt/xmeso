

/* First created by JCasGen Wed Sep 07 12:38:01 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Wed Sep 07 12:38:01 EDT 2016
 * XML source: C:/Users/zhy19/workspace/xmeso/descriptor/edu/pitt/dbmi/xmeso/XmesoEngine.xml
 * @generated */
public class XmesoTumorForm extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(XmesoTumorForm.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected XmesoTumorForm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public XmesoTumorForm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public XmesoTumorForm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public XmesoTumorForm(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: currentPart

  /** getter for currentPart - gets currentPart
   * @generated
   * @return value of the feature 
   */
  public int getCurrentPart() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_currentPart == null)
      jcasType.jcas.throwFeatMissing("currentPart", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return jcasType.ll_cas.ll_getIntValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_currentPart);}
    
  /** setter for currentPart - sets currentPart 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCurrentPart(int v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_currentPart == null)
      jcasType.jcas.throwFeatMissing("currentPart", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    jcasType.ll_cas.ll_setIntValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_currentPart, v);}    
   
    
  //*--------------*
  //* Feature: tumorSite

  /** getter for tumorSite - gets tumorSite
   * @generated
   * @return value of the feature 
   */
  public String getTumorSite() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSite == null)
      jcasType.jcas.throwFeatMissing("tumorSite", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSite);}
    
  /** setter for tumorSite - sets tumorSite 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorSite(String v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSite == null)
      jcasType.jcas.throwFeatMissing("tumorSite", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSite, v);}    
   
    
  //*--------------*
  //* Feature: histopathologicalType

  /** getter for histopathologicalType - gets histopathologicalType
   * @generated
   * @return value of the feature 
   */
  public String getHistopathologicalType() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_histopathologicalType == null)
      jcasType.jcas.throwFeatMissing("histopathologicalType", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_histopathologicalType);}
    
  /** setter for histopathologicalType - sets histopathologicalType 
   * @generated
   * @param v value to set into the feature 
   */
  public void setHistopathologicalType(String v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_histopathologicalType == null)
      jcasType.jcas.throwFeatMissing("histopathologicalType", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_histopathologicalType, v);}    
   
    
  //*--------------*
  //* Feature: tumorConfiguration

  /** getter for tumorConfiguration - gets tumorConfiguration
   * @generated
   * @return value of the feature 
   */
  public String getTumorConfiguration() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorConfiguration == null)
      jcasType.jcas.throwFeatMissing("tumorConfiguration", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorConfiguration);}
    
  /** setter for tumorConfiguration - sets tumorConfiguration 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorConfiguration(String v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorConfiguration == null)
      jcasType.jcas.throwFeatMissing("tumorConfiguration", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorConfiguration, v);}    
   
    
  //*--------------*
  //* Feature: tumorDifferentiation

  /** getter for tumorDifferentiation - gets tumorDifferentiation
   * @generated
   * @return value of the feature 
   */
  public String getTumorDifferentiation() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorDifferentiation == null)
      jcasType.jcas.throwFeatMissing("tumorDifferentiation", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorDifferentiation);}
    
  /** setter for tumorDifferentiation - sets tumorDifferentiation 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorDifferentiation(String v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorDifferentiation == null)
      jcasType.jcas.throwFeatMissing("tumorDifferentiation", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorDifferentiation, v);}    
   
    
  //*--------------*
  //* Feature: sizeDimensionX

  /** getter for sizeDimensionX - gets sizeDimensionX
   * @generated
   * @return value of the feature 
   */
  public float getSizeDimensionX() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_sizeDimensionX == null)
      jcasType.jcas.throwFeatMissing("sizeDimensionX", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_sizeDimensionX);}
    
  /** setter for sizeDimensionX - sets sizeDimensionX 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSizeDimensionX(float v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_sizeDimensionX == null)
      jcasType.jcas.throwFeatMissing("sizeDimensionX", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_sizeDimensionX, v);}    
   
    
  //*--------------*
  //* Feature: sizeDimensionY

  /** getter for sizeDimensionY - gets sizeDimensionY
   * @generated
   * @return value of the feature 
   */
  public float getSizeDimensionY() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_sizeDimensionY == null)
      jcasType.jcas.throwFeatMissing("sizeDimensionY", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_sizeDimensionY);}
    
  /** setter for sizeDimensionY - sets sizeDimensionY 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSizeDimensionY(float v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_sizeDimensionY == null)
      jcasType.jcas.throwFeatMissing("sizeDimensionY", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_sizeDimensionY, v);}    
   
    
  //*--------------*
  //* Feature: sizeDimensionZ

  /** getter for sizeDimensionZ - gets sizeDimensionZ
   * @generated
   * @return value of the feature 
   */
  public float getSizeDimensionZ() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_sizeDimensionZ == null)
      jcasType.jcas.throwFeatMissing("sizeDimensionZ", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_sizeDimensionZ);}
    
  /** setter for sizeDimensionZ - sets sizeDimensionZ 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSizeDimensionZ(float v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_sizeDimensionZ == null)
      jcasType.jcas.throwFeatMissing("sizeDimensionZ", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_sizeDimensionZ, v);}    
   
    
  //*--------------*
  //* Feature: sizeDimensionMax

  /** getter for sizeDimensionMax - gets sizeDimensionMax
   * @generated
   * @return value of the feature 
   */
  public float getSizeDimensionMax() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_sizeDimensionMax == null)
      jcasType.jcas.throwFeatMissing("sizeDimensionMax", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_sizeDimensionMax);}
    
  /** setter for sizeDimensionMax - sets sizeDimensionMax 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSizeDimensionMax(float v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_sizeDimensionMax == null)
      jcasType.jcas.throwFeatMissing("sizeDimensionMax", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_sizeDimensionMax, v);}    
  }

    