package com.rbkmoney.dark.api.questionaryaggr.utils;

import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderFL;
import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderUL;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderUL;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.License;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponse;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KonturFocusCompareUtil {

    public static void licencesCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse thriftLicencesResponse,
                                 LicencesResponse swagLicencesResponse) {
        Assert.assertEquals(thriftLicencesResponse.getInn(), swagLicencesResponse.getInn());
        Assert.assertEquals(thriftLicencesResponse.getFocusHref(), swagLicencesResponse.getFocusHref());
        Assert.assertEquals(thriftLicencesResponse.getOgrn(), swagLicencesResponse.getOgrn());
        for (int i = 0; i < thriftLicencesResponse.getLicenses().size(); i++) {
            License license = thriftLicencesResponse.getLicenses().get(i);
            KonturFocusLicense swagLicense = swagLicencesResponse.getLicenses().get(i);
            licenseCompare(license, swagLicense);
        }
    }

    private static void licenseCompare(License license, KonturFocusLicense swagLicense) {
        Assert.assertEquals(license.getActivity(), swagLicense.getActivity());
        Assert.assertThat(license.getAddresses(), CoreMatchers.is(swagLicense.getAddresses()));
        Assert.assertEquals(license.getOfficialNum(), swagLicense.getOfficialNum());
        Assert.assertEquals(license.getDate(), swagLicense.getDate());
        Assert.assertEquals(license.getDateEnd(), swagLicense.getDateEnd());
        Assert.assertEquals(license.getDateStart(), swagLicense.getDateStart());
        Assert.assertEquals(license.getIssuerName(), swagLicense.getIssuerNamer());
        Assert.assertThat(license.getServices(), CoreMatchers.is(swagLicense.getServices()));
        Assert.assertEquals(license.getStatusDescription(), swagLicense.getStatusDescription());
    }

    public static void egrDetailsCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse thriftEgrDetailsResponse, EgrDetailsResponse swagEgrDetailsResponse) {
        Assert.assertEquals(thriftEgrDetailsResponse.getInn(), swagEgrDetailsResponse.getInn());
        Assert.assertEquals(thriftEgrDetailsResponse.getOgrn(), swagEgrDetailsResponse.getOgrn());
        Assert.assertEquals(thriftEgrDetailsResponse.getFocusHref(), swagEgrDetailsResponse.getFocusHref());
        if (thriftEgrDetailsResponse.getContractor().isSetIndividualEntity()) {
            var thriftIndividualEntity = thriftEgrDetailsResponse.getContractor().getIndividualEntity();
            EgrDetailsIndividualEntity swagEgrDetailsIndividualEntity = (EgrDetailsIndividualEntity) swagEgrDetailsResponse.getContractor();
            shortenedAddressCompare(thriftIndividualEntity.getShortenedAddress(), swagEgrDetailsIndividualEntity.getShortenedAddress());
            activitiesCompare(thriftIndividualEntity.getActivities(), swagEgrDetailsIndividualEntity.getActivities());
            for (int i = 0; i < thriftIndividualEntity.getEgrRecords().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrRecord egrRecord = thriftIndividualEntity.getEgrRecords().get(i);
                EgrRecord swagEgrRecord = swagEgrDetailsIndividualEntity.getEgrRecords().get(i);
                egrRecordCompare(egrRecord, swagEgrRecord);
            }
            Assert.assertEquals(thriftIndividualEntity.getFomsRegNumber(), swagEgrDetailsIndividualEntity.getFomsRegNumber());
            Assert.assertEquals(thriftIndividualEntity.getFssRegNumber(), swagEgrDetailsIndividualEntity.getFssRegNumber());
            nalogRegBodyCompare(thriftIndividualEntity.getNalogRegBody(), swagEgrDetailsIndividualEntity.getNalogRegBody());
            Assert.assertEquals(thriftIndividualEntity.getOkato(), swagEgrDetailsIndividualEntity.getOkato());
            Assert.assertEquals(thriftIndividualEntity.getOkpo(), swagEgrDetailsIndividualEntity.getOkpo());
            regBodyCompare(thriftIndividualEntity.getRegBody(), swagEgrDetailsIndividualEntity.getRegBody());
            regInfoCompare(thriftIndividualEntity.getRegInfo(), swagEgrDetailsIndividualEntity.getRegInfo());
            Assert.assertEquals(thriftIndividualEntity.getPfrRegNumber(), swagEgrDetailsIndividualEntity.getPfrRegNumber());
        } else if (thriftEgrDetailsResponse.getContractor().isSetLegalEntity()) {
            var thriftLegalEntity = thriftEgrDetailsResponse.getContractor().getLegalEntity();
            EgrDetailsLegalEntity swagLegalEntity = (EgrDetailsLegalEntity) swagEgrDetailsResponse.getContractor();
            activitiesCompare(thriftLegalEntity.getActivities(), swagLegalEntity.getActivities());
            shareholdersCompare(thriftLegalEntity.getShareholders(), swagLegalEntity.getShareHolders());
            for (int i = 0; i < thriftLegalEntity.getEgrRecords().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrRecord egrRecord = thriftLegalEntity.getEgrRecords().get(i);
                EgrRecord swagEgrRecord = swagLegalEntity.getEgrRecords().get(i);
                egrRecordCompare(egrRecord, swagEgrRecord);
            }
            for (int i = 0; i < thriftLegalEntity.getPredecessors().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Predecessor predecessor = thriftLegalEntity.getPredecessors().get(i);
                Predecessor swagPredecessor = swagLegalEntity.getPredecessor().get(i);
                predecessorCompare(predecessor, swagPredecessor);
            }
            for (int i = 0; i < thriftLegalEntity.getSuccessors().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Successor successor = thriftLegalEntity.getSuccessors().get(i);
                Successor swagSuccessor = swagLegalEntity.getSuccessor().get(i);
                successorCompare(successor, swagSuccessor);
            }
            for (int i = 0; i < thriftLegalEntity.getShareholders().getShareholdersOther().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther shareHolderOther =
                        thriftLegalEntity.getShareholders().getShareholdersOther().get(i);
                ShareHolderOther swagShareHolderOther = swagLegalEntity.getShareHolders().getShareHoldersOther().get(i);
                shareHolderOtherCompare(shareHolderOther, swagShareHolderOther);
            }
            for (int i = 0; i < thriftLegalEntity.getShareholders().getShareholdersUl().size(); i++) {
                ShareHolderUL shareHolderUL = thriftLegalEntity.getShareholders().getShareholdersUl().get(i);
                ShareHolderUl swagShareHolderUl = swagLegalEntity.getShareHolders().getShareHoldersUl().get(i);
                shareHolderULCompare(shareHolderUL, swagShareHolderUl);
            }
            for (int i = 0; i < thriftLegalEntity.getShareholders().getShareholdersFl().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl shareHolderFL =
                        thriftLegalEntity.getShareholders().getShareholdersFl().get(i);
                ShareHolderFl swagShareHolderUl = swagLegalEntity.getShareHolders().getShareHoldersFl().get(i);
                shareHolderFlCompare(shareHolderFL, swagShareHolderUl);
            }
            for (int i = 0; i < thriftLegalEntity.getFoundersForeign().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderForeign founderForeign =
                        thriftLegalEntity.getFoundersForeign().get(i);
                FounderForeign swagFounderForeign = swagLegalEntity.getFoundersForeign().get(i);
                founderForeignCompare(founderForeign, swagFounderForeign);
            }
            Assert.assertEquals(thriftLegalEntity.getOkpo(), swagLegalEntity.getOkpo());
            Assert.assertEquals(thriftLegalEntity.getPfrRegNumber(), swagLegalEntity.getPfrRegNumber());
            Assert.assertEquals(thriftLegalEntity.getStatedCapital().getDate(), swagLegalEntity.getStatedCapital().getDate());
            Assert.assertEquals(thriftLegalEntity.getStatedCapital().getSum(), swagLegalEntity.getStatedCapital().getSum().longValue());
            nalogRegBodyCompare(thriftLegalEntity.getNalogRegBody(), swagLegalEntity.getNalogRegBody());
            regInfoCompare(thriftLegalEntity.getRegInfo(), swagLegalEntity.getRegInfo());
            regBodyCompare(thriftLegalEntity.getRegBody(), swagLegalEntity.getRegBody());
            for (int i = 0; i < thriftLegalEntity.getFoundersFl().size(); i++) {
                FounderFL founderFL = thriftLegalEntity.getFoundersFl().get(i);
                FounderFl swagFounderFl = swagLegalEntity.getFoundersFl().get(i);
                founderFlCompare(founderFL, swagFounderFl);
            }
            for (int i = 0; i < thriftLegalEntity.getFoundersUl().size(); i++) {
                FounderUL founderUL = thriftLegalEntity.getFoundersUl().get(i);
                FounderUl swagFounderUl = swagLegalEntity.getFoundersUl().get(i);
                founderUlCompare(founderUL, swagFounderUl);
            }
            egrDetailsHistoryCompare(thriftLegalEntity.getHistory(), swagLegalEntity.getHistory());
        }
    }

    private static void egrDetailsHistoryCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsHistory thriftEgrDetailsHistory, EgrDetailsHistory swagEgrDetailsHistory) {
        for (int i = 0; i < thriftEgrDetailsHistory.getShareholders().getShareholdersOther().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther shareHolderOther = thriftEgrDetailsHistory.getShareholders().getShareholdersOther().get(i);
            ShareHolderOther swagShareHolderOther = swagEgrDetailsHistory.getShareholdersOther().get(i);
            shareHolderOtherCompare(shareHolderOther, swagShareHolderOther);
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getShareholders().getShareholdersUl().size(); i++) {
            ShareHolderUL shareHolderUL = thriftEgrDetailsHistory.getShareholders().getShareholdersUl().get(i);
            ShareHolderUl swagShareHolderUl = swagEgrDetailsHistory.getShareHoldersUl().get(i);
            shareHolderULCompare(shareHolderUL, swagShareHolderUl);
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getShareholders().getShareholdersFl().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl shareHolderFl = thriftEgrDetailsHistory.getShareholders().getShareholdersFl().get(i);
            ShareHolderFl swagShareHolderFl = swagEgrDetailsHistory.getShareHoldersFl().get(i);
            shareHolderFlCompare(shareHolderFl, swagShareHolderFl);
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getStatedCapitals().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.StatedCapital statedCapital = thriftEgrDetailsHistory.getStatedCapitals().get(i);
            StatedCapital swagStatedCapital = swagEgrDetailsHistory.getStatedCapitals().get(i);
            Assert.assertEquals(statedCapital.getSum(), swagStatedCapital.getSum().longValue());
            Assert.assertEquals(statedCapital.getDate(), swagStatedCapital.getDate());
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getFoundersFl().size(); i++) {
            FounderFL founderFL = thriftEgrDetailsHistory.getFoundersFl().get(i);
            FounderFl swagFounderFl = swagEgrDetailsHistory.getFoundersFl().get(i);
            founderFlCompare(founderFL, swagFounderFl);
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getFoundersUl().size(); i++) {
            FounderUL founderUL = thriftEgrDetailsHistory.getFoundersUl().get(i);
            FounderUl swagFounderUl = swagEgrDetailsHistory.getFoundersUl().get(i);
            founderUlCompare(founderUL, swagFounderUl);
        }
    }

    private static void founderFlCompare(FounderFL thriftFounderFL, FounderFl swagFounderFl) {
        Assert.assertEquals(thriftFounderFL.getDate(), swagFounderFl.getDate());
        Assert.assertEquals(thriftFounderFL.getFio(), swagFounderFl.getFio());
        Assert.assertEquals(thriftFounderFL.getFirstDate(), swagFounderFl.getFirstDate());
        Assert.assertEquals(thriftFounderFL.getInnfl(), swagFounderFl.getInnfl());
        shareCompare(thriftFounderFL.getShare(), swagFounderFl.getShare());
    }

    private static void founderUlCompare(FounderUL thriftFounderUL, FounderUl swagFounderUl) {
        Assert.assertEquals(thriftFounderUL.getDate(), swagFounderUl.getDate());
        Assert.assertEquals(thriftFounderUL.getFirstDate(), swagFounderUl.getFirstDate());
        Assert.assertEquals(thriftFounderUL.getInn(), swagFounderUl.getInn());
        Assert.assertEquals(thriftFounderUL.getOgrn(), swagFounderUl.getOgrn());
        shareCompare(thriftFounderUL.getShare(), swagFounderUl.getShare());
    }

    private static void founderForeignCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderForeign thriftFounderForeign, FounderForeign swagFounderForeign) {
        Assert.assertEquals(thriftFounderForeign.getCountry(), swagFounderForeign.getCountry());
        Assert.assertEquals(thriftFounderForeign.getDate(), swagFounderForeign.getDate());
        Assert.assertEquals(thriftFounderForeign.getFirstDate(), swagFounderForeign.getFirstDate());
        Assert.assertEquals(thriftFounderForeign.getFullName(), swagFounderForeign.getFullName());
        shareCompare(thriftFounderForeign.getShare(), swagFounderForeign.getShare());
    }

    private static void shareCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Share thriftShare, Share swagShare) {
        Assert.assertEquals(thriftShare.getPercentageDenominator(), ((int) swagShare.getPercentageDenominator()));
        Assert.assertEquals(thriftShare.getPercentageNominator(), ((int) swagShare.getPercentageNominator()));
        Assert.assertEquals(0, Double.compare(thriftShare.getPercentagePlain(), swagShare.getPercentagePlain().doubleValue()));
        Assert.assertEquals(thriftShare.getSum(), swagShare.getSum().longValue());
    }

    private static void shareHolderFlCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl thriftShareHolderFL, ShareHolderFl swagShareFolderFl) {
        Assert.assertEquals(thriftShareHolderFL.getAddress(), swagShareFolderFl.getAddress());
        Assert.assertEquals(0, Double.compare(thriftShareHolderFL.getCapitalSharesPercent(), swagShareFolderFl.getCapitalSharesPercent().doubleValue()));
        Assert.assertEquals(0, Double.compare(thriftShareHolderFL.getVotingSharesPercent(), swagShareFolderFl.getVotingSharesPercent().doubleValue()));
        Assert.assertEquals(thriftShareHolderFL.getDate(), swagShareFolderFl.getDate());
    }

    private static void shareHolderULCompare(ShareHolderUL thriftShareHolderUL, ShareHolderUl swagShareHolderUl) {
        Assert.assertEquals(thriftShareHolderUL.getAddress(), swagShareHolderUl.getAddress());
        Assert.assertEquals(0, Double.compare(thriftShareHolderUL.getCapitalSharesPercent(), swagShareHolderUl.getCapitalSharesPercent().doubleValue()));
        Assert.assertEquals(0, Double.compare(thriftShareHolderUL.getVotingSharesPercent(), swagShareHolderUl.getVotingSharesPercent().doubleValue()));
        Assert.assertEquals(thriftShareHolderUL.getDate(), swagShareHolderUl.getDate());
        Assert.assertEquals(thriftShareHolderUL.getFullName(), swagShareHolderUl.getFullName());
    }

    private static void shareHolderOtherCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther thriftShareHolderOther, ShareHolderOther swagShareHolderOther) {
        Assert.assertEquals(thriftShareHolderOther.getAddress(), swagShareHolderOther.getAddress());
        Assert.assertEquals(0, Double.compare(thriftShareHolderOther.getCapitalSharesPercent(), swagShareHolderOther.getCapitalSharesPercent().doubleValue()));
        Assert.assertEquals(0, Double.compare(thriftShareHolderOther.getVotingSharesPercent(), swagShareHolderOther.getVotingSharesPercent().doubleValue()));
        Assert.assertEquals(thriftShareHolderOther.getDate(), swagShareHolderOther.getDate());
        Assert.assertEquals(thriftShareHolderOther.getFullName(), swagShareHolderOther.getFullName());
    }

    private static void successorCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Successor thriftSuccessor, Successor swagSuccessor) {
        Assert.assertEquals(thriftSuccessor.getDate(), swagSuccessor.getDate());
        Assert.assertEquals(thriftSuccessor.getInn(), swagSuccessor.getInn());
        Assert.assertEquals(thriftSuccessor.getName(), swagSuccessor.getName());
        Assert.assertEquals(thriftSuccessor.getOgrn(), swagSuccessor.getOgrn());
    }

    private static void predecessorCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Predecessor thriftPredecessor, Predecessor swagPredecessor) {
        Assert.assertEquals(thriftPredecessor.getDate(), swagPredecessor.getDate());
        Assert.assertEquals(thriftPredecessor.getInn(), swagPredecessor.getInn());
        Assert.assertEquals(thriftPredecessor.getName(), swagPredecessor.getName());
        Assert.assertEquals(thriftPredecessor.getOgrn(), swagPredecessor.getOgrn());
    }

    private static void regInfoCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.RegInfo thriftRegInfo, RegInfo swagRegInfo) {
        Assert.assertEquals(thriftRegInfo.getOgrnDate(), swagRegInfo.getOgrnDate());
        Assert.assertEquals(thriftRegInfo.getRegName(), swagRegInfo.getRegName());
    }

    private static void regBodyCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.RegBody thriftRegBody, RegBody swagRegBody) {
        Assert.assertEquals(thriftRegBody.getDate(), swagRegBody.getDate());
        Assert.assertEquals(thriftRegBody.getKpp(), swagRegBody.getKpp());
        Assert.assertEquals(thriftRegBody.getNalogCode(), swagRegBody.getNalogCode());
        Assert.assertEquals(thriftRegBody.getNalogName(), swagRegBody.getNalogName());
        Assert.assertEquals(thriftRegBody.getNalogRegDate(), swagRegBody.getNalogRegDate());
    }

    private static void nalogRegBodyCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.NalogRegBody thriftNalogRegBody, NalogRegBody swagNalogRegBody) {
        Assert.assertEquals(thriftNalogRegBody.getDate(), swagNalogRegBody.getDate());
        Assert.assertEquals(thriftNalogRegBody.getKpp(), swagNalogRegBody.getKpp());
        Assert.assertEquals(thriftNalogRegBody.getNalogCode(), swagNalogRegBody.getNalogCode());
        Assert.assertEquals(thriftNalogRegBody.getNalogName(), swagNalogRegBody.getNalogName());
        Assert.assertEquals(thriftNalogRegBody.getNalogRegDate(), swagNalogRegBody.getNalogRegDate());
    }

    private static void shortenedAddressCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShortenedAddress thriftShortenedAddress, ShortenedAddress swagShortenedAddress) {
        toponimCompare(thriftShortenedAddress.getCity(), swagShortenedAddress.getCity());
        toponimCompare(thriftShortenedAddress.getDistrict(), swagShortenedAddress.getDistrict());
        toponimCompare(thriftShortenedAddress.getRegionName(), swagShortenedAddress.getRegionName());
        toponimCompare(thriftShortenedAddress.getSettlement(), swagShortenedAddress.getSettlement());
        Assert.assertEquals(thriftShortenedAddress.getRegionCode(), thriftShortenedAddress.getRegionCode());
    }

    private static void activitiesCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Activity thriftActivity, Activity swagAcitivty) {
        Assert.assertEquals(thriftActivity.getPrincipalActivity().getCode(), swagAcitivty.getPrincipalActivity().getCode());
        Assert.assertEquals(thriftActivity.getPrincipalActivity().getText(), swagAcitivty.getPrincipalActivity().getText());
        Assert.assertEquals(thriftActivity.getPrincipalActivity().getDate(), swagAcitivty.getPrincipalActivity().getDate());
        for (int i = 0; i < thriftActivity.getComplementaryActivities().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ComplementaryActivity complementaryActivity = thriftActivity.getComplementaryActivities().get(i);
            ComplementaryActivity swagComplementaryActivity = swagAcitivty.getComplementaryActivities().get(i);
            Assert.assertEquals(complementaryActivity.getCode(), swagComplementaryActivity.getCode());
            Assert.assertEquals(complementaryActivity.getDate(), swagComplementaryActivity.getDate());
            Assert.assertEquals(complementaryActivity.getText(), swagComplementaryActivity.getText());
        }
    }

    private static void egrRecordCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrRecord thriftEgrRecord, EgrRecord swagEgrRecord) {
        Assert.assertEquals(thriftEgrRecord.getDate(), swagEgrRecord.getDate());
        Assert.assertEquals(thriftEgrRecord.getGrn(), swagEgrRecord.getGrn());
        Assert.assertEquals(thriftEgrRecord.getName(), swagEgrRecord.getName());
        Assert.assertEquals(thriftEgrRecord.getRegCode(), swagEgrRecord.getRegCode());
        Assert.assertEquals(thriftEgrRecord.getRegName(), swagEgrRecord.getRegName());
        for (int i = 0; i < thriftEgrRecord.getCertificates().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Certificate certificate = thriftEgrRecord.getCertificates().get(i);
            Certificate swagCertificate = swagEgrRecord.getCertificates().get(i);
            Assert.assertEquals(certificate.getDate(), swagCertificate.getDate());
            Assert.assertEquals(certificate.getSerialNumber(), swagCertificate.getSerialNumber());
        }
        for (int i = 0; i < thriftEgrRecord.getDocuments().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.RecordDocument recordDocument = thriftEgrRecord.getDocuments().get(i);
            RecordDocument swagRecordDocument = swagEgrRecord.getDocuments().get(i);
            Assert.assertEquals(recordDocument.getDate(), swagRecordDocument.getDate());
            Assert.assertEquals(recordDocument.getName(), swagRecordDocument.getName());
        }
    }


    public static void reqResponseCompare(ReqResponse thriftReqResponse, com.rbkmoney.swag.questionary_aggr_proxy.model.ReqResponse swagReqResponse) {
        Assert.assertEquals(thriftReqResponse.getInn(), swagReqResponse.getInn());
        Assert.assertEquals(thriftReqResponse.getOgrn(), swagReqResponse.getOgrn());
        Assert.assertEquals(thriftReqResponse.getBriefReport().getSummary().isGreenStatements(),
                swagReqResponse.getBriefReport().getSummary().isGreenStatements());
        Assert.assertEquals(thriftReqResponse.getBriefReport().getSummary().isRedStatements(),
                swagReqResponse.getBriefReport().getSummary().isRedStatements());
        Assert.assertEquals(thriftReqResponse.getBriefReport().getSummary().isYellowStatements(),
                swagReqResponse.getBriefReport().getSummary().isYellowStatements());
        Assert.assertEquals(thriftReqResponse.getBriefReport().getHref(), swagReqResponse.getBriefReport().getHref());
        Assert.assertEquals(thriftReqResponse.getContactPhones().getCount(), swagReqResponse.getContactPhones().getCount().longValue());
        Assert.assertThat(thriftReqResponse.getContactPhones().getPhones(), CoreMatchers.is(swagReqResponse.getContactPhones().getPhones()));
        if (thriftReqResponse.getPrivateEntity().isSetIndividualEntity()) {
            var reqIndividualEntity = thriftReqResponse.getPrivateEntity().getIndividualEntity();
            ReqIndividualEntity swagReqIndividualEntity = (ReqIndividualEntity) swagReqResponse.getContractor();
            Assert.assertEquals(reqIndividualEntity.getDissolutionDate(), swagReqIndividualEntity.getDissolutionDate());
            Assert.assertEquals(reqIndividualEntity.getFio(), swagReqIndividualEntity.getFio());
            Assert.assertEquals(reqIndividualEntity.getOkato(), swagReqIndividualEntity.getOkato());
            Assert.assertEquals(reqIndividualEntity.getOkfs(), swagReqIndividualEntity.getOkfs());
            Assert.assertEquals(reqIndividualEntity.getOkogu(), swagReqIndividualEntity.getOkogu());
            Assert.assertEquals(reqIndividualEntity.getOkopf(), swagReqIndividualEntity.getOkopf());
            Assert.assertEquals(reqIndividualEntity.getOkpo(), swagReqIndividualEntity.getOkpo());
            Assert.assertEquals(reqIndividualEntity.getOktmo(), swagReqIndividualEntity.getOktmo());
            Assert.assertEquals(reqIndividualEntity.getRegistrationDate(), swagReqIndividualEntity.getRegistrationDate());
            Assert.assertEquals(reqIndividualEntity.getStatusDetail().getDate(), swagReqIndividualEntity.getStatusDetail().getDate());
            Assert.assertEquals(reqIndividualEntity.getStatusDetail().getStatus(), swagReqIndividualEntity.getStatusDetail().getStatus());
        } else if (thriftReqResponse.getPrivateEntity().isSetLegalEntity()) {
            var reqLegalEntity = thriftReqResponse.getPrivateEntity().getLegalEntity();
            ReqLegalEntity swagReqLegalEntity = (ReqLegalEntity) swagReqResponse.getContractor();
            for (int i = 0; i < swagReqLegalEntity.getBranches().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch branch = reqLegalEntity.getBranches().get(i);
                Branch swagBranch = swagReqLegalEntity.getBranches().get(i);
                branchCompare(branch, swagBranch);
            }
            Assert.assertEquals(reqLegalEntity.getStatus().getDate(), swagReqLegalEntity.getStatus().getDate());
            Assert.assertEquals(reqLegalEntity.getStatus().getStatus(), swagReqLegalEntity.getStatus().getStatus());
            for (int i = 0; i < swagReqLegalEntity.getHeads().size(); i++) {
                Head swagHead = swagReqLegalEntity.getHeads().get(i);
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Head head = reqLegalEntity.getHeads().get(i);
                headCompare(head, swagHead);
            }
            legalAddressCompare(reqLegalEntity.getLegalAddress(), swagReqLegalEntity.getLegalAddress());
            legalNameCompare(reqLegalEntity.getLegalName(), swagReqLegalEntity.getLegalName());
            for (int i = 0; i < reqLegalEntity.getManagementCompanies().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ManagementCompany managementCompany = reqLegalEntity.getManagementCompanies().get(i);
                ManagementCompany swagManagementCompany = swagReqLegalEntity.getManagementCompanies().get(i);
                managementCompanyCompare(managementCompany, swagManagementCompany);
            }
            Assert.assertEquals(reqLegalEntity.getOkato(), swagReqLegalEntity.getOkato());
            Assert.assertEquals(reqLegalEntity.getOkfs(), swagReqLegalEntity.getOkfs());
            Assert.assertEquals(reqLegalEntity.getOkogu(), swagReqLegalEntity.getOkogu());
            Assert.assertEquals(reqLegalEntity.getOkopf(), swagReqLegalEntity.getOkopf());
            Assert.assertEquals(reqLegalEntity.getOkpo(), swagReqLegalEntity.getOkpo());
            Assert.assertEquals(reqLegalEntity.getOktmo(), swagReqLegalEntity.getOktmo());
            Assert.assertEquals(reqLegalEntity.getOpf(), swagReqLegalEntity.getOpf());
            Assert.assertEquals(reqLegalEntity.getDissolutionDate(), swagReqLegalEntity.getDissolutionDate());
            Assert.assertEquals(reqLegalEntity.getRegistrationDate(), swagReqLegalEntity.getRegistrationDate());
            Assert.assertEquals(reqLegalEntity.getKpp(), swagReqLegalEntity.getKpp());
            for (int i = 0; i < reqLegalEntity.getHistory().getLegalAddresses().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalAddress legalAddress = reqLegalEntity.getHistory().getLegalAddresses().get(i);
                LegalAddress swagLegalAddresses = swagReqLegalEntity.getHistory().getLegalAddresses().get(i);
                legalAddressCompare(legalAddress, swagLegalAddresses);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getLegalNames().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalName legalName = reqLegalEntity.getHistory().getLegalNames().get(i);
                LegalName swagLegalName = swagReqLegalEntity.getHistory().getLegalNames().get(i);
                legalNameCompare(legalName, swagLegalName);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getKpps().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqKppHistory reqKppHistory = reqLegalEntity.getHistory().getKpps().get(i);
                ReqKppHistory swagReqKppHistory = swagReqLegalEntity.getHistory().getKpps().get(i);
                kppsNameCompare(reqKppHistory, swagReqKppHistory);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getManagementCompanies().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ManagementCompany managementCompany = reqLegalEntity.getHistory().getManagementCompanies().get(i);
                ManagementCompany swagManagementCompany = swagReqLegalEntity.getHistory().getManagementCompanies().get(i);
                managementCompanyCompare(managementCompany, swagManagementCompany);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getHeads().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Head head = reqLegalEntity.getHistory().getHeads().get(i);
                Head swagHead = swagReqLegalEntity.getHistory().getHeads().get(i);
                headCompare(head, swagHead);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getBranches().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch branch = reqLegalEntity.getHistory().getBranches().get(i);
                Branch swagBranch = swagReqLegalEntity.getHistory().getBranches().get(i);
                branchCompare(branch, swagBranch);
            }
        }
    }

    public static void beneficialOwnerResponseCompare(BeneficialOwnerResponse thriftBeneficialOwnerResponse, com.rbkmoney.swag.questionary_aggr_proxy.model.BeneficialOwnerResponse swagBeneficialOwnerResponse) {
        Assert.assertEquals(thriftBeneficialOwnerResponse.getInn(), swagBeneficialOwnerResponse.getInn());
        Assert.assertEquals(thriftBeneficialOwnerResponse.getOgrn(), swagBeneficialOwnerResponse.getOgrn());
        Assert.assertEquals(thriftBeneficialOwnerResponse.getFocusHref(), swagBeneficialOwnerResponse.getFocusHref());
        Assert.assertEquals(thriftBeneficialOwnerResponse.getStatedCapital().getDate(),
                swagBeneficialOwnerResponse.getStatedCapital().getDate());
        Assert.assertEquals(Long.valueOf(thriftBeneficialOwnerResponse.getStatedCapital().getSum()),
                swagBeneficialOwnerResponse.getStatedCapital().getSum());
        beneficialOwnerCompare(thriftBeneficialOwnerResponse.getBeneficialOwners(), swagBeneficialOwnerResponse.getBeneficialOwners());
        beneficialOwnerCompare(thriftBeneficialOwnerResponse.getHistoricalBeneficialOwners(), swagBeneficialOwnerResponse.getHistoricalBeneficialOwners());
    }

    private static void beneficialOwnerCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwners thriftBeneficialOwners, BeneficialOwners swagBeneficialOwners) {
        int thriftBeneficialOwnersUlSize = thriftBeneficialOwners.getBeneficialOwnersUl().size();
        for (int i = 0; i < thriftBeneficialOwnersUlSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerUl thriftBeneficialOwnerUl =
                    thriftBeneficialOwners.getBeneficialOwnersUl().get(i);
            BeneficialOwnerUl swagBeneficialOwnerUl = swagBeneficialOwners.getBeneficialOwnersUl().get(i);
            beneficialOwnerUlCompare(thriftBeneficialOwnerUl, swagBeneficialOwnerUl);
        }
        int thriftBeneficialOwnersFlSize = thriftBeneficialOwners.getBeneficialOwnersFl().size();
        for (int i = 0; i < thriftBeneficialOwnersFlSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerFl thriftBeneficialOwnerFl =
                    thriftBeneficialOwners.getBeneficialOwnersFl().get(i);
            BeneficialOwnerFl swagBeneficialOwnerFl = swagBeneficialOwners.getBeneficialOwnerFl().get(i);
            beneficialOwnerFlCompare(thriftBeneficialOwnerFl, swagBeneficialOwnerFl);
        }
        int thriftBeneficialOwnersOtherSize = thriftBeneficialOwners.getBeneficialOwnersOther().size();
        for (int i = 0; i < thriftBeneficialOwnersOtherSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerOther thriftBeneficialOwnerOther =
                    thriftBeneficialOwners.getBeneficialOwnersOther().get(i);
            BeneficialOwnerOther swagBeneficialOwnerOther = swagBeneficialOwners.getBeneficialOwnersOther().get(i);
            beneficialOwnerOtherCompare(thriftBeneficialOwnerOther, swagBeneficialOwnerOther);
        }
        int thriftBeneficialOwnersForeignSize = thriftBeneficialOwners.getBeneficialOwnersForeign().size();
        for (int i = 0; i < thriftBeneficialOwnersForeignSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerForeign thriftBeneficialOwnerForeign =
                    thriftBeneficialOwners.getBeneficialOwnersForeign().get(i);
            BeneficialOwnerForeign swagBeneficialOwnerForeign = swagBeneficialOwners.getBeneficialOwnersForeign().get(i);
            beneficialOwnerForeignCompare(thriftBeneficialOwnerForeign, swagBeneficialOwnerForeign);
        }
    }

    private static void branchCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch thriftBranch, Branch swagBranch) {
        Assert.assertEquals(thriftBranch.getDate(), swagBranch.getDate());
        Assert.assertEquals(thriftBranch.getName(), swagBranch.getName());
        Assert.assertEquals(thriftBranch.getAddressRf().getBulkRaw(), swagBranch.getAddressRf().getBulkRaw());
        Assert.assertEquals(thriftBranch.getAddressRf().getFlatRaw(), swagBranch.getAddressRf().getFlatRaw());
        Assert.assertEquals(thriftBranch.getAddressRf().getHouseRaw(), swagBranch.getAddressRf().getHouseRaw());
        Assert.assertEquals(thriftBranch.getAddressRf().getKladrCode(), swagBranch.getAddressRf().getKladrCode());
        Assert.assertEquals(thriftBranch.getAddressRf().getRegionCode(), swagBranch.getAddressRf().getRegionCode());
        Assert.assertEquals(thriftBranch.getAddressRf().getZipCode(), swagBranch.getAddressRf().getZipCode());
        addressRFCompare(thriftBranch.getAddressRf(), swagBranch.getAddressRf());
    }

    private static void addressRFCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ParsedAddressRF thriftParsedAddressRF,
                                  ParsedAddressRF swagParsedAddressRF) {
        toponimCompare(thriftParsedAddressRF.getBulk(), swagParsedAddressRF.getBulk());
        toponimCompare(thriftParsedAddressRF.getCity(), swagParsedAddressRF.getCity());
        toponimCompare(thriftParsedAddressRF.getDistrict(), swagParsedAddressRF.getDistrict());
        toponimCompare(thriftParsedAddressRF.getFlat(), swagParsedAddressRF.getFlat());
        toponimCompare(thriftParsedAddressRF.getHouse(), swagParsedAddressRF.getHouse());
        toponimCompare(thriftParsedAddressRF.getRegionName(), swagParsedAddressRF.getRegionName());
        toponimCompare(thriftParsedAddressRF.getSettlement(), swagParsedAddressRF.getSettlement());
    }

    private static void toponimCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Toponim thriftToponim, Toponim swagToponim) {
        Assert.assertEquals(thriftToponim.getTopoFullName(), swagToponim.getTopoFullName());
        Assert.assertEquals(thriftToponim.getTopoShortName(), swagToponim.getTopoShortName());
        Assert.assertEquals(thriftToponim.getTopoValue(), swagToponim.getTopoValue());
    }

    private static void headCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Head thriftHead, Head swagHead) {
        Assert.assertEquals(thriftHead.getDate(), swagHead.getDate());
        Assert.assertEquals(thriftHead.getFio(), swagHead.getFio());
        Assert.assertEquals(thriftHead.getFirstDate(), swagHead.getFirstDate());
        Assert.assertEquals(thriftHead.getInnfl(), swagHead.getInnfl());
        Assert.assertEquals(thriftHead.getPosition(), swagHead.getPosition());
    }

    private static void managementCompanyCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ManagementCompany thriftManagementCompany,
                                          ManagementCompany swagManagementCompany) {
        Assert.assertEquals(thriftManagementCompany.getDate(), swagManagementCompany.getDate());
        Assert.assertEquals(thriftManagementCompany.getFirstDate(), swagManagementCompany.getFirstDate());
        Assert.assertEquals(thriftManagementCompany.getInn(), swagManagementCompany.getInn());
        Assert.assertEquals(thriftManagementCompany.getName(), swagManagementCompany.getName());
        Assert.assertEquals(thriftManagementCompany.getOgrn(), swagManagementCompany.getOgrn());
    }

    private static void legalNameCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalName thriftLegalName, LegalName swagLegalName) {
        Assert.assertEquals(thriftLegalName.getDate(), swagLegalName.getDate());
        Assert.assertEquals(thriftLegalName.getFullName(), swagLegalName.getFullName());
        Assert.assertEquals(thriftLegalName.getShortName(), swagLegalName.getShortName());
    }

    private static void legalAddressCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalAddress thriftLegalAddress,
                                     LegalAddress swagLegalAddress) {
        Assert.assertEquals(thriftLegalAddress.getFirstDate(), swagLegalAddress.getFirstDate());
        Assert.assertEquals(thriftLegalAddress.getDate(), swagLegalAddress.getDate());
        addressRFCompare(thriftLegalAddress.getAddressRf(), swagLegalAddress.getAddressRf());
    }

    private static void kppsNameCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqKppHistory thriftReqKppHistory, ReqKppHistory swagReqKppHistory) {
        Assert.assertEquals(thriftReqKppHistory.getDate(), swagReqKppHistory.getDate());
        Assert.assertEquals(thriftReqKppHistory.getKpp(), swagReqKppHistory.getKpp());
    }

    private static void beneficialOwnerUlCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerUl thriftBeneficialOwnerUl,
                                         BeneficialOwnerUl swagBeneficialOwnerUl) {
        Assert.assertEquals(thriftBeneficialOwnerUl.getInn(), swagBeneficialOwnerUl.getInn());
        Assert.assertEquals(thriftBeneficialOwnerUl.getFullName(), swagBeneficialOwnerUl.getFullName());
        Assert.assertEquals(thriftBeneficialOwnerUl.getOgrn(), swagBeneficialOwnerUl.getOgrn());
        Assert.assertEquals(0, Double.compare(thriftBeneficialOwnerUl.getShare(), swagBeneficialOwnerUl.getShare().doubleValue()));
        Assert.assertEquals(thriftBeneficialOwnerUl.isIsAccurate(), swagBeneficialOwnerUl.isIsAccurate());
    }

    private static void beneficialOwnerFlCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerFl thriftBeneficialOwnerFl,
                                                 BeneficialOwnerFl swagBeneficialOwnerFl) {
        Assert.assertEquals(thriftBeneficialOwnerFl.getFio(), swagBeneficialOwnerFl.getFio());
        Assert.assertEquals(thriftBeneficialOwnerFl.getInnfl(), swagBeneficialOwnerFl.getInnfl());
        Assert.assertEquals(0, Double.compare(thriftBeneficialOwnerFl.getShare(), swagBeneficialOwnerFl.getShare().doubleValue()));
        Assert.assertEquals(thriftBeneficialOwnerFl.isIsAccurate(), swagBeneficialOwnerFl.isIsAccurate());
    }

    private static void beneficialOwnerOtherCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerOther thriftBeneficialOwnerOther,
                                                    BeneficialOwnerOther swagBeneficialOwnerOther) {
        Assert.assertEquals(thriftBeneficialOwnerOther.getFullName(), swagBeneficialOwnerOther.getFullname());
        Assert.assertEquals(0, Double.compare(thriftBeneficialOwnerOther.getShare(), swagBeneficialOwnerOther.getShare().doubleValue()));
        Assert.assertEquals(thriftBeneficialOwnerOther.isIsAccurate(), swagBeneficialOwnerOther.isIsAccurate());
    }

    private static void beneficialOwnerForeignCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerForeign thriftBeneficialOwnerForeign, BeneficialOwnerForeign swagBeneficialOwnerForeign) {
        Assert.assertEquals(thriftBeneficialOwnerForeign.getCountry(), swagBeneficialOwnerForeign.getCountry());
        Assert.assertEquals(thriftBeneficialOwnerForeign.getFullName(), swagBeneficialOwnerForeign.getFullName());
        Assert.assertEquals(0, Double.compare(thriftBeneficialOwnerForeign.getShare(), swagBeneficialOwnerForeign.getShare().doubleValue()));
        Assert.assertEquals(thriftBeneficialOwnerForeign.isIsAccurate(), swagBeneficialOwnerForeign.isIsAccurate());
    }

    private static void shareholdersCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolders thriftShareholders, ShareHolders swagShareholders) {
        Assert.assertEquals(thriftShareholders.getDate(), swagShareholders.getDate());
        int thriftShareholdersFl = thriftShareholders.getShareholdersFl().size();
        for (int i = 0; i < thriftShareholdersFl; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl thriftShareHolderFl = thriftShareholders.getShareholdersFl().get(i);
            ShareHolderFl swagShareHolderFl = swagShareholders.getShareHoldersFl().get(i);
            Assert.assertEquals(thriftShareHolderFl.getAddress(), swagShareHolderFl.getAddress());
            Assert.assertEquals(thriftShareHolderFl.getDate(), swagShareHolderFl.getDate());
            Assert.assertEquals(thriftShareHolderFl.getFio(), swagShareHolderFl.getFio());
            Assert.assertEquals(0, Double.compare(thriftShareHolderFl.getCapitalSharesPercent(), swagShareHolderFl.getCapitalSharesPercent().doubleValue()));
            Assert.assertEquals(0, Double.compare(thriftShareHolderFl.getVotingSharesPercent(), swagShareHolderFl.getVotingSharesPercent().doubleValue()));
        }
        int thriftShareholdersUl = thriftShareholders.getShareholdersUl().size();
        for (int i = 0; i < thriftShareholdersUl; i++) {
            ShareHolderUL thriftShareHolderUL = thriftShareholders.getShareholdersUl().get(i);
            ShareHolderUl swagShareHolderUl = swagShareholders.getShareHoldersUl().get(i);
            Assert.assertEquals(thriftShareHolderUL.getAddress(), swagShareHolderUl.getAddress());
            Assert.assertEquals(thriftShareHolderUL.getDate(), swagShareHolderUl.getDate());
            Assert.assertEquals(thriftShareHolderUL.getFullName(), swagShareHolderUl.getFullName());
            Assert.assertEquals(thriftShareHolderUL.getInn(), swagShareHolderUl.getInn());
            Assert.assertEquals(thriftShareHolderUL.getOgrn(), swagShareHolderUl.getOgrn());
            Assert.assertEquals(0, Double.compare(thriftShareHolderUL.getCapitalSharesPercent(), swagShareHolderUl.getCapitalSharesPercent().doubleValue()));
            Assert.assertEquals(0, Double.compare(thriftShareHolderUL.getVotingSharesPercent(), swagShareHolderUl.getVotingSharesPercent().doubleValue()));
        }
        int thriftShareholdersOtherSize = thriftShareholders.getShareholdersOther().size();
        for (int i = 0; i < thriftShareholdersOtherSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther thriftShareHolderOther = thriftShareholders.getShareholdersOther().get(i);
            ShareHolderOther swagShareHolderOther = swagShareholders.getShareHoldersOther().get(i);
            Assert.assertEquals(thriftShareHolderOther.getAddress(), swagShareHolderOther.getAddress());
            Assert.assertEquals(thriftShareHolderOther.getDate(), swagShareHolderOther.getDate());
            Assert.assertEquals(thriftShareHolderOther.getFullName(), swagShareHolderOther.getFullName());
            Assert.assertEquals(0, Double.compare(thriftShareHolderOther.getCapitalSharesPercent(), swagShareHolderOther.getCapitalSharesPercent().doubleValue()));
            Assert.assertEquals(0, Double.compare(thriftShareHolderOther.getVotingSharesPercent(), swagShareHolderOther.getVotingSharesPercent().doubleValue()));
        }
    }

}
