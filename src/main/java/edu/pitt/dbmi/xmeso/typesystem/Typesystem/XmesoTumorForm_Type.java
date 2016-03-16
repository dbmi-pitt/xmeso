
/* First created by JCasGen Tue Mar 01 10:05:29 EST 2016 */
package edu.pitt.dbmi.xmeso.typesystem.Typesystem;

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

/** Type defined in edu.pitt.dbmi.xmeso.typesystem.Typesystem
 * Updated by JCasGen Tue Mar 01 10:05:29 EST 2016
 * @generated */
public class XmesoTumorForm_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (XmesoTumorForm_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = XmesoTumorForm_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new XmesoTumorForm(addr, XmesoTumorForm_Type.this);
  			   XmesoTumorForm_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new XmesoTumorForm(addr, XmesoTumorForm_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = XmesoTumorForm.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
 
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
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return ll_cas.ll_getIntValue(addr, casFeatCode_partNumber);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPartNumber(int addr, int v) {
        if (featOkTst && casFeat_partNumber == null)
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    ll_cas.ll_setIntValue(addr, casFeatCode_partNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorSiteCode;
  /** @generated */
  final int     casFeatCode_tumorSiteCode;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTumorSiteCode(int addr) {
        if (featOkTst && casFeat_tumorSiteCode == null)
      jcas.throwFeatMissing("tumorSiteCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tumorSiteCode);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorSiteCode(int addr, String v) {
        if (featOkTst && casFeat_tumorSiteCode == null)
      jcas.throwFeatMissing("tumorSiteCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_tumorSiteCode, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorConfigurationCode;
  /** @generated */
  final int     casFeatCode_tumorConfigurationCode;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTumorConfigurationCode(int addr) {
        if (featOkTst && casFeat_tumorConfigurationCode == null)
      jcas.throwFeatMissing("tumorConfigurationCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tumorConfigurationCode);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorConfigurationCode(int addr, String v) {
        if (featOkTst && casFeat_tumorConfigurationCode == null)
      jcas.throwFeatMissing("tumorConfigurationCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_tumorConfigurationCode, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorDifferentiationCode;
  /** @generated */
  final int     casFeatCode_tumorDifferentiationCode;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTumorDifferentiationCode(int addr) {
        if (featOkTst && casFeat_tumorDifferentiationCode == null)
      jcas.throwFeatMissing("tumorDifferentiationCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tumorDifferentiationCode);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorDifferentiationCode(int addr, String v) {
        if (featOkTst && casFeat_tumorDifferentiationCode == null)
      jcas.throwFeatMissing("tumorDifferentiationCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_tumorDifferentiationCode, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorSizeGreatestDimension;
  /** @generated */
  final int     casFeatCode_tumorSizeGreatestDimension;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getTumorSizeGreatestDimension(int addr) {
        if (featOkTst && casFeat_tumorSizeGreatestDimension == null)
      jcas.throwFeatMissing("tumorSizeGreatestDimension", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_tumorSizeGreatestDimension);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorSizeGreatestDimension(int addr, float v) {
        if (featOkTst && casFeat_tumorSizeGreatestDimension == null)
      jcas.throwFeatMissing("tumorSizeGreatestDimension", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    ll_cas.ll_setFloatValue(addr, casFeatCode_tumorSizeGreatestDimension, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorSizeOne;
  /** @generated */
  final int     casFeatCode_tumorSizeOne;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getTumorSizeOne(int addr) {
        if (featOkTst && casFeat_tumorSizeOne == null)
      jcas.throwFeatMissing("tumorSizeOne", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_tumorSizeOne);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorSizeOne(int addr, float v) {
        if (featOkTst && casFeat_tumorSizeOne == null)
      jcas.throwFeatMissing("tumorSizeOne", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    ll_cas.ll_setFloatValue(addr, casFeatCode_tumorSizeOne, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorSizeTwo;
  /** @generated */
  final int     casFeatCode_tumorSizeTwo;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getTumorSizeTwo(int addr) {
        if (featOkTst && casFeat_tumorSizeTwo == null)
      jcas.throwFeatMissing("tumorSizeTwo", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_tumorSizeTwo);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorSizeTwo(int addr, float v) {
        if (featOkTst && casFeat_tumorSizeTwo == null)
      jcas.throwFeatMissing("tumorSizeTwo", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    ll_cas.ll_setFloatValue(addr, casFeatCode_tumorSizeTwo, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorSizeThree;
  /** @generated */
  final int     casFeatCode_tumorSizeThree;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getTumorSizeThree(int addr) {
        if (featOkTst && casFeat_tumorSizeThree == null)
      jcas.throwFeatMissing("tumorSizeThree", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_tumorSizeThree);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorSizeThree(int addr, float v) {
        if (featOkTst && casFeat_tumorSizeThree == null)
      jcas.throwFeatMissing("tumorSizeThree", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm");
    ll_cas.ll_setFloatValue(addr, casFeatCode_tumorSizeThree, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public XmesoTumorForm_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_partNumber = jcas.getRequiredFeatureDE(casType, "partNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_partNumber  = (null == casFeat_partNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_partNumber).getCode();

 
    casFeat_tumorSiteCode = jcas.getRequiredFeatureDE(casType, "tumorSiteCode", "uima.cas.String", featOkTst);
    casFeatCode_tumorSiteCode  = (null == casFeat_tumorSiteCode) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorSiteCode).getCode();

 
    casFeat_tumorConfigurationCode = jcas.getRequiredFeatureDE(casType, "tumorConfigurationCode", "uima.cas.String", featOkTst);
    casFeatCode_tumorConfigurationCode  = (null == casFeat_tumorConfigurationCode) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorConfigurationCode).getCode();

 
    casFeat_tumorDifferentiationCode = jcas.getRequiredFeatureDE(casType, "tumorDifferentiationCode", "uima.cas.String", featOkTst);
    casFeatCode_tumorDifferentiationCode  = (null == casFeat_tumorDifferentiationCode) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorDifferentiationCode).getCode();

 
    casFeat_tumorSizeGreatestDimension = jcas.getRequiredFeatureDE(casType, "tumorSizeGreatestDimension", "uima.cas.Float", featOkTst);
    casFeatCode_tumorSizeGreatestDimension  = (null == casFeat_tumorSizeGreatestDimension) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorSizeGreatestDimension).getCode();

 
    casFeat_tumorSizeOne = jcas.getRequiredFeatureDE(casType, "tumorSizeOne", "uima.cas.Float", featOkTst);
    casFeatCode_tumorSizeOne  = (null == casFeat_tumorSizeOne) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorSizeOne).getCode();

 
    casFeat_tumorSizeTwo = jcas.getRequiredFeatureDE(casType, "tumorSizeTwo", "uima.cas.Float", featOkTst);
    casFeatCode_tumorSizeTwo  = (null == casFeat_tumorSizeTwo) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorSizeTwo).getCode();

 
    casFeat_tumorSizeThree = jcas.getRequiredFeatureDE(casType, "tumorSizeThree", "uima.cas.Float", featOkTst);
    casFeatCode_tumorSizeThree  = (null == casFeat_tumorSizeThree) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorSizeThree).getCode();

  }
}



    