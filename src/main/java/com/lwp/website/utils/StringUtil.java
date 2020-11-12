package com.lwp.website.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 使用hutool工具类
 */
public class StringUtil {
    private static Map charMap = new HashMap();
    public static Logger loger = LoggerFactory.getLogger(StringUtil.class);

    static {
        charMap.put("\"", "&quot;");
        charMap.put(">", "&gt;");
        charMap.put("<", "&lt;");
        charMap.put(" ", "&nbsp;");
    }

    public StringUtil() {
    }

    public static String parseToHtml(String content) {
        if (content == null) {
            return "";
        } else {
            char[] arr = content.toCharArray();
            String[] strArr = new String[arr.length];
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < arr.length; ++i) {
                if (charMap.containsKey("" + arr[i])) {
                    sb.append((String)charMap.get("" + arr[i]));
                } else {
                    sb.append(arr[i]);
                }
            }

            return sb.toString();
        }
    }

    public static String deleteFilePostfix(String fileName) {
        int i = fileName.lastIndexOf(".");
        return i < 0 ? fileName : fileName.substring(0, i);
    }

    public static boolean isNull(String str) {
        return str == null || str.equals("");
    }

    public static boolean isNull(Object obj){
        if (obj == null)
        {
            return true;
        }
        if ((obj instanceof List))
        {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String))
        {
            return ((String) obj).trim().equals("");
        }
        return false;
    }
    public static boolean isNotNull(Object obj){
        return !isNull(obj);
    }

    public static String replace(String str, String target, String replaceStr) {
        int start = -1;

        while(true) {
            start = str.indexOf(target, start + 1);
            if (start < 0) {
                return str;
            }

            int end = start + target.length();
            str = str.substring(0, start) + replaceStr + str.substring(end, str.length());
        }
    }

    public static String join(String[] strs, String code) {
        if (strs != null && strs.length != 0) {
            if (code != null && code.length() != 0) {
                String returnValue = "";

                for(int i = 0; i < strs.length; ++i) {
                    if (!"".equals(strs[i]) && !"null".equals(strs[i])) {
                        returnValue = returnValue + strs[i] + code;
                    }
                }

                if (returnValue.length() < 1) {
                    return "";
                } else {
                    return returnValue.substring(0, returnValue.lastIndexOf(code));
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static Map<String, String> parseStringForMap(String str, String split) {
        if (str == null) {
            return null;
        } else {
            String[] fields = str.split(split);
            Map<String, String> map = new LinkedHashMap();

            for(int i = 0; i < fields.length; ++i) {
                if (fields[i].indexOf("=") < 0) {
                    loger.error("error format:" + fields[i]);
                } else {
                    String key = fields[i].substring(0, fields[i].indexOf("="));
                    String value = fields[i].substring(fields[i].indexOf("=") + 1, fields[i].length());
                    map.put(key, value);
                }
            }

            return map;
        }
    }

    public static Map<String, String> parseStringForMapByFlag(String str, String flag) {
        if (str == null) {
            return null;
        } else {
            Map<String, String> map = new LinkedHashMap();
            if (str.indexOf(flag) < 0) {
                loger.error("error format:" + str);
                return null;
            } else {
                String key = str.substring(0, str.indexOf(flag)).trim();
                String value = str.substring(str.indexOf(flag) + 1, str.length());
                map.put(key, value);
                return map;
            }
        }
    }

    public static Map parseStringForMap(String str, String split, String split1) {
        if (str == null) {
            return null;
        } else {
            String[] fields = str.split(split1);
            Map map = new LinkedHashMap();

            for(int i = 0; i < fields.length; ++i) {
                if (fields[i] != null) {
                    fields[i] = fields[i].trim();
                }

                map.putAll(parseStringForMapByFlag(fields[i], split));
            }

            return map;
        }
    }

    public static String ISOToGBK(String str) {
        try {
            if (str == null) {
                return "null";
            } else {
                boolean isISO = false;

                for(int i = 0; i < str.length(); ++i) {
                    char a = str.charAt(i);
                    if (a < 255 && a > 127) {
                        isISO = true;
                        break;
                    }
                }

                if (!isISO) {
                    return str;
                } else {
                    byte[] tempByte = str.getBytes("ISO-8859-1");
                    String strTemp = new String(tempByte, "UTF-8");
                    return strTemp;
                }
            }
        } catch (Exception var6) {
            return "null";
        }
    }

    public static String ISOToUTF8(String str) {
        try {
            if (str == null) {
                return "null";
            } else {
                boolean isISO = false;

                for(int i = 0; i < str.length(); ++i) {
                    char a = str.charAt(i);
                    if (a < 255 && a > 127) {
                        isISO = true;
                        break;
                    }
                }

                if (!isISO) {
                    return str;
                } else {
                    byte[] tempByte = str.getBytes("ISO-8859-1");
                    String strTemp = new String(tempByte, "UTF-8");
                    return strTemp;
                }
            }
        } catch (Exception var6) {
            return "null";
        }
    }

    /**
     * 获取时间的指定格式
     * @return
     */
    public static String getDate(Date date,String format){
        DateFormat df = new SimpleDateFormat(format);
        String dateName = df.format(date);
        return dateName;
    }

    public static String NullToEmpty(String str) {
        if (str == null || "null".equalsIgnoreCase(str)) {
            str = "";
        }

        return str;
    }

    public static String OR(String value1, String value2) {
        String value = "";
        char[] arr1 = value1.toCharArray();
        char[] arr2 = value2.toCharArray();

        for(int i = 0; i < arr1.length; ++i) {
            int a = Integer.parseInt(String.valueOf(arr1[i]));
            int b = Integer.parseInt(String.valueOf(arr2[i]));
            value = value + String.valueOf(a | b);
        }

        return value;
    }

    public static String fillZeroBeforeNum(String value, int iLen) {
        for(int i = value.length(); i < iLen; ++i) {
            value = '0' + value;
        }

        return value;
    }

    public static String[] sort(String[] obj) {
        if (obj != null) {
            Arrays.sort(obj, 0, obj.length);
        }

        return obj;
    }

    public static String distanctString(String str) {
        if (str != null && !str.equals("")) {
            String[] strArr = str.split(",");
            if (strArr.length == 1) {
                return str;
            } else {
                StringBuilder sb = new StringBuilder(strArr[0].toUpperCase());

                for(int i = 1; i < strArr.length; ++i) {
                    if (sb.indexOf(strArr[i].toUpperCase()) <= -1) {
                        sb.append(",").append(strArr[i].toUpperCase());
                    }
                }

                return sb.toString();
            }
        } else {
            return "";
        }
    }

    public static String replaceQuotes(String sql) {
        return sql == null ? "" : sql.replaceAll("'", "''");
    }
}
