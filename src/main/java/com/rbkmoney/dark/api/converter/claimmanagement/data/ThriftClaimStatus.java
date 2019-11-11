package com.rbkmoney.dark.api.converter.claimmanagement.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThriftClaimStatus {

    public static final String PENDING = "pending";
    public static final String REVIEW = "review";
    public static final String PENDING_ACCEPTANCE = "pendingAcceptance";
    public static final String ACCEPTED = "accepted";
    public static final String DENIED = "denied";
    public static final String REVOKED = "revoked";

}
