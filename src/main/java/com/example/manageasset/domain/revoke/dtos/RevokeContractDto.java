package com.example.manageasset.domain.revoke.dtos;

import com.example.manageasset.domain.lease.dtos.LeaseContractDto;
import com.example.manageasset.domain.revoke.models.RevokeContract;
import com.example.manageasset.domain.user.dtos.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RevokeContractDto {
    private String id;
    @JsonProperty("client")
    private UserDto clientDto;
    @JsonProperty("user")
    private UserDto userDto;
    private String reason;
    @JsonProperty("revoked_at")
    private Long revokedAt;
    private String note;
    @JsonProperty("lease_contract")
    private LeaseContractDto leaseContractDto;
    @JsonProperty("created_at")
    private Long createdAt;
    @JsonProperty("updated_at")
    private Long updatedAt;

    public static RevokeContractDto fromModel(RevokeContract revokeContract) {
        return new RevokeContractDto(
                revokeContract.getId(),
                UserDto.fromModel(revokeContract.getClient()),
                UserDto.fromModel(revokeContract.getUser()),
                revokeContract.getReason(),
                revokeContract.getRevokedAt().asLong(),
                revokeContract.getNote(),
                LeaseContractDto.fromModel(revokeContract.getLeaseContract()),
                revokeContract.getCreatedAt().asLong(),
                revokeContract.getUpdatedAt().asLong()
        );
    }
}
