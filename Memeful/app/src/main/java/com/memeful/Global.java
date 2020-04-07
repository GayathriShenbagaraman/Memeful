package com.memeful;

public class Global {
    public static enum RequestMethod
    {
        GET, POST, PUT;
    }

    public static enum RequestType
    {
        STRING_REQUEST, IMAGE_REQUEST, JSON_REQUEST, ITEM_NUM_REQUEST, GENERATE_SKU, DISCARD_ITEM, BOX_CHECK, CAMFI_CKHECK, GET_BOOTH, CAMFI_RESET, POLL_SERVER, SYNC_DATA;
    }
}
