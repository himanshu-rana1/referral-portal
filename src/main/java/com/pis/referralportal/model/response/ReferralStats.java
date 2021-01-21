package com.pis.referralportal.model.response;

public class ReferralStats {

    private int totalReferrals;
    private int selectedCandidates;
    private int rejectedCandidates;
    private int inProcess;

    public ReferralStats() {
    }

    public int getTotalReferrals() {
        return totalReferrals;
    }

    public void setTotalReferrals(int totalReferrals) {
        this.totalReferrals = totalReferrals;
    }

    public int getSelectedCandidates() {
        return selectedCandidates;
    }

    public void setSelectedCandidates(int selectedCandidates) {
        this.selectedCandidates = selectedCandidates;
    }

    public int getRejectedCandidates() {
        return rejectedCandidates;
    }

    public void setRejectedCandidates(int rejectedCandidates) {
        this.rejectedCandidates = rejectedCandidates;
    }

    public int getInProcess() {
        return inProcess;
    }

    public void setInProcess(int inProcess) {
        this.inProcess = inProcess;
    }
}
