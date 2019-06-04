package com.rbkmoney.dark.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.swag.dark_api.model.QuestionaryGetParams;
import com.rbkmoney.swag.dark_api.model.ReferenceVersion;
import org.junit.Test;

import java.io.IOException;

public class QuestionaryApiImplTest {

    @Test
    public void testMe() throws IOException {
        QuestionaryGetParams params = new QuestionaryGetParams();
        params.setQuestionaryId("QuestionaryId");

        ReferenceVersion referenceVersion = new ReferenceVersion();
        referenceVersion.setVersion(1);
        params.setReference(referenceVersion);

        System.out.println("-- serializing --");
        ObjectMapper om = new ObjectMapper();
        String s = om.writeValueAsString(params);
        System.out.println(s);

        System.out.println("-- deserializing --");
        params = om.readValue(s, QuestionaryGetParams.class);
        System.out.println(params);

    }

}