
/* First created by JCasGen Wed Aug 31 14:36:42 EDT 2016 */
package edu.pitt.dbmi.xmeso.model.Model;

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

/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Wed Aug 31 14:36:42 EDT 2016
 * @generated */
public class XmesoSentenceToken_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (XmesoSentenceToken_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = XmesoSentenceToken_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new XmesoSentenceToken(addr, XmesoSentenceToken_Type.this);
  			   XmesoSentenceToken_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new XmesoSentenceToken(addr, XmesoSentenceToken_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = XmesoSentenceToken.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
 
  /** @generated */
  final Feature casFeat_sectionName;
  /** @generated */
  final int     casFeatCode_sectionName;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSectionName(int addr) {
        if (featOkTst && casFeat_sectionName == null)
      jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sectionName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSectionName(int addr, String v) {
        if (featOkTst && casFeat_sectionName == null)
      jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    ll_cas.ll_setStringValue(addr, casFeatCode_sectionName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_partNumber;
  /** @generated */
  final int     casFeatCode_partNumber;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getPartNumber(int addr) {
        if (featOkTst && casFeat_partNumber == null)
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_partNumber);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPartNumber(int addr, int v) {
        if (featOkTst && casFeat_partNumber == null)
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_partNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sentenceNumber;
  /** @generated */
  final int     casFeatCode_sentenceNumber;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSentenceNumber(int addr) {
        if (featOkTst && casFeat_sentenceNumber == null)
      jcas.throwFeatMissing("sentenceNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_sentenceNumber);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSentenceNumber(int addr, int v) {
        if (featOkTst && casFeat_sentenceNumber == null)
      jcas.throwFeatMissing("sentenceNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_sentenceNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tokenNumber;
  /** @generated */
  final int     casFeatCode_tokenNumber;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTokenNumber(int addr) {
        if (featOkTst && casFeat_tokenNumber == null)
      jcas.throwFeatMissing("tokenNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    return ll_cas.ll_getIntValue(addr, casFeatCode_tokenNumber);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTokenNumber(int addr, int v) {
        if (featOkTst && casFeat_tokenNumber == null)
      jcas.throwFeatMissing("tokenNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoSentenceToken");
    ll_cas.ll_setIntValue(addr, casFeatCode_tokenNumber, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public XmesoSentenceToken_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_sectionName = jcas.getRequiredFeatureDE(casType, "sectionName", "uima.cas.String", featOkTst);
    casFeatCode_sectionName  = (null == casFeat_sectionName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sectionName).getCode();

 
    casFeat_partNumber = jcas.getRequiredFeatureDE(casType, "partNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_partNumber  = (null == casFeat_partNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_partNumber).getCode();

 
    casFeat_sentenceNumber = jcas.getRequiredFeatureDE(casType, "sentenceNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_sentenceNumber  = (null == casFeat_sentenceNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentenceNumber).getCode();

 
    casFeat_tokenNumber = jcas.getRequiredFeatureDE(casType, "tokenNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_tokenNumber  = (null == casFeat_tokenNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokenNumber).getCode();

  }
}



    