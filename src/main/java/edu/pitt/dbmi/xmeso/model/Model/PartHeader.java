

/* First created by JCasGen Wed Sep 07 16:18:03 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Wed Sep 07 16:18:03 EDT 2016
 * XML source: C:/Users/zhy19/workspace/xmeso/descriptor/edu/pitt/dbmi/xmeso/XmesoEngine.xml
 * @generated */
public class PartHeader extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PartHeader.class);
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
  protected PartHeader() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public PartHeader(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public PartHeader(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public PartHeader(JCas jcas, int begin, int end) {
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
    if (PartHeader_Type.featOkTst && ((PartHeader_Type)jcasType).casFeat_sectionName == null)
      jcasType.jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.PartHeader");
    return jcasType.ll_cas.ll_getStringValue(addr, ((PartHeader_Type)jcasType).casFeatCode_sectionName);}
    
  /** setter for sectionName - sets sectionName 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSectionName(String v) {
    if (PartHeader_Type.featOkTst && ((PartHeader_Type)jcasType).casFeat_sectionName == null)
      jcasType.jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.PartHeader");
    jcasType.ll_cas.ll_setStringValue(addr, ((PartHeader_Type)jcasType).casFeatCode_sectionName, v);}    
   
    
  //*--------------*
  //* Feature: sectionLevel

  /** getter for sectionLevel - gets sectionLevel
   * @generated
   * @return value of the feature 
   */
  public int getSectionLevel() {
    if (PartHeader_Type.featOkTst && ((PartHeader_Type)jcasType).casFeat_sectionLevel == null)
      jcasType.jcas.throwFeatMissing("sectionLevel", "edu.pitt.dbmi.xmeso.model.Model.PartHeader");
    return jcasType.ll_cas.ll_getIntValue(addr, ((PartHeader_Type)jcasType).casFeatCode_sectionLevel);}
    
  /** setter for sectionLevel - sets sectionLevel 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSectionLevel(int v) {
    if (PartHeader_Type.featOkTst && ((PartHeader_Type)jcasType).casFeat_sectionLevel == null)
      jcasType.jcas.throwFeatMissing("sectionLevel", "edu.pitt.dbmi.xmeso.model.Model.PartHeader");
    jcasType.ll_cas.ll_setIntValue(addr, ((PartHeader_Type)jcasType).casFeatCode_sectionLevel, v);}    
   
    
  //*--------------*
  //* Feature: partNumber

  /** getter for partNumber - gets partNumber
   * @generated
   * @return value of the feature 
   */
  public int getPartNumber() {
    if (PartHeader_Type.featOkTst && ((PartHeader_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.PartHeader");
    return jcasType.ll_cas.ll_getIntValue(addr, ((PartHeader_Type)jcasType).casFeatCode_partNumber);}
    
  /** setter for partNumber - sets partNumber 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPartNumber(int v) {
    if (PartHeader_Type.featOkTst && ((PartHeader_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.PartHeader");
    jcasType.ll_cas.ll_setIntValue(addr, ((PartHeader_Type)jcasType).casFeatCode_partNumber, v);}    
  }

    