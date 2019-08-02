package com.rbkmoney.dark.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchClaim {

    private String requestID;
    private String deadline;
    private String limit;
    private String continuationToken;
    private List<String> claimStatuses;

}
