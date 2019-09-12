package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.BusinessInfo;
import com.rbkmoney.swag.questionary.model.*;
import com.rbkmoney.swag.questionary.model.BusinessInfo.BusinessInfoTypeEnum;
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
        BusinessInfo businessInfo = new BusinessInfo();
        switch (value.getBusinessInfoType()) {
            case ANOTHERBUSINESS:
                AnotherBusiness swagAnotherBusiness = (AnotherBusiness) value;
                businessInfo.setAnotherBusiness(new com.rbkmoney.questionary.AnotherBusiness()
                        .setDescription(swagAnotherBusiness.getDescription()));
                return businessInfo;
            case BUILDINGBUSINESS:
                businessInfo.setBuildingBusiness(new com.rbkmoney.questionary.BuildingBusiness());
                return businessInfo;
            case MEDIATIONINPROPERTYBUSINESS:
                businessInfo.setMediationInPropertyBusiness(new com.rbkmoney.questionary.MediationInPropertyBusiness());
                return businessInfo;
            case PRODUCTIONBUSINESS:
                businessInfo.setProductionBusiness(new com.rbkmoney.questionary.ProductionBusiness());
                return businessInfo;
            case RETAILTRADEBUSINESS:
                businessInfo.setRetailTradeBusiness(new com.rbkmoney.questionary.RetailTradeBusiness());
                return businessInfo;
            case SECURITIESTRADINGBUSINESS:
                businessInfo.setSecuritiesTradingBusiness(new com.rbkmoney.questionary.SecuritiesTradingBusiness());
                return businessInfo;
            case TRANSPORTBUSINESS:
                businessInfo.setTransportBusiness(new com.rbkmoney.questionary.TransportBusiness());
                return businessInfo;
            case WHOLESALETRADEBUSINESS:
                businessInfo.setWholesaleTradeBusiness(new com.rbkmoney.questionary.WholesaleTradeBusiness());
                return businessInfo;
            default:
                throw new IllegalArgumentException("Unknown businessInfo type: " + value.getBusinessInfoType());
        }
    }

}
