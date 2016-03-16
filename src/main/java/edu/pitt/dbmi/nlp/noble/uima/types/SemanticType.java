

/* First created by JCasGen Tue Jan 26 17:14:56 EST 2016 */
package edu.pitt.dbmi.nlp.noble.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Noble Tools Semantic Type
 * Updated by JCasGen Thu Feb 04 17:17:10 EST 2016
 * XML source: C:/ws/ws-xmeso/xmeso/desc/types/XmesoSystemDescriptor.xml
 * @generated */
public class SemanticType extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(SemanticType.class);
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
  protected SemanticType() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public SemanticType(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public SemanticType(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public SemanticType(JCas jcas, int begin, int end) {
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
  //* Feature: tui

  /** getter for tui - gets 
   * @generated
   * @return value of the feature 
   */
  public String getTui() {
    if (SemanticType_Type.featOkTst && ((SemanticType_Type)jcasType).casFeat_tui == null)
      jcasType.jcas.throwFeatMissing("tui", "edu.pitt.dbmi.nlp.noble.uima.types.SemanticType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SemanticType_Type)jcasType).casFeatCode_tui);}
    
  /** setter for tui - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTui(String v) {
    if (SemanticType_Type.featOkTst && ((SemanticType_Type)jcasType).casFeat_tui == null)
      jcasType.jcas.throwFeatMissing("tui", "edu.pitt.dbmi.nlp.noble.uima.types.SemanticType");
    jcasType.ll_cas.ll_setStringValue(addr, ((SemanticType_Type)jcasType).casFeatCode_tui, v);}    
   
    
  //*--------------*
  //* Feature: sty

  /** getter for sty - gets Semantic Type Name
   * @generated
   * @return value of the feature 
   */
  public String getSty() {
    if (SemanticType_Type.featOkTst && ((SemanticType_Type)jcasType).casFeat_sty == null)
      jcasType.jcas.throwFeatMissing("sty", "edu.pitt.dbmi.nlp.noble.uima.types.SemanticType");
    return jcasType.ll_cas.ll_getStringValue(addr, ((SemanticType_Type)jcasType).casFeatCode_sty);}
    
  /** setter for sty - sets Semantic Type Name 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSty(String v) {
    if (SemanticType_Type.featOkTst && ((SemanticType_Type)jcasType).casFeat_sty == null)
      jcasType.jcas.throwFeatMissing("sty", "edu.pitt.dbmi.nlp.noble.uima.types.SemanticType");
    jcasType.ll_cas.ll_setStringValue(addr, ((SemanticType_Type)jcasType).casFeatCode_sty, v);}    
  }

    