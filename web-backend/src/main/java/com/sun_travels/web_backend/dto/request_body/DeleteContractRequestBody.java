package com.sun_travels.web_backend.dto.request_body;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Valid
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteContractRequestBody {
    @NotNull(message = "Contract Id cannot be null")
    private Long contractId;
}
