package com.softdignitas.ddd.web.rest.errors;

public class RecordNotFoundException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public RecordNotFoundException(String entityName, String errorKey) {
        super(ErrorConstants.RECORD_NOT_FOUND, "Could not find a record!", entityName, errorKey);
    }
}
