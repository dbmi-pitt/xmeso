
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

/** Noble Tools Semantic Type
 * Updated by JCasGen Thu Feb 04 17:17:10 EST 2016
 * @generated */
public class SemanticType_Type extends Annotation_Type {
  /** @generated 
   * @return the generator for this type
   */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (SemanticType_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = SemanticType_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new SemanticType(addr, SemanticType_Type.this);
  			   SemanticType_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new SemanticType(addr, SemanticType_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = SemanticType.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.pitt.dbmi.nlp.noble.uima.types.SemanticType");
 
  /** @generated */
  final Feature casFeat_tui;
  /** @generated */
  final int     casFeatCode_tui;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getTui(int addr) {
        if (featOkTst && casFeat_tui == null)
      jcas.throwFeatMissing("tui", "edu.pitt.dbmi.nlp.noble.uima.types.SemanticType");
    return ll_cas.ll_getStringValue(addr, casFeatCode_tui);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTui(int addr, String v) {
        if (featOkTst && casFeat_tui == null)
      jcas.throwFeatMissing("tui", "edu.pitt.dbmi.nlp.noble.uima.types.SemanticType");
    ll_cas.ll_setStringValue(addr, casFeatCode_tui, v);}
    
  
 
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
      jcas.throwFeatMissing("sty", "edu.pitt.dbmi.nlp.noble.uima.types.SemanticType");
    return ll_cas.ll_getStringValue(addr, casFeatCode_sty);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSty(int addr, String v) {
        if (featOkTst && casFeat_sty == null)
      jcas.throwFeatMissing("sty", "edu.pitt.dbmi.nlp.noble.uima.types.SemanticType");
    ll_cas.ll_setStringValue(addr, casFeatCode_sty, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public SemanticType_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_tui = jcas.getRequiredFeatureDE(casType, "tui", "uima.cas.String", featOkTst);
    casFeatCode_tui  = (null == casFeat_tui) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tui).getCode();

 
    casFeat_sty = jcas.getRequiredFeatureDE(casType, "sty", "uima.cas.String", featOkTst);
    casFeatCode_sty  = (null == casFeat_sty) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sty).getCode();

  }
}



    