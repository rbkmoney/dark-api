package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.dark.api.util.ConverterUtils;
import com.rbkmoney.questionary.IndividualPersonCategories;
import org.springframework.stereotype.Component;

@Component
public class IndividualPersonCategoriesConverter implements
        ThriftConverter<IndividualPersonCategories, com.rbkmoney.swag.questionary.model.IndividualPersonCategories>,
        SwagConverter<com.rbkmoney.swag.questionary.model.IndividualPersonCategories, IndividualPersonCategories> {

    @Override
    public com.rbkmoney.swag.questionary.model.IndividualPersonCategories toSwag(IndividualPersonCategories value,
                                                                                 SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.IndividualPersonCategories()
                .behalfOfForeign(value.isBehalfOfForeign())
                .beneficialOwner(value.isBeneficialOwner())
                .foreignPublicPerson(value.isForeignPublicPerson())
                .foreignRelativePerson(value.isForeignRelativePerson())
                .hasRepresentative(value.isHasRepresentative())
                .worldwideOrgPublicPerson(value.isWorldwideOrgPublicPerson());
    }

    @Override
    public IndividualPersonCategories toThrift(com.rbkmoney.swag.questionary.model.IndividualPersonCategories value,
                                               ThriftConverterContext ctx) {
        return new IndividualPersonCategories()
                .setBehalfOfForeign(ConverterUtils.safeSetValue(value.isBehalfOfForeign()))
                .setBeneficialOwner(ConverterUtils.safeSetValue(value.isBeneficialOwner()))
                .setForeignPublicPerson(ConverterUtils.safeSetValue(value.isForeignPublicPerson()))
                .setForeignRelativePerson(ConverterUtils.safeSetValue(value.isForeignRelativePerson()))
                .setHasRepresentative(ConverterUtils.safeSetValue(value.isHasRepresentative()))
                .setWorldwideOrgPublicPerson(ConverterUtils.safeSetValue(value.isWorldwideOrgPublicPerson()));
    }

}
