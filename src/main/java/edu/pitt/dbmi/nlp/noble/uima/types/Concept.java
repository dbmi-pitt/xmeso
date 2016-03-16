

/* First created by JCasGen Tue Jan 26 17:14:56 EST 2016 */
package edu.pitt.dbmi.nlp.noble.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** Represents  a NobleTools Concept.
 * Updated by JCasGen Thu Feb 04 17:17:10 EST 2016
 * XML source: C:/ws/ws-xmeso/xmeso/desc/types/XmesoSystemDescriptor.xml
 * @generated */
public class Concept extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Concept.class);
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
  protected Concept() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Concept(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Concept(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Concept(JCas jcas, int begin, int end) {
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
  //* Feature: cui

  /** getter for cui - gets Concept unique identifier.
   * @generated
   * @return value of the feature 
   */
  public String getCui() {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_cui == null)
      jcasType.jcas.throwFeatMissing("cui", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Concept_Type)jcasType).casFeatCode_cui);}
    
  /** setter for cui - sets Concept unique identifier. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCui(String v) {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_cui == null)
      jcasType.jcas.throwFeatMissing("cui", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.ll_cas.ll_setStringValue(addr, ((Concept_Type)jcasType).casFeatCode_cui, v);}    
   
    
  //*--------------*
  //* Feature: tuis

  /** getter for tuis - gets Set of Semantic Types
   * @generated
   * @return value of the feature 
   */
  public FSArray getTuis() {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_tuis == null)
      jcasType.jcas.throwFeatMissing("tuis", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return (FSArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_tuis)));}
    
  /** setter for tuis - sets Set of Semantic Types 
   * @generated
   * @param v value to set into the feature 
   */
  public void setTuis(FSArray v) {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_tuis == null)
      jcasType.jcas.throwFeatMissing("tuis", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.ll_cas.ll_setRefValue(addr, ((Concept_Type)jcasType).casFeatCode_tuis, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for tuis - gets an indexed value - Set of Semantic Types
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public SemanticType getTuis(int i) {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_tuis == null)
      jcasType.jcas.throwFeatMissing("tuis", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_tuis), i);
    return (SemanticType)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_tuis), i)));}

  /** indexed setter for tuis - sets an indexed value - Set of Semantic Types
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setTuis(int i, SemanticType v) { 
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_tuis == null)
      jcasType.jcas.throwFeatMissing("tuis", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_tuis), i);
    jcasType.ll_cas.ll_setRefArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_tuis), i, jcasType.ll_cas.ll_getFSRef(v));}
   
    
  //*--------------*
  //* Feature: synonyms

  /** getter for synonyms - gets Set of synonyms.
   * @generated
   * @return value of the feature 
   */
  public StringArray getSynonyms() {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_synonyms == null)
      jcasType.jcas.throwFeatMissing("synonyms", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_synonyms)));}
    
  /** setter for synonyms - sets Set of synonyms. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setSynonyms(StringArray v) {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_synonyms == null)
      jcasType.jcas.throwFeatMissing("synonyms", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.ll_cas.ll_setRefValue(addr, ((Concept_Type)jcasType).casFeatCode_synonyms, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for synonyms - gets an indexed value - Set of synonyms.
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getSynonyms(int i) {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_synonyms == null)
      jcasType.jcas.throwFeatMissing("synonyms", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_synonyms), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_synonyms), i);}

  /** indexed setter for synonyms - sets an indexed value - Set of synonyms.
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setSynonyms(int i, String v) { 
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_synonyms == null)
      jcasType.jcas.throwFeatMissing("synonyms", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_synonyms), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Concept_Type)jcasType).casFeatCode_synonyms), i, v);}
   
    
  //*--------------*
  //* Feature: cn

  /** getter for cn - gets Concept Name
   * @generated
   * @return value of the feature 
   */
  public String getCn() {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_cn == null)
      jcasType.jcas.throwFeatMissing("cn", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Concept_Type)jcasType).casFeatCode_cn);}
    
  /** setter for cn - sets Concept Name 
   * @generated
   * @param v value to set into the feature 
   */
  public void setCn(String v) {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_cn == null)
      jcasType.jcas.throwFeatMissing("cn", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.ll_cas.ll_setStringValue(addr, ((Concept_Type)jcasType).casFeatCode_cn, v);}    
   
    
  //*--------------*
  //* Feature: preferredTerm

  /** getter for preferredTerm - gets Preferred Term
   * @generated
   * @return value of the feature 
   */
  public String getPreferredTerm() {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_preferredTerm == null)
      jcasType.jcas.throwFeatMissing("preferredTerm", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Concept_Type)jcasType).casFeatCode_preferredTerm);}
    
  /** setter for preferredTerm - sets Preferred Term 
   * @generated
   * @param v value to set into the feature 
   */
  public void setPreferredTerm(String v) {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_preferredTerm == null)
      jcasType.jcas.throwFeatMissing("preferredTerm", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.ll_cas.ll_setStringValue(addr, ((Concept_Type)jcasType).casFeatCode_preferredTerm, v);}    
   
    
  //*--------------*
  //* Feature: definition

  /** getter for definition - gets Definition.
   * @generated
   * @return value of the feature 
   */
  public String getDefinition() {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_definition == null)
      jcasType.jcas.throwFeatMissing("definition", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Concept_Type)jcasType).casFeatCode_definition);}
    
  /** setter for definition - sets Definition. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setDefinition(String v) {
    if (Concept_Type.featOkTst && ((Concept_Type)jcasType).casFeat_definition == null)
      jcasType.jcas.throwFeatMissing("definition", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    jcasType.ll_cas.ll_setStringValue(addr, ((Concept_Type)jcasType).casFeatCode_definition, v);}    
  }

    