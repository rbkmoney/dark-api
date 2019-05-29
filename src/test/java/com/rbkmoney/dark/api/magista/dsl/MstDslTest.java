package com.rbkmoney.dark.api.magista.dsl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rbkmoney.swag.dark_api.model.RefundStatus;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.time.OffsetDateTime;

public class MstDslTest {

    @Test
    public void createPaymentsRequest() throws JsonProcessingException, JSONException {
        JSONAssert.assertEquals("{'query': {'enriched_payments': {'payment_flow':'hold', 'shop_id': '2','invoice_id':'A','payment_id':'B', 'payment_last_digits':'1212','from_time': '2016-03-22T00:12:00Z','to_time': '2016-03-22T01:12:00Z'}}}",
                MstDsl.createPaymentsRequest(
                        "2",
                        OffsetDateTime.parse("2016-03-22T00:12:00Z"),
                        OffsetDateTime.parse("2016-03-22T01:12:00Z"),
                        1000,
                        null,
                        "hold",
                        null,
                        null,
                        "A",
                        "B",
                        null,
                        null,
                        null,
                        null,
                        null,
                        "1212",
                        null,
                        null,
                        null
                ), false);
        JSONAssert.assertEquals("{'query': {'enriched_payments': {}}}",
                MstDsl.createPaymentsRequest(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ), false);
    }

    @Test
    public void createRefundsRequest() throws JsonProcessingException, JSONException {
        JSONAssert.assertEquals("{'query': {'enriched_refunds': {'refund_status': 'succeeded', 'shop_id': '2','invoice_id':'A','payment_id':'B', 'refund_id':'1212','from_time': '2016-03-22T00:12:00Z','to_time': '2016-03-22T01:12:00Z'}}}",
                MstDsl.createRefundsRequest(
                        "2",
                        OffsetDateTime.parse("2016-03-22T00:12:00Z"),
                        OffsetDateTime.parse("2016-03-22T01:12:00Z"),
                        1000,
                        "A",
                        "B",
                        "1212",
                        RefundStatus.StatusEnum.SUCCEEDED.toString()
                ), false);
        JSONAssert.assertEquals("{'query': {'enriched_refunds': {}}}",
                MstDsl.createRefundsRequest(
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ), false);
    }
}
