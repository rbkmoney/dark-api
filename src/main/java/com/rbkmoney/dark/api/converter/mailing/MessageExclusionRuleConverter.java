package com.rbkmoney.dark.api.converter.mailing;

import com.rbkmoney.damsel.message_sender.ExclusionType;
import com.rbkmoney.damsel.message_sender.MessageExclusion;
import com.rbkmoney.damsel.message_sender.MessageExclusionObject;
import com.rbkmoney.damsel.message_sender.ShopExclusionRule;
import com.rbkmoney.swag.sender.model.MessageExclusionRule;
import com.rbkmoney.swag.sender.model.MessageExclusionRuleRequest;
import com.rbkmoney.swag.sender.model.MessageExclusionRuleType;
import org.springframework.stereotype.Component;

@Component
public class MessageExclusionRuleConverter {

    public MessageExclusionRule toSwag(MessageExclusionObject from) {
        MessageExclusionRule to = new MessageExclusionRule();
        to.setId(from.getRef().getId());
        to.setName(from.getExclusion().getName());
        if (from.getExclusion().getRule().isSetShopRule()) {
            to.setType(MessageExclusionRuleType.SHOP);
            to.setValue(from.getExclusion().getRule().getShopRule().shop_ids);
        } else {
            throw new IllegalArgumentException("Rule type " + from + " has not supported yet");
        }
        return to;
    }

    public MessageExclusion toThrift(MessageExclusionRuleRequest from) {
        MessageExclusion to = new MessageExclusion();
        to.setName(from.getName());
        com.rbkmoney.damsel.message_sender.MessageExclusionRule rule =
                new com.rbkmoney.damsel.message_sender.MessageExclusionRule();
        if (MessageExclusionRuleType.SHOP.equals(from.getType())) {
            rule.setShopRule(new ShopExclusionRule(from.getValue()));
        } else {
            throw new IllegalArgumentException("Rule type " + from.getType() + " has not supported yet");
        }
        to.setRule(rule);
        return to;
    }

    public ExclusionType resolveType(MessageExclusionRuleType from) {
        if (MessageExclusionRuleType.SHOP.equals(from)) {
            return ExclusionType.SHOP;
        } else {
            throw  new IllegalArgumentException("Rule type " + from + " has not supported yet");
        }
    }
}
