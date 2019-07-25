//package com.rbkmoney.dark.api.converter;
//
//import com.rbkmoney.dark.api.converter.dadata.*;
//import com.rbkmoney.dark.api.converter.konturfocus.*;
//import com.rbkmoney.dark.api.utils.TestDataUtils;
//import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//
//@RunWith(SpringRunner.class)
//public class SwagConvertManagerTest {
//
//    private SwagConvertManager swagConvertManager;
//
//    @Before
//    public void setUp() throws Exception {
//        this.swagConvertManager = new SwagConvertManager();
//
//        // KonturFocus
//        swagConvertManager.addConverter(KonturFocusResponse.class, new KonturFocusResponseSwagConverter());
//        swagConvertManager.addConverter(ReqContractor.class, new ReqContractorSwagConverter());
//        swagConvertManager.addConverter(ReqIndividualEntity.class, new ReqIndividualEntitySwagConverter());
//        swagConvertManager.addConverter(ReqLegalEntity.class, new ReqLegalEntitySwagConverter());
//        swagConvertManager.addConverter(Head.class, new HeadSwagConverter());
//        swagConvertManager.addConverter(Branch.class, new BranchSwagConverter());
//        swagConvertManager.addConverter(ParsedAddressRF.class, new ParsedAddressRFSwagConverter());
//        swagConvertManager.addConverter(Toponim.class, new ToponimConverterSwagConverter());
//        swagConvertManager.addConverter(ForeignAddress.class, new ForeignAddressSwagConverter());
//        swagConvertManager.addConverter(LegalName.class, new LegalNameSwagConverter());
//        swagConvertManager.addConverter(LegalAddress.class, new LegalAddressConverter());
//        swagConvertManager.addConverter(ManagementCompany.class, new ManagementCompanySwagConverter());
//        swagConvertManager.addConverter(ReqHistory.class, new ReqHistorySwagConverter());
//        swagConvertManager.addConverter(ReqKppHistory.class, new ReqKppHistorySwagConverter());
//
//        // DaData
//        swagConvertManager.addConverter(DaDataResponse.class, new DaDataResponseSwagConverter());
//    }
//
//    @Test
//    public void convertTest() throws IOException {
//        var thriftKonturFocusReqResponse = TestDataUtils.createThriftKonturFocusReqResponse();
//
//        KonturFocusResponse convert = swagConvertManager.convert(thriftKonturFocusReqResponse, KonturFocusResponse.class);
//        System.out.println(thriftKonturFocusReqResponse);
//        //swagConvertManager.convert()
//    }
//
//}
