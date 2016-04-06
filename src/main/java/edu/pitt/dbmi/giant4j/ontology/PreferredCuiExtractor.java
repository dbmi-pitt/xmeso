package edu.pitt.dbmi.giant4j.ontology;

/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationObjectVisitor;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLSubAnnotationPropertyOfAxiom;

public class PreferredCuiExtractor implements OWLAnnotationObjectVisitor {
    
//	private String cuiAttribute = "prefCUI";
	private String cuiAttribute = "code";
	private String cui;

    public PreferredCuiExtractor() {
        cui = null;
    }

    @Override
    public void visit(OWLAnonymousIndividual individual) {}

    @Override
    public void visit(IRI iri) {}

    @Override
    public void visit(OWLLiteral literal) {}

    @Override
    public void visit(OWLAnnotation annotation) {
        /*
         * If it's a label, grab it as the result. Note that if there are
         * multiple labels, the last one will be used.
         */
    	if (cui == null) {
    		String annotationAsString = annotation.getProperty().getIRI().toString();
            if (annotationAsString.endsWith(cuiAttribute)) {
                OWLLiteral owlLiteral = (OWLLiteral) annotation.getValue();
                cui = owlLiteral.getLiteral();
                cui = extractPrefixCuiUmls(cui);
            }
    	} 	
    }
    
    private String extractPrefixCuiUmls(String cui) {
        Pattern pattern = Pattern.compile("^(C\\d{7}) \\[UMLS_CUI\\]$");
        Matcher matcher = pattern.matcher(cui);
        if (matcher.matches()) {
        	cui = "umls:" + matcher.group(1);
        }
        else {
        	cui = null;
        }
        return cui;
    }
   

    @Override
    public void visit(OWLAnnotationAssertionAxiom axiom) {}

    @Override
    public void visit(OWLAnnotationPropertyDomainAxiom axiom) {}

    @Override
    public void visit(OWLAnnotationPropertyRangeAxiom axiom) {}

    @Override
    public void visit(OWLSubAnnotationPropertyOfAxiom axiom) {}

    public void visit(OWLAnnotationProperty property) {}

    public void visit(OWLAnnotationValue value) {}

    public String getResult() {
        return cui;
    }
}