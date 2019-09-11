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
    public com.rbkmoney.swag.questionary.model.FinancialPosition toSwag(FinancialPosition value, SwagConverterContext ctx) {
        if (value.isSetAnnualFinancialStatements()) {
            return new AnnualFinancialStatements();
        } else if (value.isSetAnnualTaxReturnWithMark()) {
            return new AnnualTaxReturnWithMark();
        } else if (value.isSetAnnualTaxReturnWithoutMark()) {
            return new AnnualTaxReturnWithoutMark();
        } else if (value.isSetAnnualTaxReturnWithoutMarkPaper()) {
            return new AnnualTaxReturnWithoutMarkPaper();
        } else if (value.isSetLetterOfGuarantee()) {
            return new LetterOfGuarantee();
        } else if (value.isSetQuarterlyTaxReturnWithMark()) {
            return new QuarterlyTaxReturnWithMark();
        } else if (value.isSetQuarterlyTaxReturnWithoutMark()) {
            return new QuarterlyTaxReturnWithoutMark();
        } else if (value.isSetStatementOfDuty()) {
            return new StatementOfDuty();
        }

        throw new IllegalArgumentException("Unknown financialPosition type: " + value.getClass().getName());
    }

    @Override
    public FinancialPosition toThrift(com.rbkmoney.swag.questionary.model.FinancialPosition value, ThriftConverterContext ctx) {
        switch (value.getFinancialPositionType()) {
            case ANNUALFINANCIALSTATEMENTS:
                return FinancialPosition.annual_financial_statements(new com.rbkmoney.questionary.AnnualFinancialStatements());
            case ANNUALTAXRETURNWITHMARK:
                return FinancialPosition.annual_tax_return_with_mark(new com.rbkmoney.questionary.AnnualTaxReturnWithMark());
            case ANNUALTAXRETURNWITHOUTMARK:
                return FinancialPosition.annual_tax_return_without_mark(new com.rbkmoney.questionary.AnnualTaxReturnWithoutMark());
            case ANNUALTAXRETURNWITHOUTMARKPAPER:
                return FinancialPosition.annual_tax_return_without_mark_paper(new com.rbkmoney.questionary.AnnualTaxReturnWithoutMarkPaper());
            case LETTEROFGUARANTEE:
                return FinancialPosition.letter_of_guarantee(new com.rbkmoney.questionary.LetterOfGuarantee());
            case QUARTERLYTAXRETURNWITHMARK:
                return FinancialPosition.quarterly_tax_return_with_mark(new com.rbkmoney.questionary.QuarterlyTaxReturnWithMark());
            case QUARTERLYTAXRETURNWITHOUTMARK:
                return FinancialPosition.quarterly_tax_return_without_mark(new com.rbkmoney.questionary.QuarterlyTaxReturnWithoutMark());
            case STATEMENTOFDUTY:
                return FinancialPosition.statement_of_duty(new com.rbkmoney.questionary.StatementOfDuty());
            default:
                throw new IllegalArgumentException("Unknown financialPosition type: " + value.getFinancialPositionType());
        }
    }

}
