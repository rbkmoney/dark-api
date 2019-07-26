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
        PartyContent partyContent = new PartyContent();
        if (value.isSetAddress()) {
            DaDataAddress convert = ctx.convert(value.getAddress(), DaDataAddress.class);
            partyContent.setAddress(ctx.convert(value.getAddress(), DaDataAddress.class));
        }
        partyContent.setDocuments(ctx.convert(value.getDocuments(), PartyDocuments.class));
        partyContent.setInn(value.getInn());
        partyContent.setKpp(value.getKpp());
        if (value.isSetLicenses()) {
            List<DaDataLicense> daDataLicenseList = value.getLicenses().stream()
                    .map(license -> ctx.convert(license, DaDataLicense.class))
                    .collect(Collectors.toList());
            partyContent.setLicenses(daDataLicenseList);
        }

        if (value.isSetName()) {
            partyContent.setName(convertOrgName(value.getName()));
        }

        partyContent.setOgrn(value.getOgrn());
        partyContent.setOgrnDate(value.getOgrnDate());
        partyContent.setOkpo(value.getOkpo());
        partyContent.setValue(value.getValue());
        partyContent.setUnrestrictedValue(value.getUnrestrictedValue());
        partyContent.setOpf(ctx.convert(value.getOpf(), Opf.class));
        partyContent.setBranchCount(value.getBranchCount());
        partyContent.setBranchType(ctx.convert(value.getBranchType(), BranchType.class));
        partyContent.setOkved(value.getOkved());

        if (value.isSetCapital()) {
            partyContent.setCapital(convertPartyCapital(value.getCapital()));
        }

        partyContent.setAuthorities(convertPartyAuthorities(value.getAuthorities()));

        if (value.isSetCitizenship()) {
            partyContent.setCitizenship(convertCitizenshipIP(value.getCitizenship()));
        }

        DaDataHID daDataHID = new DaDataHID();
        daDataHID.setHid(value.getHid());
        partyContent.setHid(daDataHID);

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

        partyContent.setOkvedType(value.getOkvedType());
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
        CitizenshipIP swagCitizenshipIP = new CitizenshipIP();
        swagCitizenshipIP.setAplha3(citizenshipIP.getAlpha3());
        swagCitizenshipIP.setCountryFullName(citizenshipIP.getCountryFullName());
        swagCitizenshipIP.setCountryShortName(citizenshipIP.getCountryShortName());
        swagCitizenshipIP.setNumeric(citizenshipIP.getNumeric());
        return swagCitizenshipIP;
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
        PartyCapital swagPartyCapital = new PartyCapital();
        swagPartyCapital.setType(partyCapital.getType());
        swagPartyCapital.setValue(partyCapital.getValue());
        return swagPartyCapital;
    }

    private OrgName convertOrgName(com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgName orgName) {
        OrgName swagOrgName = new OrgName();
        swagOrgName.setFullName(orgName.getFullName());
        swagOrgName.setShortName(orgName.getShortName());
        swagOrgName.setFullWithOpf(orgName.getFullWithOpf());
        swagOrgName.setShortWithOpf(orgName.getShortWithOpf());
        swagOrgName.setLatin(orgName.getLatin());
        return swagOrgName;
    }

    private Authorities convertAuthorities(com.rbkmoney.questionary_proxy_aggr.base_dadata.Authorities authorities) {
        Authorities swagAuthorities = new Authorities();
        swagAuthorities.setAddress(authorities.getAddress());
        swagAuthorities.setCode(authorities.getCode());
        swagAuthorities.setName(authorities.getName());
        swagAuthorities.setType(authorities.getType());
        return swagAuthorities;
    }

    private PartyOkved convertPartyOkved(com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyOkved partyOkved) {
        PartyOkved swagPartyOkved = new PartyOkved();
        swagPartyOkved.setCode(partyOkved.getCode());
        swagPartyOkved.setName(partyOkved.getName());
        swagPartyOkved.setType(partyOkved.getType());
        swagPartyOkved.setMain(partyOkved.isMain());
        return swagPartyOkved;
    }

    private Management convertManagement(com.rbkmoney.questionary_proxy_aggr.base_dadata.Management management) {
        Management swagManagement = new Management();
        swagManagement.setPost(management.getPost());
        swagManagement.setName(management.getName());
        return swagManagement;
    }

    private Manager convertManager(com.rbkmoney.questionary_proxy_aggr.base_dadata.Manager manager) {
        Manager swagManager = new Manager();
        swagManager.setFio(manager.getFio());
        DaDataHID daDataHID = new DaDataHID();
        daDataHID.setHid(manager.getHid());
        swagManager.setHid(daDataHID);
        swagManager.setInn(manager.getInn());
        swagManager.setName(manager.getName());
        swagManager.setOgrn(manager.getOgrn());
        swagManager.setPost(manager.getPost());

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
