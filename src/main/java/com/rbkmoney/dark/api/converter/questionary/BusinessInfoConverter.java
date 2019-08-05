package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.BusinessInfo;
import com.rbkmoney.swag.questionary.model.*;
import org.springframework.stereotype.Component;

@Component
public class BusinessInfoConverter implements
        ThriftConverter<BusinessInfo, com.rbkmoney.swag.questionary.model.BusinessInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.BusinessInfo, BusinessInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.BusinessInfo toSwag(BusinessInfo value, SwagConverterContext ctx) {
        if (value.isSetAnotherBusiness()) {
            return new AnotherBusiness().description(value.getAnotherBusiness().getDescription());
        } else if (value.isSetBuildingBusiness()) {
            return new BuildingBusiness();
        } else if (value.isSetMediationInPropertyBusiness()) {
            return new MediationInPropertyBusiness();
        } else if (value.isSetProductionBusiness()) {
            return new ProductionBusiness();
        } else if (value.isSetRetailTradeBusiness()) {
            return new RetailTradeBusiness();
        } else if (value.isSetSecuritiesTradingBusiness()) {
            return new SecuritiesTradingBusiness();
        } else if (value.isSetTransportBusiness()) {
            return new TransportBusiness();
        } else if (value.isSetWholesaleTradeBusiness()) {
            return new WholesaleTradeBusiness();
        }
        throw new IllegalArgumentException("Unknown businessInfo type: " + value.getClass().getName());
    }

    @Override
    public BusinessInfo toThrift(com.rbkmoney.swag.questionary.model.BusinessInfo value, ThriftConverterContext ctx) {
        if (value instanceof AnotherBusiness) {
            return BusinessInfo.another_business(new com.rbkmoney.questionary.AnotherBusiness());
        } else if (value instanceof BuildingBusiness) {
            return BusinessInfo.building_business(new com.rbkmoney.questionary.BuildingBusiness());
        } else if (value instanceof MediationInPropertyBusiness) {
            return BusinessInfo.mediation_in_property_business(new com.rbkmoney.questionary.MediationInPropertyBusiness());
        } else if (value instanceof ProductionBusiness) {
            return BusinessInfo.production_business(new com.rbkmoney.questionary.ProductionBusiness());
        } else if (value instanceof RetailTradeBusiness) {
            return BusinessInfo.retail_trade_business(new com.rbkmoney.questionary.RetailTradeBusiness());
        } else if (value instanceof SecuritiesTradingBusiness) {
            return BusinessInfo.securities_trading_business(new com.rbkmoney.questionary.SecuritiesTradingBusiness());
        } else if (value instanceof TransportBusiness) {
            return BusinessInfo.transport_business(new com.rbkmoney.questionary.TransportBusiness());
        } else if (value instanceof WholesaleTradeBusiness) {
            return BusinessInfo.wholesale_trade_business(new com.rbkmoney.questionary.WholesaleTradeBusiness());
        }
        throw new IllegalArgumentException("Unknown businessInfo type: " + value.getClass().getName());
    }

}
