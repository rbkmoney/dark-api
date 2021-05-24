package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.FinancialPosition;
import com.rbkmoney.swag.questionary.model.*;
import com.rbkmoney.swag.questionary.model.FinancialPosition.FinancialPositionTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class FinancialPositionConverter implements
        ThriftConverter<FinancialPosition, com.rbkmoney.swag.questionary.model.FinancialPosition>,
        SwagConverter<com.rbkmoney.swag.questionary.model.FinancialPosition, FinancialPosition> {

    @Override
    public com.rbkmoney.swag.questionary.model.FinancialPosition toSwag(FinancialPosition value,
                                                                        SwagConverterContext ctx) {
        if (value.isSetAnnualFinancialStatements()) {
            return new AnnualFinancialStatements()
                    .financialPositionType(FinancialPositionTypeEnum.ANNUALFINANCIALSTATEMENTS);
        } else if (value.isSetAnnualTaxReturnWithMark()) {
            return new AnnualTaxReturnWithMark()
                    .financialPositionType(FinancialPositionTypeEnum.ANNUALTAXRETURNWITHMARK);
        } else if (value.isSetAnnualTaxReturnWithoutMark()) {
            return new AnnualTaxReturnWithoutMark()
                    .financialPositionType(FinancialPositionTypeEnum.ANNUALTAXRETURNWITHOUTMARK);
        } else if (value.isSetAnnualTaxReturnWithoutMarkPaper()) {
            return new AnnualTaxReturnWithoutMarkPaper()
                    .financialPositionType(FinancialPositionTypeEnum.ANNUALTAXRETURNWITHOUTMARKPAPER);
        } else if (value.isSetLetterOfGuarantee()) {
            return new LetterOfGuarantee().financialPositionType(FinancialPositionTypeEnum.LETTEROFGUARANTEE);
        } else if (value.isSetQuarterlyTaxReturnWithMark()) {
            return new QuarterlyTaxReturnWithMark()
                    .financialPositionType(FinancialPositionTypeEnum.QUARTERLYTAXRETURNWITHMARK);
        } else if (value.isSetQuarterlyTaxReturnWithoutMark()) {
            return new QuarterlyTaxReturnWithoutMark()
                    .financialPositionType(FinancialPositionTypeEnum.QUARTERLYTAXRETURNWITHOUTMARK);
        } else if (value.isSetStatementOfDuty()) {
            return new StatementOfDuty().financialPositionType(FinancialPositionTypeEnum.STATEMENTOFDUTY);
        }

        throw new IllegalArgumentException("Unknown financialPosition type: " + value.getClass().getName());
    }

    @Override
    public FinancialPosition toThrift(com.rbkmoney.swag.questionary.model.FinancialPosition value,
                                      ThriftConverterContext ctx) {
        FinancialPosition financialPosition = new FinancialPosition();
        switch (value.getFinancialPositionType()) {
            case ANNUALFINANCIALSTATEMENTS:
                financialPosition
                        .setAnnualFinancialStatements(new com.rbkmoney.questionary.AnnualFinancialStatements());
                return financialPosition;
            case ANNUALTAXRETURNWITHMARK:
                financialPosition.setAnnualTaxReturnWithMark(new com.rbkmoney.questionary.AnnualTaxReturnWithMark());
                return financialPosition;
            case ANNUALTAXRETURNWITHOUTMARK:
                financialPosition
                        .setAnnualTaxReturnWithoutMark(new com.rbkmoney.questionary.AnnualTaxReturnWithoutMark());
                return financialPosition;
            case ANNUALTAXRETURNWITHOUTMARKPAPER:
                financialPosition.setAnnualTaxReturnWithoutMarkPaper(
                        new com.rbkmoney.questionary.AnnualTaxReturnWithoutMarkPaper());
                return financialPosition;
            case LETTEROFGUARANTEE:
                financialPosition.setLetterOfGuarantee(new com.rbkmoney.questionary.LetterOfGuarantee());
                return financialPosition;
            case QUARTERLYTAXRETURNWITHMARK:
                financialPosition
                        .setQuarterlyTaxReturnWithMark(new com.rbkmoney.questionary.QuarterlyTaxReturnWithMark());
                return financialPosition;
            case QUARTERLYTAXRETURNWITHOUTMARK:
                financialPosition
                        .setQuarterlyTaxReturnWithoutMark(new com.rbkmoney.questionary.QuarterlyTaxReturnWithoutMark());
                return financialPosition;
            case STATEMENTOFDUTY:
                financialPosition.setStatementOfDuty(new com.rbkmoney.questionary.StatementOfDuty());
                return financialPosition;
            default:
                throw new IllegalArgumentException(
                        "Unknown financialPosition type: " + value.getFinancialPositionType());
        }
    }

}
