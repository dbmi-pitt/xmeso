

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
public class XmesoEncounterForm extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(XmesoEncounterForm.class);
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
  protected XmesoEncounterForm() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public XmesoEncounterForm(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public XmesoEncounterForm(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public XmesoEncounterForm(JCas jcas, int begin, int end) {
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
  //* Feature: surgicalProcedureCode

  /** getter for surgicalProcedureCode - gets surgicalProcedureCode
   * @generated
   * @return value of the feature 
   */
  public String getSurgicalProcedureCode() {
    if (XmesoEncounterForm_Type.featOkTst && ((XmesoEncounterForm_Type)jcasType).casFeat_surgicalProcedureCode == null)
      jcasType.jcas.throwFeatMissing("surgicalProcedureCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoEncounterForm_Type)jcasType).casFeatCode_surgicalProcedureCode);}
    
  /** setter for surgicalProcedureCode - sets surgicalProcedureCode 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSurgicalProcedureCode(String v) {
    if (XmesoEncounterForm_Type.featOkTst && ((XmesoEncounterForm_Type)jcasType).casFeat_surgicalProcedureCode == null)
      jcasType.jcas.throwFeatMissing("surgicalProcedureCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoEncounterForm_Type)jcasType).casFeatCode_surgicalProcedureCode, v);}    
   
    
  //*--------------*
  //* Feature: histologicTypeCode

  /** getter for histologicTypeCode - gets histologicTypeCode
   * @generated
   * @return value of the feature 
   */
  public String getHistologicTypeCode() {
    if (XmesoEncounterForm_Type.featOkTst && ((XmesoEncounterForm_Type)jcasType).casFeat_histologicTypeCode == null)
      jcasType.jcas.throwFeatMissing("histologicTypeCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
    return jcasType.ll_cas.ll_getStringValue(addr, ((XmesoEncounterForm_Type)jcasType).casFeatCode_histologicTypeCode);}
    
  /** setter for histologicTypeCode - sets histologicTypeCode 
   * @generated
   * @param v value to set into the feature 
   */
  public void setHistologicTypeCode(String v) {
    if (XmesoEncounterForm_Type.featOkTst && ((XmesoEncounterForm_Type)jcasType).casFeat_histologicTypeCode == null)
      jcasType.jcas.throwFeatMissing("histologicTypeCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
    jcasType.ll_cas.ll_setStringValue(addr, ((XmesoEncounterForm_Type)jcasType).casFeatCode_histologicTypeCode, v);}    
  }

    