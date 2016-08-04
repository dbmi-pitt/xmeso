
/* First created by JCasGen Mon Aug 01 15:50:48 EDT 2016 */
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
 * Updated by JCasGen Thu Aug 04 16:21:38 EDT 2016
 * @generated */
public class PartHeaderGenerator_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PartHeaderGenerator_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PartHeaderGenerator_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PartHeaderGenerator(addr, PartHeaderGenerator_Type.this);
  			   PartHeaderGenerator_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PartHeaderGenerator(addr, PartHeaderGenerator_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = PartHeaderGenerator.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator");
 
  /** @generated */
  final Feature casFeat_genNumber;
  /** @generated */
  final int     casFeatCode_genNumber;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getGenNumber(int addr) {
        if (featOkTst && casFeat_genNumber == null)
      jcas.throwFeatMissing("genNumber", "edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator");
    return ll_cas.ll_getIntValue(addr, casFeatCode_genNumber);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGenNumber(int addr, int v) {
        if (featOkTst && casFeat_genNumber == null)
      jcas.throwFeatMissing("genNumber", "edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator");
    ll_cas.ll_setIntValue(addr, casFeatCode_genNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_genCount;
  /** @generated */
  final int     casFeatCode_genCount;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getGenCount(int addr) {
        if (featOkTst && casFeat_genCount == null)
      jcas.throwFeatMissing("genCount", "edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator");
    return ll_cas.ll_getIntValue(addr, casFeatCode_genCount);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGenCount(int addr, int v) {
        if (featOkTst && casFeat_genCount == null)
      jcas.throwFeatMissing("genCount", "edu.pitt.dbmi.xmeso.model.Model.PartHeaderGenerator");
    ll_cas.ll_setIntValue(addr, casFeatCode_genCount, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public PartHeaderGenerator_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_genNumber = jcas.getRequiredFeatureDE(casType, "genNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_genNumber  = (null == casFeat_genNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_genNumber).getCode();

 
    casFeat_genCount = jcas.getRequiredFeatureDE(casType, "genCount", "uima.cas.Integer", featOkTst);
    casFeatCode_genCount  = (null == casFeat_genCount) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_genCount).getCode();

  }
}



    