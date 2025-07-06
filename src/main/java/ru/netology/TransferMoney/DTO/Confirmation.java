package ru.netology.TransferMoney.DTO;

public class Confirmation {

    private String operationId;
    private String code;

    public Confirmation(String operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public String getOperationId () {
        return operationId;
    }

    public String getCode () {
        return code;
    }

    public  void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
