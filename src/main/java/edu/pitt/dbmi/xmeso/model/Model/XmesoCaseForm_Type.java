
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
    
  
 
  /** @generated */
  final Feature casFeat_surgicalProcedureTerm;
  /** @generated */
  final int     casFeatCode_surgicalProcedureTerm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSurgicalProcedureTerm(int addr) {
        if (featOkTst && casFeat_surgicalProcedureTerm == null)
      jcas.throwFeatMissing("surgicalProcedureTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_surgicalProcedureTerm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSurgicalProcedureTerm(int addr, String v) {
        if (featOkTst && casFeat_surgicalProcedureTerm == null)
      jcas.throwFeatMissing("surgicalProcedureTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_surgicalProcedureTerm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ultrastructuralFindingsTerm;
  /** @generated */
  final int     casFeatCode_ultrastructuralFindingsTerm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getUltrastructuralFindingsTerm(int addr) {
        if (featOkTst && casFeat_ultrastructuralFindingsTerm == null)
      jcas.throwFeatMissing("ultrastructuralFindingsTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ultrastructuralFindingsTerm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setUltrastructuralFindingsTerm(int addr, String v) {
        if (featOkTst && casFeat_ultrastructuralFindingsTerm == null)
      jcas.throwFeatMissing("ultrastructuralFindingsTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_ultrastructuralFindingsTerm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_lymphNodesExaminedTerm;
  /** @generated */
  final int     casFeatCode_lymphNodesExaminedTerm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getLymphNodesExaminedTerm(int addr) {
        if (featOkTst && casFeat_lymphNodesExaminedTerm == null)
      jcas.throwFeatMissing("lymphNodesExaminedTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_lymphNodesExaminedTerm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setLymphNodesExaminedTerm(int addr, String v) {
        if (featOkTst && casFeat_lymphNodesExaminedTerm == null)
      jcas.throwFeatMissing("lymphNodesExaminedTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_lymphNodesExaminedTerm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_specialStainTerm;
  /** @generated */
  final int     casFeatCode_specialStainTerm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getSpecialStainTerm(int addr) {
        if (featOkTst && casFeat_specialStainTerm == null)
      jcas.throwFeatMissing("specialStainTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_specialStainTerm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSpecialStainTerm(int addr, String v) {
        if (featOkTst && casFeat_specialStainTerm == null)
      jcas.throwFeatMissing("specialStainTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_specialStainTerm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_invasiveTumorTerm;
  /** @generated */
  final int     casFeatCode_invasiveTumorTerm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getInvasiveTumorTerm(int addr) {
        if (featOkTst && casFeat_invasiveTumorTerm == null)
      jcas.throwFeatMissing("invasiveTumorTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    return ll_cas.ll_getStringValue(addr, casFeatCode_invasiveTumorTerm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setInvasiveTumorTerm(int addr, String v) {
        if (featOkTst && casFeat_invasiveTumorTerm == null)
      jcas.throwFeatMissing("invasiveTumorTerm", "edu.pitt.dbmi.xmeso.model.Model.XmesoCaseForm");
    ll_cas.ll_setStringValue(addr, casFeatCode_invasiveTumorTerm, v);}
    
  



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

 
    casFeat_surgicalProcedureTerm = jcas.getRequiredFeatureDE(casType, "surgicalProcedureTerm", "uima.cas.String", featOkTst);
    casFeatCode_surgicalProcedureTerm  = (null == casFeat_surgicalProcedureTerm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_surgicalProcedureTerm).getCode();

 
    casFeat_ultrastructuralFindingsTerm = jcas.getRequiredFeatureDE(casType, "ultrastructuralFindingsTerm", "uima.cas.String", featOkTst);
    casFeatCode_ultrastructuralFindingsTerm  = (null == casFeat_ultrastructuralFindingsTerm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ultrastructuralFindingsTerm).getCode();

 
    casFeat_lymphNodesExaminedTerm = jcas.getRequiredFeatureDE(casType, "lymphNodesExaminedTerm", "uima.cas.String", featOkTst);
    casFeatCode_lymphNodesExaminedTerm  = (null == casFeat_lymphNodesExaminedTerm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_lymphNodesExaminedTerm).getCode();

 
    casFeat_specialStainTerm = jcas.getRequiredFeatureDE(casType, "specialStainTerm", "uima.cas.String", featOkTst);
    casFeatCode_specialStainTerm  = (null == casFeat_specialStainTerm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_specialStainTerm).getCode();

 
    casFeat_invasiveTumorTerm = jcas.getRequiredFeatureDE(casType, "invasiveTumorTerm", "uima.cas.String", featOkTst);
    casFeatCode_invasiveTumorTerm  = (null == casFeat_invasiveTumorTerm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_invasiveTumorTerm).getCode();

  }
}



    