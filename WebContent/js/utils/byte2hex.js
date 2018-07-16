function convert_to_formated_hex(byte_arr) {
    var hex_str = "",
        i,
        len,
        tmp_hex;
    
//    if (!is_array(byte_arr)) {
//        return false;
//    }
    
    len = byte_arr.length;
    
    for (i = 0; i < len; ++i) {
        if (byte_arr[i] < 0) {
            byte_arr[i] = byte_arr[i] + 256;
        }
        tmp_hex = byte_arr[i].toString(16);
        
        // Add leading zero.
        if (tmp_hex.length == 1) tmp_hex = "0" + tmp_hex;
        
        hex_str += tmp_hex;
    }
    
    return hex_str.trim();
}