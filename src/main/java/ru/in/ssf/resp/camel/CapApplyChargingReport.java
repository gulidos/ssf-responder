package ru.in.ssf.resp.camel;


import lombok.Getter;
import lombok.Setter;
import ru.in.ssf.resp.camel.primitives.CallLeg;
import ru.in.ssf.resp.camel.primitives.CapMsg;

public class CapApplyChargingReport extends CapMessage {
	@Getter @Setter
	private String stringNumber;
	@Getter @Setter
	private CallLeg partyToCharge;
	@Getter @Setter
	private TimeInformation timeInformationType;
	@Getter @Setter
	private int timeInformation;
	@Getter @Setter
	private boolean callAactive;
	
	
	public CapApplyChargingReport() {
		super(CapMsg.CAP_APPLY_CHARGING_REPORT, 0, 0, "");
	}
	
	public CapApplyChargingReport(Builder b) {
		super(b);
		this.stringNumber = b.stringNumber;
		this.partyToCharge = b.partyToCharge;
		this.timeInformationType = b.timeInformationType;
		this.timeInformation = b.timeInformation;
		this.callAactive = b.callAactive;
	}


	public enum TimeInformation {
		timeIfNoTariffSwitch, timeSinceTariffSwitch, tariffSwitchInterval
	}
	
	
	public static class Builder extends CapMessage.InnerBuilder<Builder> {
		private String stringNumber = "";
		private CallLeg partyToCharge = CallLeg.sendingSide;
		private TimeInformation timeInformationType = TimeInformation.timeIfNoTariffSwitch;
		private int timeInformation = 0;
		private boolean callAactive = false;
		
		public Builder() {}
		public Builder setStringNumber(String stringNumber) {	this.stringNumber = stringNumber; return this;	}
		public Builder setPartyToCharge(CallLeg partyToCharge) {this.partyToCharge = partyToCharge; return this;}
		public Builder setTimeInformationType(TimeInformation timeInformationType) {
			this.timeInformationType = timeInformationType;
			return this;
		}
		public Builder setTimeInformation(int timeInformation) {this.timeInformation = timeInformation; return this;}
		public Builder setCallAactive(boolean callAactive) {this.callAactive = callAactive; return this;}


		@Override
		public CapApplyChargingReport build() {
			setMsgType(CapMsg.CAP_APPLY_CHARGING_REPORT);
			return new CapApplyChargingReport(this);
		}
	}
}
