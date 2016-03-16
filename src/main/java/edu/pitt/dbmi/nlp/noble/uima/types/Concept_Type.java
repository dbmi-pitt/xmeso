
/* First created by JCasGen Tue Jan 26 17:14:56 EST 2016 */
package edu.pitt.dbmi.nlp.noble.uima.types;

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

/** Represents  a NobleTools Concept.
 * Updated by JCasGen Thu Feb 04 17:17:10 EST 2016
 * @generated */
public class Concept_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Concept_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Concept_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Concept(addr, Concept_Type.this);
  			   Concept_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Concept(addr, Concept_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Concept.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.nlp.noble.uima.types.Concept");
 
  /** @generated */
  final Feature casFeat_cui;
  /** @generated */
  final int     casFeatCode_cui;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCui(int addr) {
        if (featOkTst && casFeat_cui == null)
      jcas.throwFeatMissing("cui", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_cui);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCui(int addr, String v) {
        if (featOkTst && casFeat_cui == null)
      jcas.throwFeatMissing("cui", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    ll_cas.ll_setStringValue(addr, casFeatCode_cui, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tuis;
  /** @generated */
  final int     casFeatCode_tuis;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTuis(int addr) {
        if (featOkTst && casFeat_tuis == null)
      jcas.throwFeatMissing("tuis", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tuis);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTuis(int addr, int v) {
        if (featOkTst && casFeat_tuis == null)
      jcas.throwFeatMissing("tuis", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    ll_cas.ll_setRefValue(addr, casFeatCode_tuis, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getTuis(int addr, int i) {
        if (featOkTst && casFeat_tuis == null)
      jcas.throwFeatMissing("tuis", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_tuis), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_tuis), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_tuis), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setTuis(int addr, int i, int v) {
        if (featOkTst && casFeat_tuis == null)
      jcas.throwFeatMissing("tuis", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_tuis), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_tuis), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_tuis), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_synonyms;
  /** @generated */
  final int     casFeatCode_synonyms;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSynonyms(int addr) {
        if (featOkTst && casFeat_synonyms == null)
      jcas.throwFeatMissing("synonyms", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return ll_cas.ll_getRefValue(addr, casFeatCode_synonyms);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSynonyms(int addr, int v) {
        if (featOkTst && casFeat_synonyms == null)
      jcas.throwFeatMissing("synonyms", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    ll_cas.ll_setRefValue(addr, casFeatCode_synonyms, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getSynonyms(int addr, int i) {
        if (featOkTst && casFeat_synonyms == null)
      jcas.throwFeatMissing("synonyms", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_synonyms), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_synonyms), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_synonyms), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setSynonyms(int addr, int i, String v) {
        if (featOkTst && casFeat_synonyms == null)
      jcas.throwFeatMissing("synonyms", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_synonyms), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_synonyms), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_synonyms), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_cn;
  /** @generated */
  final int     casFeatCode_cn;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCn(int addr) {
        if (featOkTst && casFeat_cn == null)
      jcas.throwFeatMissing("cn", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_cn);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCn(int addr, String v) {
        if (featOkTst && casFeat_cn == null)
      jcas.throwFeatMissing("cn", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    ll_cas.ll_setStringValue(addr, casFeatCode_cn, v);}
    
  
 
  /** @generated */
  final Feature casFeat_preferredTerm;
  /** @generated */
  final int     casFeatCode_preferredTerm;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getPreferredTerm(int addr) {
        if (featOkTst && casFeat_preferredTerm == null)
      jcas.throwFeatMissing("preferredTerm", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_preferredTerm);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setPreferredTerm(int addr, String v) {
        if (featOkTst && casFeat_preferredTerm == null)
      jcas.throwFeatMissing("preferredTerm", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    ll_cas.ll_setStringValue(addr, casFeatCode_preferredTerm, v);}
    
  
 
  /** @generated */
  final Feature casFeat_definition;
  /** @generated */
  final int     casFeatCode_definition;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDefinition(int addr) {
        if (featOkTst && casFeat_definition == null)
      jcas.throwFeatMissing("definition", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    return ll_cas.ll_getStringValue(addr, casFeatCode_definition);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDefinition(int addr, String v) {
        if (featOkTst && casFeat_definition == null)
      jcas.throwFeatMissing("definition", "edu.pitt.dbmi.nlp.noble.uima.types.Concept");
    ll_cas.ll_setStringValue(addr, casFeatCode_definition, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public Concept_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_cui = jcas.getRequiredFeatureDE(casType, "cui", "uima.cas.String", featOkTst);
    casFeatCode_cui  = (null == casFeat_cui) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_cui).getCode();

 
    casFeat_tuis = jcas.getRequiredFeatureDE(casType, "tuis", "uima.cas.FSArray", featOkTst);
    casFeatCode_tuis  = (null == casFeat_tuis) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tuis).getCode();

 
    casFeat_synonyms = jcas.getRequiredFeatureDE(casType, "synonyms", "uima.cas.StringArray", featOkTst);
    casFeatCode_synonyms  = (null == casFeat_synonyms) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_synonyms).getCode();

 
    casFeat_cn = jcas.getRequiredFeatureDE(casType, "cn", "uima.cas.String", featOkTst);
    casFeatCode_cn  = (null == casFeat_cn) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_cn).getCode();

 
    casFeat_preferredTerm = jcas.getRequiredFeatureDE(casType, "preferredTerm", "uima.cas.String", featOkTst);
    casFeatCode_preferredTerm  = (null == casFeat_preferredTerm) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_preferredTerm).getCode();

 
    casFeat_definition = jcas.getRequiredFeatureDE(casType, "definition", "uima.cas.String", featOkTst);
    casFeatCode_definition  = (null == casFeat_definition) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_definition).getCode();

  }
}



    