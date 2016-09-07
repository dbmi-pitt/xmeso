
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

/** Type defined in edu.pitt.dbmi.xmeso.model.Model
 * Updated by JCasGen Wed Sep 07 12:35:01 EDT 2016
 * @generated */
public class XmesoSize_Type extends XmesoNamedEntity_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (XmesoSize_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = XmesoSize_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new XmesoSize(addr, XmesoSize_Type.this);
  			   XmesoSize_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new XmesoSize(addr, XmesoSize_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = XmesoSize.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
 
  /** @generated */
  final Feature casFeat_maxDim;
  /** @generated */
  final int     casFeatCode_maxDim;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getMaxDim(int addr) {
        if (featOkTst && casFeat_maxDim == null)
      jcas.throwFeatMissing("maxDim", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_maxDim);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setMaxDim(int addr, float v) {
        if (featOkTst && casFeat_maxDim == null)
      jcas.throwFeatMissing("maxDim", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    ll_cas.ll_setFloatValue(addr, casFeatCode_maxDim, v);}
    
  
 
  /** @generated */
  final Feature casFeat_dimOne;
  /** @generated */
  final int     casFeatCode_dimOne;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getDimOne(int addr) {
        if (featOkTst && casFeat_dimOne == null)
      jcas.throwFeatMissing("dimOne", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_dimOne);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDimOne(int addr, float v) {
        if (featOkTst && casFeat_dimOne == null)
      jcas.throwFeatMissing("dimOne", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    ll_cas.ll_setFloatValue(addr, casFeatCode_dimOne, v);}
    
  
 
  /** @generated */
  final Feature casFeat_dimTwo;
  /** @generated */
  final int     casFeatCode_dimTwo;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getDimTwo(int addr) {
        if (featOkTst && casFeat_dimTwo == null)
      jcas.throwFeatMissing("dimTwo", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_dimTwo);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDimTwo(int addr, float v) {
        if (featOkTst && casFeat_dimTwo == null)
      jcas.throwFeatMissing("dimTwo", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    ll_cas.ll_setFloatValue(addr, casFeatCode_dimTwo, v);}
    
  
 
  /** @generated */
  final Feature casFeat_dimThree;
  /** @generated */
  final int     casFeatCode_dimThree;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public float getDimThree(int addr) {
        if (featOkTst && casFeat_dimThree == null)
      jcas.throwFeatMissing("dimThree", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return ll_cas.ll_getFloatValue(addr, casFeatCode_dimThree);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDimThree(int addr, float v) {
        if (featOkTst && casFeat_dimThree == null)
      jcas.throwFeatMissing("dimThree", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    ll_cas.ll_setFloatValue(addr, casFeatCode_dimThree, v);}
    
  
 
  /** @generated */
  final Feature casFeat_uom;
  /** @generated */
  final int     casFeatCode_uom;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getUom(int addr) {
        if (featOkTst && casFeat_uom == null)
      jcas.throwFeatMissing("uom", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    return ll_cas.ll_getStringValue(addr, casFeatCode_uom);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setUom(int addr, String v) {
        if (featOkTst && casFeat_uom == null)
      jcas.throwFeatMissing("uom", "edu.pitt.dbmi.xmeso.model.Model.XmesoSize");
    ll_cas.ll_setStringValue(addr, casFeatCode_uom, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public XmesoSize_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_maxDim = jcas.getRequiredFeatureDE(casType, "maxDim", "uima.cas.Float", featOkTst);
    casFeatCode_maxDim  = (null == casFeat_maxDim) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_maxDim).getCode();

 
    casFeat_dimOne = jcas.getRequiredFeatureDE(casType, "dimOne", "uima.cas.Float", featOkTst);
    casFeatCode_dimOne  = (null == casFeat_dimOne) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dimOne).getCode();

 
    casFeat_dimTwo = jcas.getRequiredFeatureDE(casType, "dimTwo", "uima.cas.Float", featOkTst);
    casFeatCode_dimTwo  = (null == casFeat_dimTwo) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dimTwo).getCode();

 
    casFeat_dimThree = jcas.getRequiredFeatureDE(casType, "dimThree", "uima.cas.Float", featOkTst);
    casFeatCode_dimThree  = (null == casFeat_dimThree) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dimThree).getCode();

 
    casFeat_uom = jcas.getRequiredFeatureDE(casType, "uom", "uima.cas.String", featOkTst);
    casFeatCode_uom  = (null == casFeat_uom) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_uom).getCode();

  }
}



    