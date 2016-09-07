
/* First created by JCasGen Wed Sep 07 12:38:01 EDT 2016 */
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
 * Updated by JCasGen Wed Sep 07 12:38:01 EDT 2016
 * @generated */
public class XmesoCaseForm_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (XmesoCaseForm_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = XmesoCaseForm_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new XmesoCaseForm(addr, XmesoCaseForm_Type.this);
  			   XmesoCaseForm_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new XmesoCaseForm(addr, XmesoCaseForm_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = XmesoCaseForm.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
 
  /** @generated */
  final Feature casFeat_surgicalProcedure;
  /** @generated */
  final int     casFeatCode_surgicalProcedure;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSurgicalProcedure(int addr) {
        if (featOkTst && casFeat_surgicalProcedure == null)
      jcas.throwFeatMissing("surgicalProcedure", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_surgicalProcedure);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSurgicalProcedure(int addr, String v) {
        if (featOkTst && casFeat_surgicalProcedure == null)
      jcas.throwFeatMissing("surgicalProcedure", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_surgicalProcedure, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ultrastructuralFindings;
  /** @generated */
  final int     casFeatCode_ultrastructuralFindings;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getUltrastructuralFindings(int addr) {
        if (featOkTst && casFeat_ultrastructuralFindings == null)
      jcas.throwFeatMissing("ultrastructuralFindings", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ultrastructuralFindings);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setUltrastructuralFindings(int addr, String v) {
        if (featOkTst && casFeat_ultrastructuralFindings == null)
      jcas.throwFeatMissing("ultrastructuralFindings", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_ultrastructuralFindings, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lymphNodesExamined;
  /** @generated */
  final int     casFeatCode_lymphNodesExamined;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLymphNodesExamined(int addr) {
        if (featOkTst && casFeat_lymphNodesExamined == null)
      jcas.throwFeatMissing("lymphNodesExamined", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lymphNodesExamined);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLymphNodesExamined(int addr, String v) {
        if (featOkTst && casFeat_lymphNodesExamined == null)
      jcas.throwFeatMissing("lymphNodesExamined", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_lymphNodesExamined, v);}
    
  
 
  /** @generated */
  final Feature casFeat_specialStain;
  /** @generated */
  final int     casFeatCode_specialStain;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSpecialStain(int addr) {
        if (featOkTst && casFeat_specialStain == null)
      jcas.throwFeatMissing("specialStain", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_specialStain);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSpecialStain(int addr, String v) {
        if (featOkTst && casFeat_specialStain == null)
      jcas.throwFeatMissing("specialStain", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_specialStain, v);}
    
  
 
  /** @generated */
  final Feature casFeat_invasiveTumor;
  /** @generated */
  final int     casFeatCode_invasiveTumor;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getInvasiveTumor(int addr) {
        if (featOkTst && casFeat_invasiveTumor == null)
      jcas.throwFeatMissing("invasiveTumor", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_invasiveTumor);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInvasiveTumor(int addr, String v) {
        if (featOkTst && casFeat_invasiveTumor == null)
      jcas.throwFeatMissing("invasiveTumor", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_invasiveTumor, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public XmesoCaseForm_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_surgicalProcedure = jcas.getRequiredFeatureDE(casType, "surgicalProcedure", "uima.cas.String", featOkTst);
    casFeatCode_surgicalProcedure  = (null == casFeat_surgicalProcedure) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_surgicalProcedure).getCode();

 
    casFeat_ultrastructuralFindings = jcas.getRequiredFeatureDE(casType, "ultrastructuralFindings", "uima.cas.String", featOkTst);
    casFeatCode_ultrastructuralFindings  = (null == casFeat_ultrastructuralFindings) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ultrastructuralFindings).getCode();

 
    casFeat_lymphNodesExamined = jcas.getRequiredFeatureDE(casType, "lymphNodesExamined", "uima.cas.String", featOkTst);
    casFeatCode_lymphNodesExamined  = (null == casFeat_lymphNodesExamined) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lymphNodesExamined).getCode();

 
    casFeat_specialStain = jcas.getRequiredFeatureDE(casType, "specialStain", "uima.cas.String", featOkTst);
    casFeatCode_specialStain  = (null == casFeat_specialStain) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_specialStain).getCode();

 
    casFeat_invasiveTumor = jcas.getRequiredFeatureDE(casType, "invasiveTumor", "uima.cas.String", featOkTst);
    casFeatCode_invasiveTumor  = (null == casFeat_invasiveTumor) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_invasiveTumor).getCode();

  }
}



    