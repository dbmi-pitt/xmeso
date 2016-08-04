

/* First created by JCasGen Mon Aug 01 15:50:48 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Thu Aug 04 16:21:38 EDT 2016
 * XML source: C:/Users/zhy19/workspace/xmeso/descriptor/edu/pitt/dbmi/xmeso/XmesoEngine.xml
 * @generated */
public class Part extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Part.class);
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
  protected Part() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Part(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Part(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Part(JCas jcas, int begin, int end) {
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
  //* Feature: sectionName

  /** getter for sectionName - gets sectionName
   * @generated
   * @return value of the feature 
   */
  public String getSectionName() {
    if (Part_Type.featOkTst && ((Part_Type)jcasType).casFeat_sectionName == null)
      jcasType.jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.Part");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Part_Type)jcasType).casFeatCode_sectionName);}
    
  /** setter for sectionName - sets sectionName 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSectionName(String v) {
    if (Part_Type.featOkTst && ((Part_Type)jcasType).casFeat_sectionName == null)
      jcasType.jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.Part");
    jcasType.ll_cas.ll_setStringValue(addr, ((Part_Type)jcasType).casFeatCode_sectionName, v);}    
   
    
  //*--------------*
  //* Feature: sectionLevel

  /** getter for sectionLevel - gets sectionLevel
   * @generated
   * @return value of the feature 
   */
  public int getSectionLevel() {
    if (Part_Type.featOkTst && ((Part_Type)jcasType).casFeat_sectionLevel == null)
      jcasType.jcas.throwFeatMissing("sectionLevel", "edu.pitt.dbmi.xmeso.model.Model.Part");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Part_Type)jcasType).casFeatCode_sectionLevel);}
    
  /** setter for sectionLevel - sets sectionLevel 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSectionLevel(int v) {
    if (Part_Type.featOkTst && ((Part_Type)jcasType).casFeat_sectionLevel == null)
      jcasType.jcas.throwFeatMissing("sectionLevel", "edu.pitt.dbmi.xmeso.model.Model.Part");
    jcasType.ll_cas.ll_setIntValue(addr, ((Part_Type)jcasType).casFeatCode_sectionLevel, v);}    
   
    
  //*--------------*
  //* Feature: partNumber

  /** getter for partNumber - gets partNumber
   * @generated
   * @return value of the feature 
   */
  public int getPartNumber() {
    if (Part_Type.featOkTst && ((Part_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.Part");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Part_Type)jcasType).casFeatCode_partNumber);}
    
  /** setter for partNumber - sets partNumber 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPartNumber(int v) {
    if (Part_Type.featOkTst && ((Part_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.Part");
    jcasType.ll_cas.ll_setIntValue(addr, ((Part_Type)jcasType).casFeatCode_partNumber, v);}    
  }

    