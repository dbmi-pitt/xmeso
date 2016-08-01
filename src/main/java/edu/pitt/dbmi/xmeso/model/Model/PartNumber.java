

/* First created by JCasGen Mon Aug 01 15:50:48 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Mon Aug 01 15:50:48 EDT 2016
 * XML source: C:/Users/zhy19/workspace/xmeso/descriptor/edu/pitt/dbmi/xmeso/XmesoEngine.xml
 * @generated */
public class PartNumber extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PartNumber.class);
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
  protected PartNumber() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public PartNumber(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public PartNumber(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public PartNumber(JCas jcas, int begin, int end) {
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
    if (PartNumber_Type.featOkTst && ((PartNumber_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    return jcasType.ll_cas.ll_getIntValue(addr, ((PartNumber_Type)jcasType).casFeatCode_partNumber);}
    
  /** setter for partNumber - sets partNumber 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPartNumber(int v) {
    if (PartNumber_Type.featOkTst && ((PartNumber_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    jcasType.ll_cas.ll_setIntValue(addr, ((PartNumber_Type)jcasType).casFeatCode_partNumber, v);}    
   
    
  //*--------------*
  //* Feature: genNumber

  /** getter for genNumber - gets genNumber
   * @generated
   * @return value of the feature 
   */
  public int getGenNumber() {
    if (PartNumber_Type.featOkTst && ((PartNumber_Type)jcasType).casFeat_genNumber == null)
      jcasType.jcas.throwFeatMissing("genNumber", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    return jcasType.ll_cas.ll_getIntValue(addr, ((PartNumber_Type)jcasType).casFeatCode_genNumber);}
    
  /** setter for genNumber - sets genNumber 
   * @generated
   * @param v value to set into the feature 
   */
  public void setGenNumber(int v) {
    if (PartNumber_Type.featOkTst && ((PartNumber_Type)jcasType).casFeat_genNumber == null)
      jcasType.jcas.throwFeatMissing("genNumber", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    jcasType.ll_cas.ll_setIntValue(addr, ((PartNumber_Type)jcasType).casFeatCode_genNumber, v);}    
   
    
  //*--------------*
  //* Feature: genSeq

  /** getter for genSeq - gets genSeq
   * @generated
   * @return value of the feature 
   */
  public int getGenSeq() {
    if (PartNumber_Type.featOkTst && ((PartNumber_Type)jcasType).casFeat_genSeq == null)
      jcasType.jcas.throwFeatMissing("genSeq", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    return jcasType.ll_cas.ll_getIntValue(addr, ((PartNumber_Type)jcasType).casFeatCode_genSeq);}
    
  /** setter for genSeq - sets genSeq 
   * @generated
   * @param v value to set into the feature 
   */
  public void setGenSeq(int v) {
    if (PartNumber_Type.featOkTst && ((PartNumber_Type)jcasType).casFeat_genSeq == null)
      jcasType.jcas.throwFeatMissing("genSeq", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    jcasType.ll_cas.ll_setIntValue(addr, ((PartNumber_Type)jcasType).casFeatCode_genSeq, v);}    
  }

    