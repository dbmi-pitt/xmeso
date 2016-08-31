
/* First created by JCasGen Wed Aug 31 14:53:44 EDT 2016 */
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
 * Updated by JCasGen Wed Aug 31 14:53:44 EDT 2016
 * @generated */
public class PartNumber_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (PartNumber_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = PartNumber_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new PartNumber(addr, PartNumber_Type.this);
  			   PartNumber_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new PartNumber(addr, PartNumber_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = PartNumber.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.model.Model.PartNumber");
 
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
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    return ll_cas.ll_getIntValue(addr, casFeatCode_partNumber);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPartNumber(int addr, int v) {
        if (featOkTst && casFeat_partNumber == null)
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    ll_cas.ll_setIntValue(addr, casFeatCode_partNumber, v);}
    
  
 
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
      jcas.throwFeatMissing("genNumber", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    return ll_cas.ll_getIntValue(addr, casFeatCode_genNumber);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGenNumber(int addr, int v) {
        if (featOkTst && casFeat_genNumber == null)
      jcas.throwFeatMissing("genNumber", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    ll_cas.ll_setIntValue(addr, casFeatCode_genNumber, v);}
    
  
 
  /** @generated */
  final Feature casFeat_genSeq;
  /** @generated */
  final int     casFeatCode_genSeq;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getGenSeq(int addr) {
        if (featOkTst && casFeat_genSeq == null)
      jcas.throwFeatMissing("genSeq", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    return ll_cas.ll_getIntValue(addr, casFeatCode_genSeq);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setGenSeq(int addr, int v) {
        if (featOkTst && casFeat_genSeq == null)
      jcas.throwFeatMissing("genSeq", "edu.pitt.dbmi.xmeso.model.Model.PartNumber");
    ll_cas.ll_setIntValue(addr, casFeatCode_genSeq, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public PartNumber_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_partNumber = jcas.getRequiredFeatureDE(casType, "partNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_partNumber  = (null == casFeat_partNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_partNumber).getCode();

 
    casFeat_genNumber = jcas.getRequiredFeatureDE(casType, "genNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_genNumber  = (null == casFeat_genNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_genNumber).getCode();

 
    casFeat_genSeq = jcas.getRequiredFeatureDE(casType, "genSeq", "uima.cas.Integer", featOkTst);
    casFeatCode_genSeq  = (null == casFeat_genSeq) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_genSeq).getCode();

  }
}



    