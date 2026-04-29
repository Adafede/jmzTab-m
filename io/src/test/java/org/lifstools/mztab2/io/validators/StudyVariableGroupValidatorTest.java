/*
 * Copyright 2019 Leibniz-Institut für Analytische Wissenschaften – ISAS – e.V..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lifstools.mztab2.io.validators;

import org.lifstools.mztab2.model.Metadata;
import org.lifstools.mztab2.model.Parameter;
import org.lifstools.mztab2.model.StudyVariable;
import org.lifstools.mztab2.model.StudyVariableGroup;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.ebi.pride.jmztab2.utils.errors.LogicalErrorType;
import uk.ac.ebi.pride.jmztab2.utils.errors.MZTabError;
import uk.ac.ebi.pride.jmztab2.utils.parser.MZTabParserContext;

/**
 *
 * @author nilshoffmann
 */
public class StudyVariableGroupValidatorTest {

    private static final Parameter PATO_SEX = new Parameter().
        cvLabel("PATO").cvAccession("PATO:0000383").name("sex");
    private static final Parameter STATO_CATEGORICAL = new Parameter().
        cvLabel("STATO").cvAccession("STATO:0000252").name("categorical variable");

    /** No groups declared → validator is a no-op, no errors expected. */
    @Test
    public void testEmptyGroupMapProducesNoErrors() {
        Metadata metadata = new Metadata();
        MZTabParserContext parserContext = new MZTabParserContext();
        StudyVariableGroupValidator instance = new StudyVariableGroupValidator();

        List<MZTabError> result = instance.validateRefine(metadata, parserContext);
        assertTrue(result.isEmpty());
    }

    /** Valid group with all mandatory fields, referenced by a study variable → no errors. */
    @Test
    public void testValidGroupReferencedByStudyVariable() {
        Metadata metadata = new Metadata();
        MZTabParserContext parserContext = new MZTabParserContext();

        StudyVariableGroup group = new StudyVariableGroup().id(1).
            parameter(PATO_SEX).
            description("Sex of the individual");
        parserContext.addStudyVariableGroup(metadata, group);

        StudyVariable sv = new StudyVariable().id(1).name("Female").
            description("Female samples").groupRef(group);
        parserContext.addStudyVariable(metadata, sv);

        StudyVariableGroupValidator instance = new StudyVariableGroupValidator();
        List<MZTabError> result = instance.validateRefine(metadata, parserContext);
        assertTrue(result.isEmpty());
    }

    /** Valid group with all optional fields populated → no errors. */
    @Test
    public void testValidGroupWithAllFields() {
        Metadata metadata = new Metadata();
        MZTabParserContext parserContext = new MZTabParserContext();

        StudyVariableGroup group = new StudyVariableGroup().id(1).
            parameter(PATO_SEX).
            description("Sex of the individual").
            type(STATO_CATEGORICAL).
            datatype("xsd:string").
            unit(new Parameter().cvLabel("UO").cvAccession("UO:0000189").name("count unit"));
        parserContext.addStudyVariableGroup(metadata, group);

        StudyVariable sv = new StudyVariable().id(1).name("Female").
            description("Female samples").groupRef(group);
        parserContext.addStudyVariable(metadata, sv);

        StudyVariableGroupValidator instance = new StudyVariableGroupValidator();
        List<MZTabError> result = instance.validateRefine(metadata, parserContext);
        assertTrue(result.isEmpty());
    }

    /** Group with null parameter → error for missing mandatory field. */
    @Test
    public void testMissingParameterProducesError() {
        Metadata metadata = new Metadata();
        MZTabParserContext parserContext = new MZTabParserContext();

        StudyVariableGroup group = new StudyVariableGroup().id(1).
            description("Sex of the individual");
        parserContext.addStudyVariableGroup(metadata, group);

        StudyVariable sv = new StudyVariable().id(1).name("Female").
            description("Female samples").groupRef(group);
        parserContext.addStudyVariable(metadata, sv);

        StudyVariableGroupValidator instance = new StudyVariableGroupValidator();
        List<MZTabError> expResult = Arrays.asList(
            new MZTabError(LogicalErrorType.NotDefineInMetadata, -1,
                Metadata.JSON_PROPERTY_STUDY_VARIABLE_GROUP + "[1]"
                + "\t<" + StudyVariableGroup.JSON_PROPERTY_PARAMETER + ">")
        );
        List<MZTabError> result = instance.validateRefine(metadata, parserContext);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).toString(), result.get(0).toString());
    }

    /** Group with null description → error for missing mandatory field. */
    @Test
    public void testMissingDescriptionProducesError() {
        Metadata metadata = new Metadata();
        MZTabParserContext parserContext = new MZTabParserContext();

        StudyVariableGroup group = new StudyVariableGroup().id(1).
            parameter(PATO_SEX);
        parserContext.addStudyVariableGroup(metadata, group);

        StudyVariable sv = new StudyVariable().id(1).name("Female").
            description("Female samples").groupRef(group);
        parserContext.addStudyVariable(metadata, sv);

        StudyVariableGroupValidator instance = new StudyVariableGroupValidator();
        List<MZTabError> expResult = Arrays.asList(
            new MZTabError(LogicalErrorType.NotDefineInMetadata, -1,
                Metadata.JSON_PROPERTY_STUDY_VARIABLE_GROUP + "[1]-"
                + StudyVariableGroup.JSON_PROPERTY_DESCRIPTION)
        );
        List<MZTabError> result = instance.validateRefine(metadata, parserContext);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).toString(), result.get(0).toString());
    }

    /** Group type with a non-STATO cv label → error. */
    @Test
    public void testTypeWithNonStatoCvLabelProducesError() {
        Metadata metadata = new Metadata();
        MZTabParserContext parserContext = new MZTabParserContext();

        Parameter invalidType = new Parameter().cvLabel("MS").
            cvAccession("MS:1000001").name("some ms term");
        StudyVariableGroup group = new StudyVariableGroup().id(1).
            parameter(PATO_SEX).
            description("Sex of the individual").
            type(invalidType);
        parserContext.addStudyVariableGroup(metadata, group);

        StudyVariable sv = new StudyVariable().id(1).name("Female").
            description("Female samples").groupRef(group);
        parserContext.addStudyVariable(metadata, sv);

        StudyVariableGroupValidator instance = new StudyVariableGroupValidator();
        List<MZTabError> expResult = Arrays.asList(
            new MZTabError(LogicalErrorType.NotDefineInMetadata, -1,
                Metadata.JSON_PROPERTY_STUDY_VARIABLE_GROUP + "[1]-"
                + StudyVariableGroup.JSON_PROPERTY_TYPE
                + " (MUST be a STATO ontology term, e.g. [STATO, STATO:0000252, categorical variable, ])")
        );
        List<MZTabError> result = instance.validateRefine(metadata, parserContext);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).toString(), result.get(0).toString());
    }

    /** Group with an unsupported xsd datatype value → error. */
    @Test
    public void testInvalidDatatypeProducesError() {
        Metadata metadata = new Metadata();
        MZTabParserContext parserContext = new MZTabParserContext();

        StudyVariableGroup group = new StudyVariableGroup().id(1).
            parameter(PATO_SEX).
            description("Sex of the individual").
            datatype("xsd:unsupported");
        parserContext.addStudyVariableGroup(metadata, group);

        StudyVariable sv = new StudyVariable().id(1).name("Female").
            description("Female samples").groupRef(group);
        parserContext.addStudyVariable(metadata, sv);

        StudyVariableGroupValidator instance = new StudyVariableGroupValidator();
        List<MZTabError> expResult = Arrays.asList(
            new MZTabError(LogicalErrorType.NotDefineInMetadata, -1,
                Metadata.JSON_PROPERTY_STUDY_VARIABLE_GROUP + "[1]-"
                + StudyVariableGroup.JSON_PROPERTY_DATATYPE
                + " (MUST be one of: xsd:string, xsd:integer, xsd:decimal, xsd:boolean, xsd:date, xsd:time, xsd:dateTime, xsd:anyURI)")
        );
        List<MZTabError> result = instance.validateRefine(metadata, parserContext);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).toString(), result.get(0).toString());
    }

    /** Group not referenced by any study variable → StudyVariableNotDefined error. */
    @Test
    public void testUnreferencedGroupProducesError() {
        Metadata metadata = new Metadata();
        MZTabParserContext parserContext = new MZTabParserContext();

        StudyVariableGroup group = new StudyVariableGroup().id(1).
            parameter(PATO_SEX).
            description("Sex of the individual");
        parserContext.addStudyVariableGroup(metadata, group);

        // No study variable with groupRef → group is unreferenced
        StudyVariableGroupValidator instance = new StudyVariableGroupValidator();
        List<MZTabError> expResult = Arrays.asList(
            new MZTabError(LogicalErrorType.StudyVariableNotDefined, -1,
                Metadata.JSON_PROPERTY_STUDY_VARIABLE_GROUP + "[1]"
                + " is not referenced by any study_variable[n]-group_ref")
        );
        List<MZTabError> result = instance.validateRefine(metadata, parserContext);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).toString(), result.get(0).toString());
    }

    /** All valid xsd datatypes are accepted without error. */
    @Test
    public void testAllValidXsdDatatypesAreAccepted() {
        String[] validTypes = {
            "xsd:string", "xsd:integer", "xsd:decimal", "xsd:boolean",
            "xsd:date", "xsd:time", "xsd:dateTime", "xsd:anyURI"
        };
        for (String datatype : validTypes) {
            Metadata metadata = new Metadata();
            MZTabParserContext parserContext = new MZTabParserContext();

            StudyVariableGroup group = new StudyVariableGroup().id(1).
                parameter(PATO_SEX).
                description("Sex of the individual").
                datatype(datatype);
            parserContext.addStudyVariableGroup(metadata, group);

            StudyVariable sv = new StudyVariable().id(1).name("Female").
                description("Female samples").groupRef(group);
            parserContext.addStudyVariable(metadata, sv);

            StudyVariableGroupValidator instance = new StudyVariableGroupValidator();
            List<MZTabError> result = instance.validateRefine(metadata, parserContext);
            assertTrue(result.isEmpty(), "Expected no errors for datatype: " + datatype);
        }
    }
}
