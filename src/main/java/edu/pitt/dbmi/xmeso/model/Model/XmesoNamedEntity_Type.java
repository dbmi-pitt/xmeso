
/* First created by JCasGen Wed Sep 07 09:03:56 EDT 2016 */
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
 * Updated by JCasGen Wed Sep 07 09:03:56 EDT 2016
 * @generated */
public class XmesoNamedEntity_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (XmesoNamedEntity_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = XmesoNamedEntity_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new XmesoNamedEntity(addr, XmesoNamedEntity_Type.this);
  			   XmesoNamedEntity_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new XmesoNamedEntity(addr, XmesoNamedEntity_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = XmesoNamedEntity.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
 
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
      jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sectionName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSectionName(int addr, String v) {
        if (featOkTst && casFeat_sectionName == null)
      jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_sectionName, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sectionLevel;
  /** @generated */
  final int     casFeatCode_sectionLevel;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSectionLevel(int addr) {
        if (featOkTst && casFeat_sectionLevel == null)
      jcas.throwFeatMissing("sectionLevel", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    return ll_cas.ll_getIntValue(addr, casFeatCode_sectionLevel);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSectionLevel(int addr, int v) {
        if (featOkTst && casFeat_sectionLevel == null)
      jcas.throwFeatMissing("sectionLevel", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    ll_cas.ll_setIntValue(addr, casFeatCode_sectionLevel, v);}
    
  
 
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
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    return ll_cas.ll_getIntValue(addr, casFeatCode_partNumber);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPartNumber(int addr, int v) {
        if (featOkTst && casFeat_partNumber == null)
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    ll_cas.ll_setIntValue(addr, casFeatCode_partNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_snomedCode;
  /** @generated */
  final int     casFeatCode_snomedCode;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSnomedCode(int addr) {
        if (featOkTst && casFeat_snomedCode == null)
      jcas.throwFeatMissing("snomedCode", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_snomedCode);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSnomedCode(int addr, String v) {
        if (featOkTst && casFeat_snomedCode == null)
      jcas.throwFeatMissing("snomedCode", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_snomedCode, v);}
    
  
 
  /** @generated */
  final Feature casFeat_coveredText;
  /** @generated */
  final int     casFeatCode_coveredText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCoveredText(int addr) {
        if (featOkTst && casFeat_coveredText == null)
      jcas.throwFeatMissing("coveredText", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_coveredText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCoveredText(int addr, String v) {
        if (featOkTst && casFeat_coveredText == null)
      jcas.throwFeatMissing("coveredText", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_coveredText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_semanticType;
  /** @generated */
  final int     casFeatCode_semanticType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSemanticType(int addr) {
        if (featOkTst && casFeat_semanticType == null)
      jcas.throwFeatMissing("semanticType", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_semanticType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSemanticType(int addr, String v) {
        if (featOkTst && casFeat_semanticType == null)
      jcas.throwFeatMissing("semanticType", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_semanticType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_codedPenalty;
  /** @generated */
  final int     casFeatCode_codedPenalty;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getCodedPenalty(int addr) {
        if (featOkTst && casFeat_codedPenalty == null)
      jcas.throwFeatMissing("codedPenalty", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    return ll_cas.ll_getIntValue(addr, casFeatCode_codedPenalty);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCodedPenalty(int addr, int v) {
        if (featOkTst && casFeat_codedPenalty == null)
      jcas.throwFeatMissing("codedPenalty", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    ll_cas.ll_setIntValue(addr, casFeatCode_codedPenalty, v);}
    
  
 
  /** @generated */
  final Feature casFeat_isNegated;
  /** @generated */
  final int     casFeatCode_isNegated;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getIsNegated(int addr) {
        if (featOkTst && casFeat_isNegated == null)
      jcas.throwFeatMissing("isNegated", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isNegated);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIsNegated(int addr, boolean v) {
        if (featOkTst && casFeat_isNegated == null)
      jcas.throwFeatMissing("isNegated", "edu.pitt.dbmi.xmeso.model.Model.XmesoNamedEntity");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isNegated, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public XmesoNamedEntity_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_sectionName = jcas.getRequiredFeatureDE(casType, "sectionName", "uima.cas.String", featOkTst);
    casFeatCode_sectionName  = (null == casFeat_sectionName) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sectionName).getCode();

 
    casFeat_sectionLevel = jcas.getRequiredFeatureDE(casType, "sectionLevel", "uima.cas.Integer", featOkTst);
    casFeatCode_sectionLevel  = (null == casFeat_sectionLevel) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sectionLevel).getCode();

 
    casFeat_partNumber = jcas.getRequiredFeatureDE(casType, "partNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_partNumber  = (null == casFeat_partNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_partNumber).getCode();

 
    casFeat_snomedCode = jcas.getRequiredFeatureDE(casType, "snomedCode", "uima.cas.String", featOkTst);
    casFeatCode_snomedCode  = (null == casFeat_snomedCode) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_snomedCode).getCode();

 
    casFeat_coveredText = jcas.getRequiredFeatureDE(casType, "coveredText", "uima.cas.String", featOkTst);
    casFeatCode_coveredText  = (null == casFeat_coveredText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_coveredText).getCode();

 
    casFeat_semanticType = jcas.getRequiredFeatureDE(casType, "semanticType", "uima.cas.String", featOkTst);
    casFeatCode_semanticType  = (null == casFeat_semanticType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_semanticType).getCode();

 
    casFeat_codedPenalty = jcas.getRequiredFeatureDE(casType, "codedPenalty", "uima.cas.Integer", featOkTst);
    casFeatCode_codedPenalty  = (null == casFeat_codedPenalty) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_codedPenalty).getCode();

 
    casFeat_isNegated = jcas.getRequiredFeatureDE(casType, "isNegated", "uima.cas.Boolean", featOkTst);
    casFeatCode_isNegated  = (null == casFeat_isNegated) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isNegated).getCode();

  }
}



    