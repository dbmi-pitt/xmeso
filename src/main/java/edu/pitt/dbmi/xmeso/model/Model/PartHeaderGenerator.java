

/* First created by JCasGen Wed Sep 07 14:14:36 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Wed Sep 07 14:14:36 EDT 2016
 * XML source: C:/Users/zhy19/workspace/xmeso/descriptor/edu/pitt/dbmi/xmeso/XmesoEngine.xml
 * @generated */
public class PartHeaderGenerator extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(PartHeaderGenerator.class);
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
  protected PartHeaderGenerator() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public PartHeaderGenerator(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public PartHeaderGenerator(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public PartHeaderGenerator(JCas jcas, int begin, int end) {
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
  //* Feature: genNumber

  /** getter for genNumber - gets genNumber
   * @generated
   * @return value of the feature 
   */
  public int getGenNumber() {
    if (PartHeaderGenerator_Type.featOkTst && ((PartHeaderGenerator_Type)jcasType).casFeat_genNumber == null)
      jcasType.jcas.throwFeatMissing("genNumber", "edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator");
    return jcasType.ll_cas.ll_getIntValue(addr, ((PartHeaderGenerator_Type)jcasType).casFeatCode_genNumber);}
    
  /** setter for genNumber - sets genNumber 
   * @generated
   * @param v value to set into the feature 
   */
  public void setGenNumber(int v) {
    if (PartHeaderGenerator_Type.featOkTst && ((PartHeaderGenerator_Type)jcasType).casFeat_genNumber == null)
      jcasType.jcas.throwFeatMissing("genNumber", "edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator");
    jcasType.ll_cas.ll_setIntValue(addr, ((PartHeaderGenerator_Type)jcasType).casFeatCode_genNumber, v);}    
   
    
  //*--------------*
  //* Feature: genCount

  /** getter for genCount - gets genCount
   * @generated
   * @return value of the feature 
   */
  public int getGenCount() {
    if (PartHeaderGenerator_Type.featOkTst && ((PartHeaderGenerator_Type)jcasType).casFeat_genCount == null)
      jcasType.jcas.throwFeatMissing("genCount", "edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator");
    return jcasType.ll_cas.ll_getIntValue(addr, ((PartHeaderGenerator_Type)jcasType).casFeatCode_genCount);}
    
  /** setter for genCount - sets genCount 
   * @generated
   * @param v value to set into the feature 
   */
  public void setGenCount(int v) {
    if (PartHeaderGenerator_Type.featOkTst && ((PartHeaderGenerator_Type)jcasType).casFeat_genCount == null)
      jcasType.jcas.throwFeatMissing("genCount", "edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator");
    jcasType.ll_cas.ll_setIntValue(addr, ((PartHeaderGenerator_Type)jcasType).casFeatCode_genCount, v);}    
  }

    