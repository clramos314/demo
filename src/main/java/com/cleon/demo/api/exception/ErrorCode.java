package com.cleon.demo.api.exception;

public enum ErrorCode {

    GENERIC_ERROR("DEMO-0001", "The system is unable to complete the request. Contact system support."),
    HTTP_MEDIATYPE_NOT_SUPPORTED("DEMO-0002", "Requested media type is not supported. Please use application/json as 'Content-Type' header value"),
    JSON_PARSE_ERROR("DEMO-0003", "Make sure request payload should be a valid JSON object."),
    HTTP_MESSAGE_NOT_READABLE("DEMO-0004", "Make sure request payload should be a valid JSON object according to 'Content-Type'."),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED("DEMO-0005", "Request method not supported."),
    ILLEGAL_ARGUMENT_EXCEPTION("DEMO-0006", "Invalid data passed.");

    private String errCode;
    private String errMsgKey;

    private ErrorCode(final String errCode, final String errMsgKey) {
        this.errCode = errCode;
        this.errMsgKey = errMsgKey;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @return the errMsgKey
     */
    public String getErrMsgKey() {
        return errMsgKey;
    }
}
