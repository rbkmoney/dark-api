package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.*;
import org.springframework.stereotype.Component;

@Component
public class PropertyInfoDocumentTypeConverter implements
        ThriftConverter<PropertyInfoDocumentType, com.rbkmoney.swag.questionary.model.PropertyInfoDocumentType>,
        SwagConverter<com.rbkmoney.swag.questionary.model.PropertyInfoDocumentType, PropertyInfoDocumentType> {

    @Override
    public PropertyInfoDocumentType toThrift(com.rbkmoney.swag.questionary.model.PropertyInfoDocumentType value, ThriftConverterContext ctx) {
        switch (value.getDocumentType()) {
            case LEASECONTRACT:
                return PropertyInfoDocumentType.lease_contract(new LeaseContract());
            case SUBLEASECONTRACT:
                return PropertyInfoDocumentType.sublease_contract(new SubleaseContract());
            case CERTIFICATEOFOWNERSHIP:
                return PropertyInfoDocumentType.certificate_of_ownership(new CertificateOfOwnership());
            case OTHERPROPERTYINFODOCUMENTTYPE:
                var swagOtherPropertyInfoDocumentType = (com.rbkmoney.swag.questionary.model.OtherPropertyInfoDocumentType) value;
                OtherPropertyInfoDocumentType otherPropertyInfoDocumentType = new OtherPropertyInfoDocumentType()
                        .setName(swagOtherPropertyInfoDocumentType.getName());
                return PropertyInfoDocumentType.other_property_info_document_type(otherPropertyInfoDocumentType);
            default:
                throw new IllegalArgumentException("Unknown propertyInfoDocument type: " + value.getDocumentType());
        }
    }


    @Override
    public com.rbkmoney.swag.questionary.model.PropertyInfoDocumentType toSwag(PropertyInfoDocumentType value, SwagConverterContext ctx) {
        if (value.isSetLeaseContract()) {
           return new com.rbkmoney.swag.questionary.model.LeaseContract();
        } else if (value.isSetSubleaseContract()) {
            return new com.rbkmoney.swag.questionary.model.SubleaseContract();
        } else if (value.isSetCertificateOfOwnership()) {
            return new com.rbkmoney.swag.questionary.model.CertificateOfOwnership();
        } else if (value.isSetOtherPropertyInfoDocumentType()) {
            var otherPropertyInfoDocumentType = new com.rbkmoney.swag.questionary.model.OtherPropertyInfoDocumentType();
            otherPropertyInfoDocumentType.setName(value.getOtherPropertyInfoDocumentType().getName());
            return otherPropertyInfoDocumentType;
        } else {
            throw new IllegalArgumentException("Unknown propertyInfoDocument type: " + value.getClass().getName());
        }
    }
}
