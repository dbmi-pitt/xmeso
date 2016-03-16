
/* First created by JCasGen Tue Jan 26 17:14:56 EST 2016 */
package edu.pitt.dbmi.nlp.noble.uima.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** Noble Coder Sentence
 * Updated by JCasGen Thu Feb 04 17:17:10 EST 2016
 * @generated */
public class Sentence_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Sentence_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Sentence_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Sentence(addr, Sentence_Type.this);
  			   Sentence_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Sentence(addr, Sentence_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Sentence.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.nlp.noble.uima.types.Sentence");
 
  /** @generated */
  final Feature casFeat_numberOfCharacters;
  /** @generated */
  final int     casFeatCode_numberOfCharacters;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getNumberOfCharacters(int addr) {
        if (featOkTst && casFeat_numberOfCharacters == null)
      jcas.throwFeatMissing("numberOfCharacters", "edu.pitt.dbmi.nlp.noble.uima.types.Sentence");
    return ll_cas.ll_getIntValue(addr, casFeatCode_numberOfCharacters);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setNumberOfCharacters(int addr, int v) {
        if (featOkTst && casFeat_numberOfCharacters == null)
      jcas.throwFeatMissing("numberOfCharacters", "edu.pitt.dbmi.nlp.noble.uima.types.Sentence");
    ll_cas.ll_setIntValue(addr, casFeatCode_numberOfCharacters, v);}
    
  
 
  /** @generated */
  final Feature casFeat_underLyingText;
  /** @generated */
  final int     casFeatCode_underLyingText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getUnderLyingText(int addr) {
        if (featOkTst && casFeat_underLyingText == null)
      jcas.throwFeatMissing("underLyingText", "edu.pitt.dbmi.nlp.noble.uima.types.Sentence");
    return ll_cas.ll_getStringValue(addr, casFeatCode_underLyingText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setUnderLyingText(int addr, String v) {
        if (featOkTst && casFeat_underLyingText == null)
      jcas.throwFeatMissing("underLyingText", "edu.pitt.dbmi.nlp.noble.uima.types.Sentence");
    ll_cas.ll_setStringValue(addr, casFeatCode_underLyingText, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Sentence_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_numberOfCharacters = jcas.getRequiredFeatureDE(casType, "numberOfCharacters", "uima.cas.Integer", featOkTst);
    casFeatCode_numberOfCharacters  = (null == casFeat_numberOfCharacters) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_numberOfCharacters).getCode();

 
    casFeat_underLyingText = jcas.getRequiredFeatureDE(casType, "underLyingText", "uima.cas.String", featOkTst);
    casFeatCode_underLyingText  = (null == casFeat_underLyingText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_underLyingText).getCode();

  }
}



    