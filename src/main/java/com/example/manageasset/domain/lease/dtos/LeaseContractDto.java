package com.example.manageasset.domain.lease.dtos;

import com.example.manageasset.domain.lease.models.LeaseContract;
import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.example.manageasset.domain.user.models.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaseContractDto {
    private String id;
    @JsonProperty("client")
    private UserDto clientDto;
    @JsonProperty("user")
    private UserDto userDto;
    private String reason;
    @JsonProperty("revoked_at")
    private Long revokedAt;
    @JsonProperty("leased_at")
    private Long leasedAt;
    private String note;
    @JsonProperty("asset_leased")
    private List<AssetLeasedDto> assetLeasedDtos;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;

    public static LeaseContractDto fromModel(LeaseContract leaseContract){
        return new LeaseContractDto(
                leaseContract.getId(),
                UserDto.fromModel(leaseContract.getClient()),
                UserDto.fromModel(leaseContract.getUser()),
                leaseContract.getReason(),
                leaseContract.getRevokedAt().asLong(),
                leaseContract.getLeasedAt().asLong(),
                leaseContract.getNote(),
                leaseContract.getAssetLeaseds().stream().map(AssetLeasedDto::fromModel).collect(Collectors.toList()),
                leaseContract.getCreatedAt().asLong(),
                leaseContract.getUpdatedAt().asLong()
        );
    }

}
