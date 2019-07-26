package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.PrivateEntityStatusDetail;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ReqIndividualEntity;
import org.springframework.stereotype.Component;

@Component
public class ReqIndividualEntitySwagConverter
        implements SwagConverter<ReqIndividualEntity, com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqIndividualEntity> {

    @Override
    public ReqIndividualEntity toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqIndividualEntity value, SwagConverterContext ctx) {
        ReqIndividualEntity swagReqIndividualEntity = new ReqIndividualEntity();
        swagReqIndividualEntity.setFio(value.getFio());
        swagReqIndividualEntity.setOktmo(value.getOktmo());
        swagReqIndividualEntity.setOpf(value.getOpf());
        swagReqIndividualEntity.setOkopf(value.getOkopf());
        swagReqIndividualEntity.setOkpo(value.getOkpo());
        swagReqIndividualEntity.setOkogu(value.getOkogu());
        swagReqIndividualEntity.setOkfs(value.getOkfs());
        swagReqIndividualEntity.setOkato(value.getOkato());
        if (value.isSetStatusDetail()) {
            swagReqIndividualEntity.setStatusDetail(convertPrivateEntityStatusDetail(value.getStatusDetail()));
        }
        if (value.isSetDissolutionDate()) {
            swagReqIndividualEntity.setDissolutionDate(value.getDissolutionDate());
        }
        if (value.isSetRegistrationDate()) {
            swagReqIndividualEntity.setRegistrationDate(value.getRegistrationDate());
        }
        return swagReqIndividualEntity;
    }

    private PrivateEntityStatusDetail convertPrivateEntityStatusDetail(com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.PrivateEntityStatusDetail privateEntityStatusDetail) {
        PrivateEntityStatusDetail swagPrivateEntityStatusDetail = new PrivateEntityStatusDetail();
        swagPrivateEntityStatusDetail.setDate(privateEntityStatusDetail.getDate());
        swagPrivateEntityStatusDetail.setDissolved(privateEntityStatusDetail.isDissolved());
        swagPrivateEntityStatusDetail.setStatus(privateEntityStatusDetail.getStatus());
        return swagPrivateEntityStatusDetail;
    }

}
