package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReqLegalEntitySwagConverter
        implements SwagConverter<ReqLegalEntity, com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqLegalEntity> {

    @Override
    public ReqLegalEntity toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqLegalEntity value, SwagConverterContext ctx) {
        ReqLegalEntity swagReqLegalEntity = new ReqLegalEntity();
        swagReqLegalEntity.setKpp(value.getKpp());
        swagReqLegalEntity.setOkato(value.getOkato());
        swagReqLegalEntity.setOkfs(value.getOkfs());
        swagReqLegalEntity.setOkogu(value.getOkogu());
        swagReqLegalEntity.setOkopf(value.getOkopf());
        swagReqLegalEntity.setOkpo(value.getOkpo());
        swagReqLegalEntity.setOktmo(value.getOktmo());
        swagReqLegalEntity.setOpf(value.getOpf());
        swagReqLegalEntity.setDissolutionDate(value.getDissolutionDate());
        swagReqLegalEntity.setRegistrationDate(value.getRegistrationDate());
        if (value.isSetStatus()) {
            LegalEntityStatusDetail swagLegalEntityStatusDetail = new LegalEntityStatusDetail();
            swagLegalEntityStatusDetail.setStatus(value.getStatus().getStatus());
            swagLegalEntityStatusDetail.setDate(value.getStatus().getDate());
            swagLegalEntityStatusDetail.setDissolved(value.getStatus().isDissolved());
            swagLegalEntityStatusDetail.setDissolving(value.getStatus().isDissolving());
            swagLegalEntityStatusDetail.setReorganizing(value.getStatus().isReorganizing());
            swagReqLegalEntity.setStatus(swagLegalEntityStatusDetail);
        }
        if (value.isSetBranches()) {
            List<Branch> swagBranchList = value.getBranches().stream()
                    .map(branch -> {
                        return ctx.convert(branch, Branch.class);
                    })
                    .collect(Collectors.toList());
            swagReqLegalEntity.setBranches(swagBranchList);
        }
        if (value.isSetHeads()) {
            List<Head> swagHeadList = value.getHeads().stream()
                    .map(head -> {
                        return ctx.convert(head, Head.class);
                    })
                    .collect(Collectors.toList());
            swagReqLegalEntity.setHeads(swagHeadList);
        }

        if (value.isSetLegalName()) {
            swagReqLegalEntity.setLegalName(ctx.convert(value.getLegalName(), LegalName.class));
        }

        if (value.isSetLegalAddress()) {
            swagReqLegalEntity.setLegalAddress(ctx.convert(value.getLegalAddress(), LegalAddress.class));
        }

        if (value.isSetManagementCompanies()) {
            List<ManagementCompany> swagManagementCompanies = value.getManagementCompanies().stream()
                    .map(managementCompany -> {
                        return ctx.convert(managementCompany, ManagementCompany.class);
                    })
                    .collect(Collectors.toList());
            swagReqLegalEntity.setManagementCompanies(swagManagementCompanies);
        }

        if (value.isSetHistory()) {
            swagReqLegalEntity.setHistory(ctx.convert(value.getHistory(), ReqHistory.class));
        }

        return swagReqLegalEntity;
    }

}
