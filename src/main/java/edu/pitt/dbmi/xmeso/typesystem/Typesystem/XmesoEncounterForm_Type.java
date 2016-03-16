
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
public class XmesoEncounterForm_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (XmesoEncounterForm_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = XmesoEncounterForm_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new XmesoEncounterForm(addr, XmesoEncounterForm_Type.this);
  			   XmesoEncounterForm_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new XmesoEncounterForm(addr, XmesoEncounterForm_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = XmesoEncounterForm.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
 
  /** @generated */
  final Feature casFeat_surgicalProcedureCode;
  /** @generated */
  final int     casFeatCode_surgicalProcedureCode;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSurgicalProcedureCode(int addr) {
        if (featOkTst && casFeat_surgicalProcedureCode == null)
      jcas.throwFeatMissing("surgicalProcedureCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_surgicalProcedureCode);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSurgicalProcedureCode(int addr, String v) {
        if (featOkTst && casFeat_surgicalProcedureCode == null)
      jcas.throwFeatMissing("surgicalProcedureCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_surgicalProcedureCode, v);}
    
  
 
  /** @generated */
  final Feature casFeat_histologicTypeCode;
  /** @generated */
  final int     casFeatCode_histologicTypeCode;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getHistologicTypeCode(int addr) {
        if (featOkTst && casFeat_histologicTypeCode == null)
      jcas.throwFeatMissing("histologicTypeCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_histologicTypeCode);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setHistologicTypeCode(int addr, String v) {
        if (featOkTst && casFeat_histologicTypeCode == null)
      jcas.throwFeatMissing("histologicTypeCode", "edu.pitt.dbmi.xmeso.typesystem.Typesystem.XmesoEncounterForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_histologicTypeCode, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public XmesoEncounterForm_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_surgicalProcedureCode = jcas.getRequiredFeatureDE(casType, "surgicalProcedureCode", "uima.cas.String", featOkTst);
    casFeatCode_surgicalProcedureCode  = (null == casFeat_surgicalProcedureCode) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_surgicalProcedureCode).getCode();

 
    casFeat_histologicTypeCode = jcas.getRequiredFeatureDE(casType, "histologicTypeCode", "uima.cas.String", featOkTst);
    casFeatCode_histologicTypeCode  = (null == casFeat_histologicTypeCode) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_histologicTypeCode).getCode();

  }
}



    