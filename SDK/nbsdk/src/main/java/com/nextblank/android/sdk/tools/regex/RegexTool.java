package com.nextblank.android.sdk.tools.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexTool {

    /**
     * 正则表达式:验证用户名(不包含中文和特殊字符)如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
     */
    private static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式:验证密码(不包含特殊字符)
     */
    private static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式:验证手机号
     */
    private static final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$";

    /**
     * 正则表达式:验证邮箱
     */
    private static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式:验证汉字(1-9个汉字)  {1,9} 自定义区间
     */
    private static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{1,9}$";

    /**
     * 正则表达式:验证身份证
     */
    private static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|xX)$)";

    /**
     * 正则表达式:验证URL
     */
    private static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

    /**
     * 正则表达式:验证IP地址
     */
    private static final String REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

    /**
     * 正则表达式:验证车牌号
     */
    private static final String REGEX_PLATENUM = "^[\\u4E00-\\u9FA5]([0-9A-Z]{6})|([0-9A-Z]{5}[\\u4E00-\\u9FA5]{1})$";

    /**
     * 正则表达式:验证车辆vin码
     */
    private static final String REGEX_VIN = "^[0-9A-Za-z]+$";

    private static final Object lock = new Object();
    private static volatile RegexTool instance;

    private RegexTool() {
    }

    public static RegexTool instance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new RegexTool();
                }
            }
        }
        return instance;
    }

    public boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    public boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    public boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    public boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    public boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    public boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }

    public boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }

    public boolean isIPAddress(String ipAddress) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddress);
    }

    public boolean isPlateNum(String plateNum) {
        return Pattern.matches(REGEX_PLATENUM, plateNum);
    }

    public boolean isVehicleVin(String vin) {
        return Pattern.matches(REGEX_VIN, vin);
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 获取正则匹配的部分
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return 正则匹配的部分
     */
    public List<String> getMatches(String regex, CharSequence input) {
        if (input == null) return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 获取正则匹配分组
     *
     * @param input 要分组的字符串
     * @param regex 正则表达式
     * @return 正则匹配分组
     */
    public String[] getSplits(String input, String regex) {
        if (input == null) return null;
        return input.split(regex);
    }

    /**
     * 替换正则匹配的第一部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换正则匹配的第一部分
     */
    public String getReplaceFirst(String input, String regex, String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换所有正则匹配的部分
     *
     * @param input       要替换的字符串
     * @param regex       正则表达式
     * @param replacement 代替者
     * @return 替换所有正则匹配的部分
     */
    public String getReplaceAll(String input, String regex, String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
