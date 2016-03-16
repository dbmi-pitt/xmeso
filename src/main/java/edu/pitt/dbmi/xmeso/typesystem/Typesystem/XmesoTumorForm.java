

/* First created by JCasGen Tue Mar 01 10:05:29 EST 2016 */
package edu.pitt.dbmi.xmeso.typesystem.Typesystem;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Type defined in edu.pitt.dbmi.xmeso.typesystem.Typesystem
 * Updated by JCasGen Tue Mar 01 10:05:29 EST 2016
 * XML source: C:/ws/ws-xmeso/xmeso/resources/edu/pitt/dbmi/xmeso/typesystem/typesystemTypeSystem.xml
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
  //* Feature: partNumber

  /** getter for partNumber - gets partNumber
   * @generated
   * @return value of the feature 
   */
  public int getPartNumber() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return jcasType.ll_cas.ll_getIntValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_partNumber);}
    
  /** setter for partNumber - sets partNumber 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPartNumber(int v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    jcasType.ll_cas.ll_setIntValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_partNumber, v);}    
   
    
  //*--------------*
  //* Feature: tumorSiteCode

  /** getter for tumorSiteCode - gets tumorSiteCode
   * @generated
   * @return value of the feature 
   */
  public String getTumorSiteCode() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSiteCode == null)
      jcasType.jcas.throwFeatMissing("tumorSiteCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSiteCode);}
    
  /** setter for tumorSiteCode - sets tumorSiteCode 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorSiteCode(String v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSiteCode == null)
      jcasType.jcas.throwFeatMissing("tumorSiteCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSiteCode, v);}    
   
    
  //*--------------*
  //* Feature: tumorConfigurationCode

  /** getter for tumorConfigurationCode - gets tumorConfigurationCode
   * @generated
   * @return value of the feature 
   */
  public String getTumorConfigurationCode() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorConfigurationCode == null)
      jcasType.jcas.throwFeatMissing("tumorConfigurationCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorConfigurationCode);}
    
  /** setter for tumorConfigurationCode - sets tumorConfigurationCode 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorConfigurationCode(String v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorConfigurationCode == null)
      jcasType.jcas.throwFeatMissing("tumorConfigurationCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorConfigurationCode, v);}    
   
    
  //*--------------*
  //* Feature: tumorDifferentiationCode

  /** getter for tumorDifferentiationCode - gets tumorDifferentiationCode
   * @generated
   * @return value of the feature 
   */
  public String getTumorDifferentiationCode() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorDifferentiationCode == null)
      jcasType.jcas.throwFeatMissing("tumorDifferentiationCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorDifferentiationCode);}
    
  /** setter for tumorDifferentiationCode - sets tumorDifferentiationCode 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorDifferentiationCode(String v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorDifferentiationCode == null)
      jcasType.jcas.throwFeatMissing("tumorDifferentiationCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorDifferentiationCode, v);}    
   
    
  //*--------------*
  //* Feature: tumorSizeGreatestDimension

  /** getter for tumorSizeGreatestDimension - gets tumorSizeGreatestDimension
   * @generated
   * @return value of the feature 
   */
  public float getTumorSizeGreatestDimension() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSizeGreatestDimension == null)
      jcasType.jcas.throwFeatMissing("tumorSizeGreatestDimension", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSizeGreatestDimension);}
    
  /** setter for tumorSizeGreatestDimension - sets tumorSizeGreatestDimension 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorSizeGreatestDimension(float v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSizeGreatestDimension == null)
      jcasType.jcas.throwFeatMissing("tumorSizeGreatestDimension", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSizeGreatestDimension, v);}    
   
    
  //*--------------*
  //* Feature: tumorSizeOne

  /** getter for tumorSizeOne - gets tumorSizeOne
   * @generated
   * @return value of the feature 
   */
  public float getTumorSizeOne() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSizeOne == null)
      jcasType.jcas.throwFeatMissing("tumorSizeOne", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSizeOne);}
    
  /** setter for tumorSizeOne - sets tumorSizeOne 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorSizeOne(float v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSizeOne == null)
      jcasType.jcas.throwFeatMissing("tumorSizeOne", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSizeOne, v);}    
   
    
  //*--------------*
  //* Feature: tumorSizeTwo

  /** getter for tumorSizeTwo - gets tumorSizeTwo
   * @generated
   * @return value of the feature 
   */
  public float getTumorSizeTwo() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSizeTwo == null)
      jcasType.jcas.throwFeatMissing("tumorSizeTwo", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSizeTwo);}
    
  /** setter for tumorSizeTwo - sets tumorSizeTwo 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorSizeTwo(float v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSizeTwo == null)
      jcasType.jcas.throwFeatMissing("tumorSizeTwo", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSizeTwo, v);}    
   
    
  //*--------------*
  //* Feature: tumorSizeThree

  /** getter for tumorSizeThree - gets tumorSizeThree
   * @generated
   * @return value of the feature 
   */
  public float getTumorSizeThree() {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSizeThree == null)
      jcasType.jcas.throwFeatMissing("tumorSizeThree", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSizeThree);}
    
  /** setter for tumorSizeThree - sets tumorSizeThree 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorSizeThree(float v) {
    if (XmesoTumorForm_Type.featOkTst && ((XmesoTumorForm_Type)jcasType).casFeat_tumorSizeThree == null)
      jcasType.jcas.throwFeatMissing("tumorSizeThree", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoTumorForm_Type)jcasType).casFeatCode_tumorSizeThree, v);}    
  }

    