

/* First created by JCasGen Wed Aug 31 14:05:35 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Wed Aug 31 14:05:35 EDT 2016
 * XML source: C:/Users/zhy19/workspace/xmeso/descriptor/edu/pitt/dbmi/xmeso/XmesoEngine.xml
 * @generated */
public class XmesoSentenceToken extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(XmesoSentenceToken.class);
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
  protected XmesoSentenceToken() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public XmesoSentenceToken(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public XmesoSentenceToken(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public XmesoSentenceToken(JCas jcas, int begin, int end) {
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
    if (XmesoSentenceToken_Type.featOkTst && ((XmesoSentenceToken_Type)jcasType).casFeat_sectionName == null)
      jcasType.jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoSentenceToken_Type)jcasType).casFeatCode_sectionName);}
    
  /** setter for sectionName - sets sectionName 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSectionName(String v) {
    if (XmesoSentenceToken_Type.featOkTst && ((XmesoSentenceToken_Type)jcasType).casFeat_sectionName == null)
      jcasType.jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoSentenceToken_Type)jcasType).casFeatCode_sectionName, v);}    
   
    
  //*--------------*
  //* Feature: partNumber

  /** getter for partNumber - gets partNumber
   * @generated
   * @return value of the feature 
   */
  public int getPartNumber() {
    if (XmesoSentenceToken_Type.featOkTst && ((XmesoSentenceToken_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    return jcasType.ll_cas.ll_getIntValue(addr, ((XmesoSentenceToken_Type)jcasType).casFeatCode_partNumber);}
    
  /** setter for partNumber - sets partNumber 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPartNumber(int v) {
    if (XmesoSentenceToken_Type.featOkTst && ((XmesoSentenceToken_Type)jcasType).casFeat_partNumber == null)
      jcasType.jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    jcasType.ll_cas.ll_setIntValue(addr, ((XmesoSentenceToken_Type)jcasType).casFeatCode_partNumber, v);}    
   
    
  //*--------------*
  //* Feature: sentenceNumber

  /** getter for sentenceNumber - gets sentenceNumber
   * @generated
   * @return value of the feature 
   */
  public int getSentenceNumber() {
    if (XmesoSentenceToken_Type.featOkTst && ((XmesoSentenceToken_Type)jcasType).casFeat_sentenceNumber == null)
      jcasType.jcas.throwFeatMissing("sentenceNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    return jcasType.ll_cas.ll_getIntValue(addr, ((XmesoSentenceToken_Type)jcasType).casFeatCode_sentenceNumber);}
    
  /** setter for sentenceNumber - sets sentenceNumber 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSentenceNumber(int v) {
    if (XmesoSentenceToken_Type.featOkTst && ((XmesoSentenceToken_Type)jcasType).casFeat_sentenceNumber == null)
      jcasType.jcas.throwFeatMissing("sentenceNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    jcasType.ll_cas.ll_setIntValue(addr, ((XmesoSentenceToken_Type)jcasType).casFeatCode_sentenceNumber, v);}    
   
    
  //*--------------*
  //* Feature: tokenNumber

  /** getter for tokenNumber - gets tokenNumber
   * @generated
   * @return value of the feature 
   */
  public int getTokenNumber() {
    if (XmesoSentenceToken_Type.featOkTst && ((XmesoSentenceToken_Type)jcasType).casFeat_tokenNumber == null)
      jcasType.jcas.throwFeatMissing("tokenNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    return jcasType.ll_cas.ll_getIntValue(addr, ((XmesoSentenceToken_Type)jcasType).casFeatCode_tokenNumber);}
    
  /** setter for tokenNumber - sets tokenNumber 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTokenNumber(int v) {
    if (XmesoSentenceToken_Type.featOkTst && ((XmesoSentenceToken_Type)jcasType).casFeat_tokenNumber == null)
      jcasType.jcas.throwFeatMissing("tokenNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    jcasType.ll_cas.ll_setIntValue(addr, ((XmesoSentenceToken_Type)jcasType).casFeatCode_tokenNumber, v);}    
  }

    