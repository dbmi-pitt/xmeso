

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
public class XmesoCodeAssigner extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(XmesoCodeAssigner.class);
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
  protected XmesoCodeAssigner() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public XmesoCodeAssigner(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public XmesoCodeAssigner(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public XmesoCodeAssigner(JCas jcas, int begin, int end) {
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
  //* Feature: tumorForm

  /** getter for tumorForm - gets tumorForm
   * @generated
   * @return value of the feature 
   */
  public XmesoTumorForm getTumorForm() {
    if (XmesoCodeAssigner_Type.featOkTst && ((XmesoCodeAssigner_Type)jcasType).casFeat_tumorForm == null)
      jcasType.jcas.throwFeatMissing("tumorForm", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    return (XmesoTumorForm)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((XmesoCodeAssigner_Type)jcasType).casFeatCode_tumorForm)));}
    
  /** setter for tumorForm - sets tumorForm 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorForm(XmesoTumorForm v) {
    if (XmesoCodeAssigner_Type.featOkTst && ((XmesoCodeAssigner_Type)jcasType).casFeat_tumorForm == null)
      jcasType.jcas.throwFeatMissing("tumorForm", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    jcasType.ll_cas.ll_setRefValue(addr, ((XmesoCodeAssigner_Type)jcasType).casFeatCode_tumorForm, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: tumorSite

  /** getter for tumorSite - gets tumorSite
   * @generated
   * @return value of the feature 
   */
  public XmesoTumorSite getTumorSite() {
    if (XmesoCodeAssigner_Type.featOkTst && ((XmesoCodeAssigner_Type)jcasType).casFeat_tumorSite == null)
      jcasType.jcas.throwFeatMissing("tumorSite", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    return (XmesoTumorSite)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((XmesoCodeAssigner_Type)jcasType).casFeatCode_tumorSite)));}
    
  /** setter for tumorSite - sets tumorSite 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorSite(XmesoTumorSite v) {
    if (XmesoCodeAssigner_Type.featOkTst && ((XmesoCodeAssigner_Type)jcasType).casFeat_tumorSite == null)
      jcasType.jcas.throwFeatMissing("tumorSite", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    jcasType.ll_cas.ll_setRefValue(addr, ((XmesoCodeAssigner_Type)jcasType).casFeatCode_tumorSite, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: tumorConfiguration

  /** getter for tumorConfiguration - gets tumorConfiguration
   * @generated
   * @return value of the feature 
   */
  public XmesoTumorConfiguration getTumorConfiguration() {
    if (XmesoCodeAssigner_Type.featOkTst && ((XmesoCodeAssigner_Type)jcasType).casFeat_tumorConfiguration == null)
      jcasType.jcas.throwFeatMissing("tumorConfiguration", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    return (XmesoTumorConfiguration)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((XmesoCodeAssigner_Type)jcasType).casFeatCode_tumorConfiguration)));}
    
  /** setter for tumorConfiguration - sets tumorConfiguration 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorConfiguration(XmesoTumorConfiguration v) {
    if (XmesoCodeAssigner_Type.featOkTst && ((XmesoCodeAssigner_Type)jcasType).casFeat_tumorConfiguration == null)
      jcasType.jcas.throwFeatMissing("tumorConfiguration", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    jcasType.ll_cas.ll_setRefValue(addr, ((XmesoCodeAssigner_Type)jcasType).casFeatCode_tumorConfiguration, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: tumorDifferentiation

  /** getter for tumorDifferentiation - gets tumorDifferentiation
   * @generated
   * @return value of the feature 
   */
  public XmesoTumorDifferentiation getTumorDifferentiation() {
    if (XmesoCodeAssigner_Type.featOkTst && ((XmesoCodeAssigner_Type)jcasType).casFeat_tumorDifferentiation == null)
      jcasType.jcas.throwFeatMissing("tumorDifferentiation", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    return (XmesoTumorDifferentiation)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((XmesoCodeAssigner_Type)jcasType).casFeatCode_tumorDifferentiation)));}
    
  /** setter for tumorDifferentiation - sets tumorDifferentiation 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTumorDifferentiation(XmesoTumorDifferentiation v) {
    if (XmesoCodeAssigner_Type.featOkTst && ((XmesoCodeAssigner_Type)jcasType).casFeat_tumorDifferentiation == null)
      jcasType.jcas.throwFeatMissing("tumorDifferentiation", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    jcasType.ll_cas.ll_setRefValue(addr, ((XmesoCodeAssigner_Type)jcasType).casFeatCode_tumorDifferentiation, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    