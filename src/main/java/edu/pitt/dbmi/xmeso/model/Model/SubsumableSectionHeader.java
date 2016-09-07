

/* First created by JCasGen Wed Sep 07 09:03:56 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Wed Sep 07 09:03:56 EDT 2016
 * XML source: C:/Users/zhy19/workspace/xmeso/descriptor/edu/pitt/dbmi/xmeso/XmesoEngine.xml
 * @generated */
public class SubsumableSectionHeader extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SubsumableSectionHeader.class);
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
  protected SubsumableSectionHeader() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SubsumableSectionHeader(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SubsumableSectionHeader(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public SubsumableSectionHeader(JCas jcas, int begin, int end) {
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
  //* Feature: name

  /** getter for name - gets name
   * @generated
   * @return value of the feature 
   */
  public String getName() {
    if (SubsumableSectionHeader_Type.featOkTst && ((SubsumableSectionHeader_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "edu.pitt.dbmi.xmeso.model.Model.SubsumableSectionHeader");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SubsumableSectionHeader_Type)jcasType).casFeatCode_name);}
    
  /** setter for name - sets name 
   * @generated
   * @param v value to set into the feature 
   */
  public void setName(String v) {
    if (SubsumableSectionHeader_Type.featOkTst && ((SubsumableSectionHeader_Type)jcasType).casFeat_name == null)
      jcasType.jcas.throwFeatMissing("name", "edu.pitt.dbmi.xmeso.model.Model.SubsumableSectionHeader");
    jcasType.ll_cas.ll_setStringValue(addr, ((SubsumableSectionHeader_Type)jcasType).casFeatCode_name, v);}    
   
    
  //*--------------*
  //* Feature: level

  /** getter for level - gets level
   * @generated
   * @return value of the feature 
   */
  public int getLevel() {
    if (SubsumableSectionHeader_Type.featOkTst && ((SubsumableSectionHeader_Type)jcasType).casFeat_level == null)
      jcasType.jcas.throwFeatMissing("level", "edu.pitt.dbmi.xmeso.model.Model.SubsumableSectionHeader");
    return jcasType.ll_cas.ll_getIntValue(addr, ((SubsumableSectionHeader_Type)jcasType).casFeatCode_level);}
    
  /** setter for level - sets level 
   * @generated
   * @param v value to set into the feature 
   */
  public void setLevel(int v) {
    if (SubsumableSectionHeader_Type.featOkTst && ((SubsumableSectionHeader_Type)jcasType).casFeat_level == null)
      jcasType.jcas.throwFeatMissing("level", "edu.pitt.dbmi.xmeso.model.Model.SubsumableSectionHeader");
    jcasType.ll_cas.ll_setIntValue(addr, ((SubsumableSectionHeader_Type)jcasType).casFeatCode_level, v);}    
  }

    