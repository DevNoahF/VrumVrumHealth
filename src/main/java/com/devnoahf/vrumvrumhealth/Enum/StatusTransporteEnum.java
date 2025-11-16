package com.devnoahf.vrumvrumhealth.Enum;

public enum StatusTransporteEnum {
	REALIZADO("realizado"),
	CANCELADO("cancelado"),
	NAO_COMPARECEU("não compareceu");

	private String statusTransporte;

	StatusTransporteEnum(String statusTransporte) {
		this.statusTransporte = statusTransporte;
	}

	public String getStatusTransporte() {
		return statusTransporte;
	}
}
