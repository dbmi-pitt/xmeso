package edu.pitt.dbmi.giant4j.ontology;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import edu.pitt.dbmi.giant4j.kb.KbSummary;

public class OntologyToClassConverter {

	public Map<String, String> codedClses = new HashMap<String, String>();
	
	
	public static void  main(String[] args) {
		OntologyToClassConverter  ontologyToClassConverter = new OntologyToClassConverter();
		ontologyToClassConverter.execute();
		ontologyToClassConverter.fetchInfo();
	}

	public void execute() {
		try {
			tryExcecute();		
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void tryExcecute() throws IOException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> kbCls = KbSummary.class;
		String kbPkg = kbCls.getPackage().getName();
		ClassPath classPath = ClassPath.from(kbCls.getClassLoader());
		Set<ClassInfo> clsInfos = classPath
				.getTopLevelClassesRecursive(kbPkg);
		for (ClassInfo clsInfo : clsInfos) {
			Class<?> cls = clsInfo.load();
			if (KbSummary.class.isAssignableFrom(cls)) {
				Class<?>[] emptyParameters = {};
				Constructor<?> constructor = cls.getConstructor(emptyParameters);
				Object[] emptyObjects = {};
				KbSummary summary = (KbSummary) constructor.newInstance(emptyObjects);
				String umlsCode = summary.getCode();
				if (umlsCode.startsWith("umls:")) {
					umlsCode = StringUtils.substringAfter(umlsCode, "umls:");
					String absClsPath = summary.getClass().getName();
					codedClses.put(umlsCode,absClsPath);
				}
			}
		}
	}
	
	public void fetchInfo() {
		if (codedClses.isEmpty()) {
			System.err.println("No kb classes");
		} else {
			int clsCount = 0;
			for (String umlsCode : codedClses.keySet()) {
				if (clsCount % 100 == 0) {
					String clsName = codedClses.get(umlsCode);
					System.out.println(umlsCode + " ==> " + clsName + "\n");
				}
				clsCount++;
			}
		}
	}

}
