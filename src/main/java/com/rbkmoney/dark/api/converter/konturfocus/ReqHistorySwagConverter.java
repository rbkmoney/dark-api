package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReqHistorySwagConverter implements SwagConverter<ReqHistory, com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqHistory> {

    @Override
    public ReqHistory toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqHistory value, SwagConverterContext ctx) {
        ReqHistory reqHistory = new ReqHistory();
        if (value.isSetBranches()) {
            List<Branch> swagBranchList = value.getBranches().stream()
                    .map(branch -> {
                        return ctx.convert(branch, Branch.class);
                    })
                    .collect(Collectors.toList());
            reqHistory.setBranches(swagBranchList);
        }
        if (value.isSetManagementCompanies()) {
            List<ManagementCompany> managementCompanyList = value.getManagementCompanies().stream()
                    .map(managementCompany -> {
                        return ctx.convert(managementCompany, ManagementCompany.class);
                    })
                    .collect(Collectors.toList());
            reqHistory.setManagementCompanies(managementCompanyList);
        }
        if (value.isSetHeads()) {
            List<Head> swagHeadList = value.getHeads().stream()
                    .map(head -> {
                        return ctx.convert(head, Head.class);
                    })
                    .collect(Collectors.toList());
            reqHistory.setHeads(swagHeadList);
        }
        if (value.isSetKpps()) {
            List<ReqKppHistory> swagReqKppHistoryList = value.getKpps().stream()
                    .map(reqKppHistory -> {
                        return ctx.convert(reqKppHistory, ReqKppHistory.class);
                    })
                    .collect(Collectors.toList());
            reqHistory.setKpps(swagReqKppHistoryList);
        }
        if (value.isSetLegalNames()) {
            List<LegalName> swagLegalNameList = value.getLegalNames().stream()
                    .map(legalName -> {
                        return ctx.convert(legalName, LegalName.class);
                    })
                    .collect(Collectors.toList());
            reqHistory.setLegalNames(swagLegalNameList);
        }
        if (value.isSetLegalAddresses()) {
            List<LegalAddress> swagLegalAddressList = value.getLegalAddresses().stream()
                    .map(legalAddress -> {
                        return ctx.convert(legalAddress, LegalAddress.class);
                    })
                    .collect(Collectors.toList());
            reqHistory.setLegalAddresses(swagLegalAddressList);
        }

        return reqHistory;
    }

}
