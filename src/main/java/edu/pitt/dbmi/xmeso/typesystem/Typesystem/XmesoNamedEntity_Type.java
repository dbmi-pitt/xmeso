
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
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity");
 
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
      jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sectionName);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSectionName(int addr, String v) {
        if (featOkTst && casFeat_sectionName == null)
      jcas.throwFeatMissing("sectionName", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity");
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
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity");
    return ll_cas.ll_getIntValue(addr, casFeatCode_partNumber);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPartNumber(int addr, int v) {
        if (featOkTst && casFeat_partNumber == null)
      jcas.throwFeatMissing("partNumber", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity");
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
      jcas.throwFeatMissing("snomedCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_snomedCode);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSnomedCode(int addr, String v) {
        if (featOkTst && casFeat_snomedCode == null)
      jcas.throwFeatMissing("snomedCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_snomedCode, v);}
    
  
 
  /** @generated */
  final Feature casFeat_sty;
  /** @generated */
  final int     casFeatCode_sty;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSty(int addr) {
        if (featOkTst && casFeat_sty == null)
      jcas.throwFeatMissing("sty", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sty);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSty(int addr, String v) {
        if (featOkTst && casFeat_sty == null)
      jcas.throwFeatMissing("sty", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoNamedEntity");
    ll_cas.ll_setStringValue(addr, casFeatCode_sty, v);}
    
  



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

 
    casFeat_partNumber = jcas.getRequiredFeatureDE(casType, "partNumber", "uima.cas.Integer", featOkTst);
    casFeatCode_partNumber  = (null == casFeat_partNumber) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_partNumber).getCode();

 
    casFeat_snomedCode = jcas.getRequiredFeatureDE(casType, "snomedCode", "uima.cas.String", featOkTst);
    casFeatCode_snomedCode  = (null == casFeat_snomedCode) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_snomedCode).getCode();

 
    casFeat_sty = jcas.getRequiredFeatureDE(casType, "sty", "uima.cas.String", featOkTst);
    casFeatCode_sty  = (null == casFeat_sty) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sty).getCode();

  }
}



    