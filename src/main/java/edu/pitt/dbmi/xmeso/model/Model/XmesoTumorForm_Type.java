
/* First created by JCasGen Wed Sep 07 12:35:01 EDT 2016 */
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
 * Updated by JCasGen Wed Sep 07 12:35:01 EDT 2016
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
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
 
  /** @generated */
  final Feature casFeat_currentPart;
  /** @generated */
  final int     casFeatCode_currentPart;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getCurrentPart(int addr) {
        if (featOkTst && casFeat_currentPart == null)
      jcas.throwFeatMissing("currentPart", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return ll_cas.ll_getIntValue(addr, casFeatCode_currentPart);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCurrentPart(int addr, int v) {
        if (featOkTst && casFeat_currentPart == null)
      jcas.throwFeatMissing("currentPart", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    ll_cas.ll_setIntValue(addr, casFeatCode_currentPart, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorSite;
  /** @generated */
  final int     casFeatCode_tumorSite;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTumorSite(int addr) {
        if (featOkTst && casFeat_tumorSite == null)
      jcas.throwFeatMissing("tumorSite", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tumorSite);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorSite(int addr, String v) {
        if (featOkTst && casFeat_tumorSite == null)
      jcas.throwFeatMissing("tumorSite", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_tumorSite, v);}
    
  
 
  /** @generated */
  final Feature casFeat_histopathologicalType;
  /** @generated */
  final int     casFeatCode_histopathologicalType;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getHistopathologicalType(int addr) {
        if (featOkTst && casFeat_histopathologicalType == null)
      jcas.throwFeatMissing("histopathologicalType", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_histopathologicalType);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHistopathologicalType(int addr, String v) {
        if (featOkTst && casFeat_histopathologicalType == null)
      jcas.throwFeatMissing("histopathologicalType", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_histopathologicalType, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorConfiguration;
  /** @generated */
  final int     casFeatCode_tumorConfiguration;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTumorConfiguration(int addr) {
        if (featOkTst && casFeat_tumorConfiguration == null)
      jcas.throwFeatMissing("tumorConfiguration", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tumorConfiguration);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorConfiguration(int addr, String v) {
        if (featOkTst && casFeat_tumorConfiguration == null)
      jcas.throwFeatMissing("tumorConfiguration", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_tumorConfiguration, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorDifferentiation;
  /** @generated */
  final int     casFeatCode_tumorDifferentiation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTumorDifferentiation(int addr) {
        if (featOkTst && casFeat_tumorDifferentiation == null)
      jcas.throwFeatMissing("tumorDifferentiation", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tumorDifferentiation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorDifferentiation(int addr, String v) {
        if (featOkTst && casFeat_tumorDifferentiation == null)
      jcas.throwFeatMissing("tumorDifferentiation", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_tumorDifferentiation, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sizeDimensionX;
  /** @generated */
  final int     casFeatCode_sizeDimensionX;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getSizeDimensionX(int addr) {
        if (featOkTst && casFeat_sizeDimensionX == null)
      jcas.throwFeatMissing("sizeDimensionX", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_sizeDimensionX);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSizeDimensionX(int addr, float v) {
        if (featOkTst && casFeat_sizeDimensionX == null)
      jcas.throwFeatMissing("sizeDimensionX", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    ll_cas.ll_setFloatValue(addr, casFeatCode_sizeDimensionX, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sizeDimensionY;
  /** @generated */
  final int     casFeatCode_sizeDimensionY;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getSizeDimensionY(int addr) {
        if (featOkTst && casFeat_sizeDimensionY == null)
      jcas.throwFeatMissing("sizeDimensionY", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_sizeDimensionY);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSizeDimensionY(int addr, float v) {
        if (featOkTst && casFeat_sizeDimensionY == null)
      jcas.throwFeatMissing("sizeDimensionY", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    ll_cas.ll_setFloatValue(addr, casFeatCode_sizeDimensionY, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sizeDimensionZ;
  /** @generated */
  final int     casFeatCode_sizeDimensionZ;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getSizeDimensionZ(int addr) {
        if (featOkTst && casFeat_sizeDimensionZ == null)
      jcas.throwFeatMissing("sizeDimensionZ", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_sizeDimensionZ);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSizeDimensionZ(int addr, float v) {
        if (featOkTst && casFeat_sizeDimensionZ == null)
      jcas.throwFeatMissing("sizeDimensionZ", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    ll_cas.ll_setFloatValue(addr, casFeatCode_sizeDimensionZ, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sizeDimensionMax;
  /** @generated */
  final int     casFeatCode_sizeDimensionMax;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getSizeDimensionMax(int addr) {
        if (featOkTst && casFeat_sizeDimensionMax == null)
      jcas.throwFeatMissing("sizeDimensionMax", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_sizeDimensionMax);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSizeDimensionMax(int addr, float v) {
        if (featOkTst && casFeat_sizeDimensionMax == null)
      jcas.throwFeatMissing("sizeDimensionMax", "edu.pitt.dbmi.xmeso.model.Model.XmesoTumorForm");
    ll_cas.ll_setFloatValue(addr, casFeatCode_sizeDimensionMax, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public XmesoTumorForm_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_currentPart = jcas.getRequiredFeatureDE(casType, "currentPart", "uima.cas.Integer", featOkTst);
    casFeatCode_currentPart  = (null == casFeat_currentPart) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_currentPart).getCode();

 
    casFeat_tumorSite = jcas.getRequiredFeatureDE(casType, "tumorSite", "uima.cas.String", featOkTst);
    casFeatCode_tumorSite  = (null == casFeat_tumorSite) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorSite).getCode();

 
    casFeat_histopathologicalType = jcas.getRequiredFeatureDE(casType, "histopathologicalType", "uima.cas.String", featOkTst);
    casFeatCode_histopathologicalType  = (null == casFeat_histopathologicalType) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_histopathologicalType).getCode();

 
    casFeat_tumorConfiguration = jcas.getRequiredFeatureDE(casType, "tumorConfiguration", "uima.cas.String", featOkTst);
    casFeatCode_tumorConfiguration  = (null == casFeat_tumorConfiguration) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorConfiguration).getCode();

 
    casFeat_tumorDifferentiation = jcas.getRequiredFeatureDE(casType, "tumorDifferentiation", "uima.cas.String", featOkTst);
    casFeatCode_tumorDifferentiation  = (null == casFeat_tumorDifferentiation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorDifferentiation).getCode();

 
    casFeat_sizeDimensionX = jcas.getRequiredFeatureDE(casType, "sizeDimensionX", "uima.cas.Float", featOkTst);
    casFeatCode_sizeDimensionX  = (null == casFeat_sizeDimensionX) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sizeDimensionX).getCode();

 
    casFeat_sizeDimensionY = jcas.getRequiredFeatureDE(casType, "sizeDimensionY", "uima.cas.Float", featOkTst);
    casFeatCode_sizeDimensionY  = (null == casFeat_sizeDimensionY) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sizeDimensionY).getCode();

 
    casFeat_sizeDimensionZ = jcas.getRequiredFeatureDE(casType, "sizeDimensionZ", "uima.cas.Float", featOkTst);
    casFeatCode_sizeDimensionZ  = (null == casFeat_sizeDimensionZ) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sizeDimensionZ).getCode();

 
    casFeat_sizeDimensionMax = jcas.getRequiredFeatureDE(casType, "sizeDimensionMax", "uima.cas.Float", featOkTst);
    casFeatCode_sizeDimensionMax  = (null == casFeat_sizeDimensionMax) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sizeDimensionMax).getCode();

  }
}



    