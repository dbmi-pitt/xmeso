package edu.pitt.dbmi.xmeso.qa;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.I2b2DemoDataSourceManager;
import edu.pitt.dbmi.xmeso.i2b2.orm.i2b2data.ObservationFact;

public class QaAnalyzer {
	
	private I2b2DemoDataSourceManager demoDataMgr;
	private Session demoDataSession;
	
	private double tps = 0.0d;
	private double fps = 0.0d;
	private double fns = 0.0d;
	
	public static void main(String[] args) {
		QaAnalyzer qa = new QaAnalyzer();
		qa.execute();
	}

	private void execute() {
		establishDemoDbConnectivity();
		gatherStatistics();
		reportStatistics();
	}
	
	private void establishDemoDbConnectivity() {
		demoDataMgr = new I2b2DemoDataSourceManager();
		demoDataMgr
				.setHibernateConnectionUrl("jdbc:oracle:thin:@dbmi-i2b2-dev05.dbmi.pitt.edu:1521:xe");
		demoDataMgr.setHibernateDriver("oracle.jdbc.driver.OracleDriver");
		demoDataMgr.setHibernateShowSql("true");
		demoDataSession = demoDataMgr.getSession();
	}
	
	private void reportStatistics() {
		System.out.println("tps = " + tps);
		System.out.println("fps = " + fps);
		System.out.println("fns = " + fns);
		System.out.println("Prec: " + tps / (tps + fps));
		System.out.println("Reca: " + tps / (tps + fns));
	}
	
	@SuppressWarnings("unchecked")
	private void gatherStatistics() {
		String hql = "from ObservationFact o where ";
		hql += " o.sourcesystemCd like :sourceSystemCd ";
		Query query = demoDataSession.createQuery(hql);
		query.setString("sourceSystemCd", "Xmeso");
		List<ObservationFact> observationFacts = query.list();
		for (ObservationFact observationFact : observationFacts) {
			if (verifyAgainstGold(observationFact)) {
				tps += 1.0d;
			}
			else {
				fps += 1.0d;
			}
		}
		
	    hql = "from ObservationFact o where ";
		hql += " o.sourcesystemCd like :sourceSystemCd ";
		query = demoDataSession.createQuery(hql);
		query.setString("sourceSystemCd", "GoldEtl");
		observationFacts = query.list();
		for (ObservationFact observationFact : observationFacts) {
			if (!verifyAgainstXmeso(observationFact)) {
				fns += 1.0d;
			}
		}
	}

	private boolean verifyAgainstGold(ObservationFact observationFact) {
		String hql = "from ObservationFact o where ";
		hql += " o.id.patientNum = :obsPatientNum and ";
		hql += " o.id.encounterNum = :obsEncounterNum and ";
		hql += " o.id.instanceNum = :obsInstanceNum and ";
		hql += " o.id.conceptCd = :obsConceptCd and ";
		hql += " o.sourcesystemCd like :sourceSystemCd ";
		Query query = demoDataSession.createQuery(hql);
		query.setBigDecimal("obsPatientNum", observationFact.getId().getPatientNum());
		query.setBigDecimal("obsEncounterNum", observationFact.getId().getEncounterNum());
		query.setString("obsConceptCd", observationFact.getId().getConceptCd());
		query.setLong("obsInstanceNum", observationFact.getId().getInstanceNum());
		query.setString("sourceSystemCd", "GoldEtl");
		ObservationFact goldObservationFact = (ObservationFact) query.uniqueResult();
		return goldObservationFact != null;
	}
	
	private boolean verifyAgainstXmeso(ObservationFact observationFact) {
		String hql = "from ObservationFact o where ";
		hql += " o.id.patientNum = :obsPatientNum and ";
		hql += " o.id.encounterNum = :obsEncounterNum and ";
		hql += " o.id.instanceNum = :obsInstanceNum and ";
		hql += " o.id.conceptCd = :obsConceptCd and ";
		hql += " o.sourcesystemCd like :sourceSystemCd ";
		Query query = demoDataSession.createQuery(hql);
		query.setBigDecimal("obsPatientNum", observationFact.getId().getPatientNum());
		query.setBigDecimal("obsEncounterNum", observationFact.getId().getEncounterNum());
		query.setString("obsConceptCd", observationFact.getId().getConceptCd());
		query.setLong("obsInstanceNum", observationFact.getId().getInstanceNum());
		query.setString("sourceSystemCd", "Xmeso");
		ObservationFact xmesoObservationFact = (ObservationFact) query.uniqueResult();
		return xmesoObservationFact != null;
	}


}
