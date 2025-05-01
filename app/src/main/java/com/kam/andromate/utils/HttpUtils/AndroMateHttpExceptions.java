package com.kam.andromate.utils.HttpUtils;

public class AndroMateHttpExceptions {

    static class INVALID_RESPONSE_CODE_EXCEPTION extends Exception {

        public INVALID_RESPONSE_CODE_EXCEPTION(HttpMethods httpMethods, int responseCode) {
            super(httpMethods + " Request Failed: HTTP code "+responseCode);
        }

    }

}
