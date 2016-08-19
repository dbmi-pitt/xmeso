

/* First created by JCasGen Fri Aug 19 10:41:13 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Fri Aug 19 10:41:13 EDT 2016
 * XML source: C:/Users/zhy19/workspace/xmeso/descriptor/edu/pitt/dbmi/xmeso/XmesoEngine.xml
 * @generated */
public class XmesoSize extends XmesoNamedEntity {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(XmesoSize.class);
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
  protected XmesoSize() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public XmesoSize(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public XmesoSize(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public XmesoSize(JCas jcas, int begin, int end) {
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
  //* Feature: maxDim

  /** getter for maxDim - gets maxDim
   * @generated
   * @return value of the feature 
   */
  public float getMaxDim() {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_maxDim == null)
      jcasType.jcas.throwFeatMissing("maxDim", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_maxDim);}
    
  /** setter for maxDim - sets maxDim 
   * @generated
   * @param v value to set into the feature 
   */
  public void setMaxDim(float v) {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_maxDim == null)
      jcasType.jcas.throwFeatMissing("maxDim", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_maxDim, v);}    
   
    
  //*--------------*
  //* Feature: dimOne

  /** getter for dimOne - gets dimOne
   * @generated
   * @return value of the feature 
   */
  public float getDimOne() {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_dimOne == null)
      jcasType.jcas.throwFeatMissing("dimOne", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_dimOne);}
    
  /** setter for dimOne - sets dimOne 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDimOne(float v) {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_dimOne == null)
      jcasType.jcas.throwFeatMissing("dimOne", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_dimOne, v);}    
   
    
  //*--------------*
  //* Feature: dimTwo

  /** getter for dimTwo - gets dimTwo
   * @generated
   * @return value of the feature 
   */
  public float getDimTwo() {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_dimTwo == null)
      jcasType.jcas.throwFeatMissing("dimTwo", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_dimTwo);}
    
  /** setter for dimTwo - sets dimTwo 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDimTwo(float v) {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_dimTwo == null)
      jcasType.jcas.throwFeatMissing("dimTwo", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_dimTwo, v);}    
   
    
  //*--------------*
  //* Feature: dimThree

  /** getter for dimThree - gets dimThree
   * @generated
   * @return value of the feature 
   */
  public float getDimThree() {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_dimThree == null)
      jcasType.jcas.throwFeatMissing("dimThree", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return jcasType.ll_cas.ll_getFloatValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_dimThree);}
    
  /** setter for dimThree - sets dimThree 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDimThree(float v) {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_dimThree == null)
      jcasType.jcas.throwFeatMissing("dimThree", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    jcasType.ll_cas.ll_setFloatValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_dimThree, v);}    
   
    
  //*--------------*
  //* Feature: uom

  /** getter for uom - gets uom
   * @generated
   * @return value of the feature 
   */
  public String getUom() {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_uom == null)
      jcasType.jcas.throwFeatMissing("uom", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_uom);}
    
  /** setter for uom - sets uom 
   * @generated
   * @param v value to set into the feature 
   */
  public void setUom(String v) {
    if (XmesoSize_Type.featOkTst && ((XmesoSize_Type)jcasType).casFeat_uom == null)
      jcasType.jcas.throwFeatMissing("uom", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoSize_Type)jcasType).casFeatCode_uom, v);}    
  }

    