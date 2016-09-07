

/* First created by JCasGen Wed Sep 07 12:38:01 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Wed Sep 07 12:38:01 EDT 2016
 * XML source: C:/Users/zhy19/workspace/xmeso/descriptor/edu/pitt/dbmi/xmeso/XmesoEngine.xml
 * @generated */
public class RomanPartNumber extends PartNumber {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RomanPartNumber.class);
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
  protected RomanPartNumber() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RomanPartNumber(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RomanPartNumber(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public RomanPartNumber(JCas jcas, int begin, int end) {
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
  //* Feature: romanValue

  /** getter for romanValue - gets romanValue
   * @generated
   * @return value of the feature 
   */
  public String getRomanValue() {
    if (RomanPartNumber_Type.featOkTst && ((RomanPartNumber_Type)jcasType).casFeat_romanValue == null)
      jcasType.jcas.throwFeatMissing("romanValue", "edu.pitt.dbmi.xmeso.model.Model.RomanPartNumber");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RomanPartNumber_Type)jcasType).casFeatCode_romanValue);}
    
  /** setter for romanValue - sets romanValue 
   * @generated
   * @param v value to set into the feature 
   */
  public void setRomanValue(String v) {
    if (RomanPartNumber_Type.featOkTst && ((RomanPartNumber_Type)jcasType).casFeat_romanValue == null)
      jcasType.jcas.throwFeatMissing("romanValue", "edu.pitt.dbmi.xmeso.model.Model.RomanPartNumber");
    jcasType.ll_cas.ll_setStringValue(addr, ((RomanPartNumber_Type)jcasType).casFeatCode_romanValue, v);}    
  }

    