
/* First created by JCasGen Fri Sep 02 12:50:58 EDT 2016 */
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
 * Updated by JCasGen Tue Sep 06 11:14:50 EDT 2016
 * @generated */
public class RomanPartNumber_Type extends PartNumber_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (RomanPartNumber_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = RomanPartNumber_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new RomanPartNumber(addr, RomanPartNumber_Type.this);
  			   RomanPartNumber_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new RomanPartNumber(addr, RomanPartNumber_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = RomanPartNumber.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.model.Model.RomanPartNumber");
 
  /** @generated */
  final Feature casFeat_romanValue;
  /** @generated */
  final int     casFeatCode_romanValue;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRomanValue(int addr) {
        if (featOkTst && casFeat_romanValue == null)
      jcas.throwFeatMissing("romanValue", "edu.pitt.dbmi.xmeso.model.Model.RomanPartNumber");
    return ll_cas.ll_getStringValue(addr, casFeatCode_romanValue);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRomanValue(int addr, String v) {
        if (featOkTst && casFeat_romanValue == null)
      jcas.throwFeatMissing("romanValue", "edu.pitt.dbmi.xmeso.model.Model.RomanPartNumber");
    ll_cas.ll_setStringValue(addr, casFeatCode_romanValue, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public RomanPartNumber_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_romanValue = jcas.getRequiredFeatureDE(casType, "romanValue", "uima.cas.String", featOkTst);
    casFeatCode_romanValue  = (null == casFeat_romanValue) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_romanValue).getCode();

  }
}



    