package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("LineLength")
public class BeneficialOwnerResponseSwagConverter implements
        SwagConverter<BeneficialOwnerResponse, com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerResponse> {

    @Override
    public BeneficialOwnerResponse toSwag(
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerResponse value,
            SwagConverterContext ctx) {
        BeneficialOwnerResponse beneficialOwnerResponse = new BeneficialOwnerResponse();
        beneficialOwnerResponse.setInn(value.getInn());
        beneficialOwnerResponse.setOgrn(value.getOgrn());
        beneficialOwnerResponse.setFocusHref(value.getFocusHref());
        if (value.isSetStatedCapital()) {
            StatedCapital statedCapital = new StatedCapital();
            statedCapital.setDate(value.getStatedCapital().getDate());
            statedCapital.setSum(value.getStatedCapital().getSum());
            beneficialOwnerResponse.setStatedCapital(statedCapital);
        }

        // Beneficial owner
        if (value.isSetBeneficialOwners()) {
            BeneficialOwners beneficialOwners = new BeneficialOwners();

            if (value.getBeneficialOwners().isSetBeneficialOwnersFl()) {
                List<BeneficialOwnerFl> beneficialOwnerFlList =
                        value.getBeneficialOwners().getBeneficialOwnersFl().stream()
                                .map(beneficialOwnerFl -> ctx.convert(beneficialOwnerFl, BeneficialOwnerFl.class))
                                .collect(Collectors.toList());
                beneficialOwners.setBeneficialOwnerFl(beneficialOwnerFlList);
            }

            if (value.getBeneficialOwners().isSetBeneficialOwnersUl()) {
                List<BeneficialOwnerUl> beneficialOwnerUlList =
                        value.getBeneficialOwners().getBeneficialOwnersUl().stream()
                                .map(beneficialOwnerUl -> ctx.convert(beneficialOwnerUl, BeneficialOwnerUl.class))
                                .collect(Collectors.toList());
                beneficialOwners.setBeneficialOwnersUl(beneficialOwnerUlList);
            }

            if (value.getBeneficialOwners().isSetBeneficialOwnersForeign()) {
                List<BeneficialOwnerForeign> beneficialOwnerForeignList =
                        value.getBeneficialOwners().getBeneficialOwnersForeign().stream()
                                .map(beneficialOwnerForeign -> ctx
                                        .convert(beneficialOwnerForeign, BeneficialOwnerForeign.class))
                                .collect(Collectors.toList());
                beneficialOwners.setBeneficialOwnersForeign(beneficialOwnerForeignList);
            }

            if (value.getBeneficialOwners().isSetBeneficialOwnersOther()) {
                List<BeneficialOwnerOther> beneficialOwnerOtherList =
                        value.getBeneficialOwners().getBeneficialOwnersOther().stream()
                                .map(beneficialOwnerOther -> ctx
                                        .convert(beneficialOwnerOther, BeneficialOwnerOther.class))
                                .collect(Collectors.toList());
                beneficialOwners.setBeneficialOwnersOther(beneficialOwnerOtherList);
            }

            beneficialOwnerResponse.setBeneficialOwners(beneficialOwners);
        }

        // Historical beneficial owner
        if (value.isSetHistoricalBeneficialOwners()) {
            BeneficialOwners beneficialOwners = new BeneficialOwners();

            if (value.getHistoricalBeneficialOwners().isSetBeneficialOwnersFl()) {
                List<BeneficialOwnerFl> beneficialOwnerFlList =
                        value.getHistoricalBeneficialOwners().getBeneficialOwnersFl().stream()
                                .map(beneficialOwnerFl -> ctx.convert(beneficialOwnerFl, BeneficialOwnerFl.class))
                                .collect(Collectors.toList());
                beneficialOwners.setBeneficialOwnerFl(beneficialOwnerFlList);
            }

            if (value.getHistoricalBeneficialOwners().isSetBeneficialOwnersUl()) {
                List<BeneficialOwnerUl> beneficialOwnerUlList =
                        value.getHistoricalBeneficialOwners().getBeneficialOwnersUl().stream()
                                .map(beneficialOwnerUl -> ctx.convert(beneficialOwnerUl, BeneficialOwnerUl.class))
                                .collect(Collectors.toList());
                beneficialOwners.setBeneficialOwnersUl(beneficialOwnerUlList);
            }

            if (value.getHistoricalBeneficialOwners().isSetBeneficialOwnersForeign()) {
                List<BeneficialOwnerForeign> beneficialOwnerForeignList =
                        value.getHistoricalBeneficialOwners().getBeneficialOwnersForeign().stream()
                                .map(beneficialOwnerForeign -> ctx
                                        .convert(beneficialOwnerForeign, BeneficialOwnerForeign.class))
                                .collect(Collectors.toList());
                beneficialOwners.setBeneficialOwnersForeign(beneficialOwnerForeignList);
            }

            if (value.getHistoricalBeneficialOwners().isSetBeneficialOwnersOther()) {
                List<BeneficialOwnerOther> beneficialOwnerOtherList =
                        value.getHistoricalBeneficialOwners().getBeneficialOwnersOther().stream()
                                .map(beneficialOwnerOther -> ctx
                                        .convert(beneficialOwnerOther, BeneficialOwnerOther.class))
                                .collect(Collectors.toList());
                beneficialOwners.setBeneficialOwnersOther(beneficialOwnerOtherList);
            }

            beneficialOwnerResponse.setHistoricalBeneficialOwners(beneficialOwners);
        }

        return beneficialOwnerResponse;
    }
}
