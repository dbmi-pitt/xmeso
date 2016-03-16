

/* First created by JCasGen Tue Jan 26 17:14:56 EST 2016 */
package edu.pitt.dbmi.nlp.noble.uima.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** Noble Coder Sentence
 * Updated by JCasGen Thu Feb 04 17:17:10 EST 2016
 * XML source: C:/ws/ws-xmeso/xmeso/desc/types/XmesoSystemDescriptor.xml
 * @generated */
public class Sentence extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Sentence.class);
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
  protected Sentence() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public Sentence(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public Sentence(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public Sentence(JCas jcas, int begin, int end) {
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
  //* Feature: numberOfCharacters

  /** getter for numberOfCharacters - gets Number of characters underlying this sentence.
   * @generated
   * @return value of the feature 
   */
  public int getNumberOfCharacters() {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_numberOfCharacters == null)
      jcasType.jcas.throwFeatMissing("numberOfCharacters", "edu.pitt.dbmi.nlp.noble.uima.types.Sentence");
    return jcasType.ll_cas.ll_getIntValue(addr, ((Sentence_Type)jcasType).casFeatCode_numberOfCharacters);}
    
  /** setter for numberOfCharacters - sets Number of characters underlying this sentence. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setNumberOfCharacters(int v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_numberOfCharacters == null)
      jcasType.jcas.throwFeatMissing("numberOfCharacters", "edu.pitt.dbmi.nlp.noble.uima.types.Sentence");
    jcasType.ll_cas.ll_setIntValue(addr, ((Sentence_Type)jcasType).casFeatCode_numberOfCharacters, v);}    
   
    
  //*--------------*
  //* Feature: underLyingText

  /** getter for underLyingText - gets Text beneath sentence.
   * @generated
   * @return value of the feature 
   */
  public String getUnderLyingText() {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_underLyingText == null)
      jcasType.jcas.throwFeatMissing("underLyingText", "edu.pitt.dbmi.nlp.noble.uima.types.Sentence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Sentence_Type)jcasType).casFeatCode_underLyingText);}
    
  /** setter for underLyingText - sets Text beneath sentence. 
   * @generated
   * @param v value to set into the feature 
   */
  public void setUnderLyingText(String v) {
    if (Sentence_Type.featOkTst && ((Sentence_Type)jcasType).casFeat_underLyingText == null)
      jcasType.jcas.throwFeatMissing("underLyingText", "edu.pitt.dbmi.nlp.noble.uima.types.Sentence");
    jcasType.ll_cas.ll_setStringValue(addr, ((Sentence_Type)jcasType).casFeatCode_underLyingText, v);}    
  }

    