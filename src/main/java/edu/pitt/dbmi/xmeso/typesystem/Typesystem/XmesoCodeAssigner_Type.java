
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
public class XmesoCodeAssigner_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (XmesoCodeAssigner_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = XmesoCodeAssigner_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new XmesoCodeAssigner(addr, XmesoCodeAssigner_Type.this);
  			   XmesoCodeAssigner_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new XmesoCodeAssigner(addr, XmesoCodeAssigner_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = XmesoCodeAssigner.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
 
  /** @generated */
  final Feature casFeat_tumorForm;
  /** @generated */
  final int     casFeatCode_tumorForm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTumorForm(int addr) {
        if (featOkTst && casFeat_tumorForm == null)
      jcas.throwFeatMissing("tumorForm", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tumorForm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorForm(int addr, int v) {
        if (featOkTst && casFeat_tumorForm == null)
      jcas.throwFeatMissing("tumorForm", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    ll_cas.ll_setRefValue(addr, casFeatCode_tumorForm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorSite;
  /** @generated */
  final int     casFeatCode_tumorSite;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTumorSite(int addr) {
        if (featOkTst && casFeat_tumorSite == null)
      jcas.throwFeatMissing("tumorSite", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tumorSite);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorSite(int addr, int v) {
        if (featOkTst && casFeat_tumorSite == null)
      jcas.throwFeatMissing("tumorSite", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    ll_cas.ll_setRefValue(addr, casFeatCode_tumorSite, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorConfiguration;
  /** @generated */
  final int     casFeatCode_tumorConfiguration;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTumorConfiguration(int addr) {
        if (featOkTst && casFeat_tumorConfiguration == null)
      jcas.throwFeatMissing("tumorConfiguration", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tumorConfiguration);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorConfiguration(int addr, int v) {
        if (featOkTst && casFeat_tumorConfiguration == null)
      jcas.throwFeatMissing("tumorConfiguration", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    ll_cas.ll_setRefValue(addr, casFeatCode_tumorConfiguration, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tumorDifferentiation;
  /** @generated */
  final int     casFeatCode_tumorDifferentiation;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTumorDifferentiation(int addr) {
        if (featOkTst && casFeat_tumorDifferentiation == null)
      jcas.throwFeatMissing("tumorDifferentiation", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tumorDifferentiation);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTumorDifferentiation(int addr, int v) {
        if (featOkTst && casFeat_tumorDifferentiation == null)
      jcas.throwFeatMissing("tumorDifferentiation", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoCodeAssigner");
    ll_cas.ll_setRefValue(addr, casFeatCode_tumorDifferentiation, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public XmesoCodeAssigner_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tumorForm = jcas.getRequiredFeatureDE(casType, "tumorForm", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorForm", featOkTst);
    casFeatCode_tumorForm  = (null == casFeat_tumorForm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorForm).getCode();

 
    casFeat_tumorSite = jcas.getRequiredFeatureDE(casType, "tumorSite", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorSite", featOkTst);
    casFeatCode_tumorSite  = (null == casFeat_tumorSite) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorSite).getCode();

 
    casFeat_tumorConfiguration = jcas.getRequiredFeatureDE(casType, "tumorConfiguration", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorConfiguration", featOkTst);
    casFeatCode_tumorConfiguration  = (null == casFeat_tumorConfiguration) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorConfiguration).getCode();

 
    casFeat_tumorDifferentiation = jcas.getRequiredFeatureDE(casType, "tumorDifferentiation", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoTumorDifferentiation", featOkTst);
    casFeatCode_tumorDifferentiation  = (null == casFeat_tumorDifferentiation) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tumorDifferentiation).getCode();

  }
}



    