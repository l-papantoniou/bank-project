package com.bank.bankproject.utility;


import com.bank.bankproject.constants.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Log4j2
public final class Util {

    // Define a constant for the comma-separated splitting regex
    private static final String COMMA_SEPARATED_SPLIT_REGEX = ",\\s*";

    private Util() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String> getFieldNames(Object object) {

        List<String> fieldNames = new ArrayList<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            // Exclusion for sonar
            field.setAccessible(true); // You need to set modifier to public first.
            fieldNames.add(field.getName());
        }

        return fieldNames;
    }


    public static JsonNode createJsonNode(List<String> fields) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode fieldsArray = mapper.convertValue(fields, ArrayNode.class);
        ObjectNode fieldsObjectNode = mapper.createObjectNode();
        fieldsObjectNode.putArray("Name").addAll(fieldsArray);
        return mapper.createObjectNode().set("Fields", fieldsObjectNode);
    }

    /*public static Type getClassType(String name) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(name);
        TypeToken<?> typeToken = TypeToken.of(clazz);
        return typeToken.getType();
    }*/

    // full qualified class name
    public static Class<?> getClassFromName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

    public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        // Exclusion for sonar
        field.setAccessible(true);  // You need to set modifier to public first.
        return field.get(object);
    }


    /*public static Set<String> getClassNames(String packageName) throws IOException {

        return ClassPath.from(ClassLoader.getSystemClassLoader())
            .getAllClasses()
            .stream()
            .filter(clazz -> clazz.getPackageName()
                .equalsIgnoreCase(packageName))
            .map(ClassPath.ClassInfo::getName)
            .collect(Collectors.toSet());
    }*/

 /*   @Nullable
    public static ResponseEntity<Object> getBadInputResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ValidationErrorsDTO validationErrorsDTO = new ValidationErrorsDTO();
            validationErrorsDTO.addValidationErrors(bindingResult.getFieldErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorsDTO);
        }
        return null;
    }*/

//    public static String getUserIdentity() {
//        String result = null;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null) {
//            result = authentication.getName();
//        }
//        return result;
//    }

  /*  public static String getUserName() {
        return getClaimValue(Constants.PREFERRED_USERNAME);
    }*/


    public static Optional<String> getRequestHeaderValue(String headerName) {
        String headerValue = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
            final String xRequestHeaderValue = servletRequest.getHeader(headerName);

            if (xRequestHeaderValue != null) {
                headerValue = URLEncoder.encode(xRequestHeaderValue, StandardCharsets.UTF_8);
            }
        }
        return Optional.ofNullable(headerValue);
    }

    public static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }


    public static <T> T getCurrentProxy(Class<T> obj) {
        return ((T) AopContext.currentProxy());
    }

  /*  public static LocalDateTime getLocalDateTime(BigDecimal timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
*/

    public static HttpHeaders setResultXTotalCount(Integer totalCount) {
        //Get the number of entities for pagination needs in UI
        int xTotalCount = 0;
        if (totalCount != null) {
            xTotalCount = totalCount;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", Integer.toString(xTotalCount));
        return headers;
    }

    /*public static Double round(Double number) {
        // use it in order to round up a double value up to two places after decimal
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        bigDecimal = bigDecimal.setScale(Constants.DECIMAL_DIGITS, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }*/

    // Returns empty string if the input is null, else returns the string is self
    public static String getNonNullString(String input) {
        return input != null ? input : "";
    }


    public static String getJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }

    public static String getFullRegistrationNumber(BigDecimal dcDocRegNo, String dcDocRegLetter, String dcDocLetter, String regDate) {

        StringBuilder registerNumberStr = new StringBuilder();

        if (dcDocRegNo != null) {
            registerNumberStr.append(dcDocRegNo.longValue());
        }

        if (dcDocRegLetter != null) {
            registerNumberStr.append(dcDocRegLetter);
        }

        if (!StringUtils.isEmpty(dcDocLetter)) {
            registerNumberStr.append(dcDocLetter);
        }
        if (!StringUtils.isEmpty(regDate)) {
            registerNumberStr.append(Constants.BREAK).append(getRegisterDateFormatted(regDate));
        }
        return registerNumberStr.toString();
    }

    /**
     * getRegisterDateFormatted
     *
     * @param regDate a string date
     * @return the formatted string date or null if the date is invalid
     */
    public static String getRegisterDateFormatted(String regDate) {
        if (!isValidInput(regDate)) {
            return null;
        }

        Date date = extractDate(regDate);
        if (date == null) {
            return null;
        }

        return formatDate(date);
    }

    public static String convertToDelimitedList(List<String> inputList) {

        String delimiter = Constants.COMMA;

        if (inputList.isEmpty()) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : inputList) {
            sb.append(str).append(delimiter);
        }
        return StringUtils.removeEnd(sb.toString(), delimiter);
    }

    public static Long convertStringToNumeric(String identifier) {
        if (identifier != null) {
            return Long.valueOf(identifier);
        }
        return null;
    }


    /**
     * Checks if the provided regDate is a valid input.
     *
     * @param regDate a string date
     * @return true if valid, false otherwise
     */
    private static boolean isValidInput(String regDate) {
        if (regDate == null || regDate.length() < 4) {
            return false;
        }

        return regDate.matches("\\d+");
    }

    /**
     * Extracts a Date object from the provided regDate string.
     *
     * @param regDate a string date
     * @return a Date object or null if extraction fails
     */
    private static Date extractDate(String regDate) {
        SimpleDateFormat sdf;
        try {
            if (regDate.length() == 4) {
                sdf = new SimpleDateFormat("yyyy");
                return sdf.parse(regDate);
            } else if (regDate.length() <= 6) {
                sdf = new SimpleDateFormat("yyyyMM");
                return sdf.parse(regDate);
            } else {
                sdf = new SimpleDateFormat("yyyyMMdd");
                return sdf.parse(regDate);
            }
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Formats the provided Date object into a string.
     *
     * @param date a Date object
     * @return a formatted string
     */
    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * Convert a comma-separated string into a list of strings.
     *
     * @param input A string that contains values separated by commas.
     *              It can optionally have spaces after the commas.
     * @return A List of strings extracted from the input.
     * If the input is null or empty, an empty list is returned.
     */
    public static List<String> commaSeparatedStringToList(String input) {
        // Check if the input string is null or empty after trimming any white spaces
        if (input == null || input.trim().isEmpty()) {
            return Collections.emptyList();
        }
        // Split the input string by the comma pattern and return as a list
        return Arrays.asList(input.split(COMMA_SEPARATED_SPLIT_REGEX));
    }

}
