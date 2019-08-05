package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.Head;
import com.rbkmoney.swag.questionary.model.Founder;
import com.rbkmoney.swag.questionary.model.FounderHead;
import com.rbkmoney.swag.questionary.model.FoundersInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoundersInfoConverter implements
        ThriftConverter<com.rbkmoney.questionary.FoundersInfo, FoundersInfo>,
        SwagConverter<FoundersInfo, com.rbkmoney.questionary.FoundersInfo> {

    @Override
    public FoundersInfo toSwag(com.rbkmoney.questionary.FoundersInfo value, SwagConverterContext ctx) {
        FoundersInfo foundersInfo = new FoundersInfo();

        if (value.isSetFounders()) {
            List<Founder> founderList = value.getFounders().stream()
                    .map(founder -> ctx.convert(founder, Founder.class))
                    .collect(Collectors.toList());
            foundersInfo.setFounders(founderList);
        }

        if (value.isSetHeads()) {
            List<FounderHead> founderHeadList = value.getHeads().stream()
                    .map(head -> ctx.convert(head, FounderHead.class))
                    .collect(Collectors.toList());

            foundersInfo.setHeads(founderHeadList);
        }

        if (value.isSetLegalOwner()) {
            FounderHead founderHead = ctx.convert(value.getLegalOwner(), FounderHead.class);
            foundersInfo.setLegalOwner(founderHead);
        }

        return foundersInfo;
    }

    @Override
    public com.rbkmoney.questionary.FoundersInfo toThrift(FoundersInfo value, ThriftConverterContext ctx) {
        var foundersInfo = new com.rbkmoney.questionary.FoundersInfo();

        if (value.getFounders() != null) {
            List<com.rbkmoney.questionary.Founder> founderList = value.getFounders().stream()
                    .map(founder -> ctx.convert(founder, com.rbkmoney.questionary.Founder.class))
                    .collect(Collectors.toList());
            foundersInfo.setFounders(founderList);
        }

        if (value.getHeads() != null) {
            List<Head> founderHeadList = value.getHeads().stream()
                    .map(founderHead -> ctx.convert(founderHead, Head.class))
                    .collect(Collectors.toList());
            foundersInfo.setHeads(founderHeadList);
        }

        if (value.getLegalOwner() != null) {
            foundersInfo.setLegalOwner(ctx.convert(value.getLegalOwner(), Head.class));
        }

        return foundersInfo;
    }

}
