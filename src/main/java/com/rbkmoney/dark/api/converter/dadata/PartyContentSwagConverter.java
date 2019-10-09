package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PartyContentSwagConverter implements SwagConverter<PartyContent, com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyContent> {

    @Override
    public PartyContent toSwag(com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyContent value, SwagConverterContext ctx) {
        PartyContent partyContent = new PartyContent()
                .inn(value.getInn())
                .kpp(value.getKpp())
                .ogrn(value.getOgrn())
                .ogrnDate(value.getOgrnDate())
                .okpo(value.getOkpo())
                .value(value.getValue())
                .unrestrictedValue(value.getUnrestrictedValue())
                .branchCount(value.getBranchCount())
                .okved(value.getOkved())
                .hid(new DaDataHID().hid(value.getHid()))
                .okvedType(value.getOkvedType());

        if (value.isSetAuthorities()) {
            partyContent.setAuthorities(convertPartyAuthorities(value.getAuthorities()));
        }

        if (value.isSetOpf()) {
            partyContent.setOpf(ctx.convert(value.getOpf(), Opf.class));
        }

        if (value.isSetDocuments()) {
            partyContent.setDocuments(ctx.convert(value.getDocuments(), PartyDocuments.class));
        }

        if (value.isSetBranchType()) {
            partyContent.setBranchType(ctx.convert(value.getBranchType(), BranchType.class));
        }

        if (value.isSetAddress()) {
            partyContent.setAddress(ctx.convert(value.getAddress(), DaDataAddress.class));
        }
        if (value.isSetLicenses()) {
            List<DaDataLicense> daDataLicenseList = value.getLicenses().stream()
                    .map(license -> ctx.convert(license, DaDataLicense.class))
                    .collect(Collectors.toList());
            partyContent.setLicenses(daDataLicenseList);
        }
        if (value.isSetName()) {
            partyContent.setName(convertOrgName(value.getName()));
        }
        if (value.isSetCapital()) {
            partyContent.setCapital(convertPartyCapital(value.getCapital()));
        }
        if (value.isSetCitizenship()) {
            partyContent.setCitizenship(convertCitizenshipIP(value.getCitizenship()));
        }
        if (value.isSetManagement()) {
            partyContent.setManagement(convertManagement(value.getManagement()));
        }
        if (value.isSetFounders()) {
            List<Founder> founderList = value.getFounders().stream()
                    .map(founder -> ctx.convert(founder, Founder.class))
                    .collect(Collectors.toList());
            partyContent.setFounders(founderList);
        }
        if (value.isSetOkveds()) {
            List<PartyOkved> partyOkvedList = value.getOkveds().stream()
                    .map(this::convertPartyOkved)
                    .collect(Collectors.toList());
            partyContent.setOkveds(partyOkvedList);
        }
        if (value.isSetManagement()) {
            partyContent.setManagement(convertManagement(value.getManagement()));
        }
        if (value.isSetManagers()) {
            List<Manager> managerList = value.getManagers().stream()
                    .map(this::convertManager)
                    .collect(Collectors.toList());

            partyContent.setManagers(managerList);
        }
        if (value.getType() == com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgType.LEGAL) {
            partyContent.setOrgType(OrgType.LEGAL);
        } else if (value.getType() == com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgType.INDIVIDUAL) {
            partyContent.setOrgType(OrgType.INDIVIDUAL);
        }
        if (value.isSetState()) {
            partyContent.setState(ctx.convert(value.getState(), DaDataState.class));
        }

        return partyContent;
    }

    private CitizenshipIP convertCitizenshipIP(com.rbkmoney.questionary_proxy_aggr.base_dadata.CitizenshipIP citizenshipIP) {
        return new CitizenshipIP()
                .alpha3(citizenshipIP.getAlpha3())
                .countryFullName(citizenshipIP.getCountryFullName())
                .countryShortName(citizenshipIP.getCountryShortName())
                .numeric(citizenshipIP.getNumeric());
    }

    private PartyAuthorities convertPartyAuthorities(com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyAuthorities partyAuthorities) {
        PartyAuthorities swagPartyAuthorities = new PartyAuthorities();
        if (partyAuthorities.isSetFtsRegistration()) {
            swagPartyAuthorities.setFtsRegistration(convertAuthorities(partyAuthorities.getFtsRegistration()));
        }
        if (partyAuthorities.isSetFtsReport()) {
            swagPartyAuthorities.setFtsReport(convertAuthorities(partyAuthorities.getFtsReport()));
        }
        if (partyAuthorities.isSetPf()) {
            swagPartyAuthorities.setPf(convertAuthorities(partyAuthorities.getPf()));
        }
        if (partyAuthorities.isSetSif()) {
            swagPartyAuthorities.setSif(convertAuthorities(partyAuthorities.getSif()));
        }
        return swagPartyAuthorities;
    }

    private PartyCapital convertPartyCapital(com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyCapital partyCapital) {
        return new PartyCapital()
                .type(partyCapital.getType())
                .value(partyCapital.getValue());
    }

    private OrgName convertOrgName(com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgName orgName) {
        return new OrgName()
                .fullName(orgName.getFullName())
                .shortName(orgName.getShortName())
                .fullWithOpf(orgName.getFullWithOpf())
                .shortWithOpf(orgName.getShortWithOpf())
                .latin(orgName.getLatin());
    }

    private Authorities convertAuthorities(com.rbkmoney.questionary_proxy_aggr.base_dadata.Authorities authorities) {
        return new Authorities()
                .address(authorities.getAddress())
                .code(authorities.getCode())
                .name(authorities.getName())
                .type(authorities.getType());
    }

    private PartyOkved convertPartyOkved(com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyOkved partyOkved) {
        return new PartyOkved()
                .code(partyOkved.getCode())
                .name(partyOkved.getName())
                .type(partyOkved.getType())
                .main(partyOkved.isMain());
    }

    private Management convertManagement(com.rbkmoney.questionary_proxy_aggr.base_dadata.Management management) {
        return new Management()
                .post(management.getPost())
                .name(management.getName());
    }

    private Manager convertManager(com.rbkmoney.questionary_proxy_aggr.base_dadata.Manager manager) {
        Manager swagManager = new Manager()
                .fio(manager.getFio())
                .hid(new DaDataHID().hid(manager.getHid()))
                .inn(manager.getInn())
                .name(manager.getName())
                .ogrn(manager.getOgrn())
                .post(manager.getPost());
        if (manager.getType() == com.rbkmoney.questionary_proxy_aggr.base_dadata.ManagerType.LEGAL) {
            swagManager.setType(ManagerType.LEGAL);
        } else if (manager.getType() == com.rbkmoney.questionary_proxy_aggr.base_dadata.ManagerType.EMPLOYEE) {
            swagManager.setType(ManagerType.EMPLOYEE);
        } else if (manager.getType() == com.rbkmoney.questionary_proxy_aggr.base_dadata.ManagerType.FOREIGNER) {
            swagManager.setType(ManagerType.FOREIGNER);
        }
        return swagManager;
    }

}
