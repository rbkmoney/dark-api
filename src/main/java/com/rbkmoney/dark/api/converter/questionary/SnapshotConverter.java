package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.manage.Snapshot;
import com.rbkmoney.swag.questionary.model.Questionary;
import org.springframework.stereotype.Component;

@Component
public class SnapshotConverter implements ThriftConverter<Snapshot, com.rbkmoney.swag.questionary.model.Snapshot>,
        SwagConverter<com.rbkmoney.swag.questionary.model.Snapshot, Snapshot> {

    @Override
    public com.rbkmoney.swag.questionary.model.Snapshot toSwag(Snapshot value, SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.Snapshot()
                .version(String.valueOf(value.getVersion()))
                .questionary(ctx.convert(value.getQuestionary(), Questionary.class));
    }

    @Override
    public Snapshot toThrift(com.rbkmoney.swag.questionary.model.Snapshot value, ThriftConverterContext ctx) {
        Snapshot snapshot = new Snapshot();
        snapshot.setVersion(Long.parseLong(value.getVersion()));
        snapshot.setQuestionary(ctx.convert(value.getQuestionary(), com.rbkmoney.questionary.manage.Questionary.class));
        return snapshot;
    }

}
