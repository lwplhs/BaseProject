package com.lwp.website.utils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/08/20/18:09
 * @Description:
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Net Util .
 *
 * @author waterborn
 */
public class MacUtil {

    private static List<String> macAddressStr = null;
    private static String computerName = System.getenv().get("COMPUTERNAME");

    private static final String[] windowsCommand = { "ipconfig", "/all" };
    private static final String[] linuxCommand = { "/sbin/ifconfig", "-a" };
    private static final Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*",
            Pattern.CASE_INSENSITIVE);

    /**
     * 获取多个网卡地址
     *
     * @return
     * @throws IOException
     */
    private final static List<String> getMacAddressList() throws IOException {
        final ArrayList<String> macAddressList = new ArrayList<String>();
        final String os = System.getProperty("os.name");
        final String command[];

        if (os.startsWith("Windows")) {
            command = windowsCommand;
        } else if (os.startsWith("Linux")) {
            command = linuxCommand;
        } else {
            throw new IOException("Unknow operating system:" + os);
        }
        // 执行命令
        final Process process = Runtime.getRuntime().exec(command);

        BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null; (line = bufReader.readLine()) != null;) {
            Matcher matcher = macPattern.matcher(line);
            if (matcher.matches()) {
                macAddressList.add(matcher.group(1));
                // macAddressList.add(matcher.group(1).replaceAll("[-:]",
                // ""));//去掉MAC中的“-”
            }
        }

        process.destroy();
        bufReader.close();
        return macAddressList;
    }

    /**
     * 获取网卡地址
     *
     * @return
     */
    public static List<String> getMacAddress() {
        if (macAddressStr == null || StringUtil.isNull(macAddressStr)) {
            List<String> macList = new ArrayList();

            try {
                macList = getMacAddressList();
            } catch (IOException e) {
                e.printStackTrace();
            }

            macAddressStr = macList;

        }

        return macAddressStr;
    }

    /**
     * 限制创建实例
     */
    private MacUtil() {

    }

    public static void main(String[] args) throws IOException {

        List<String> macList = MacUtil.getMacAddress();
        if(StringUtil.isNotNull(macList)){
            for (int i = 0; i < macList.size(); i++) {
                System.out.println(macList.get(i));
            }
        }

    }

}