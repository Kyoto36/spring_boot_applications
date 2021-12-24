package com.ls.library.swagger2;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.service.ApiKey;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * swagger2 属性配置
 * 必须配置 prefix ，才能有提示
 *
 */
@Data
//@RefreshScope
@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
public class SwaggerProperties {
    public static final String PREFIX = "aif7.swagger";
    /**
     * 是否开启swagger
     **/
    private Boolean enabled = true;

    /**
     * 是否生产环境
     */
    private Boolean production = false;
    /**
     * 离线文档路径
     */
    private Markdown markdown = new Markdown();


    /**
     * 访问账号密码
     */
    private Basic basic = new Basic();

    /**
     * 标题
     **/
    private String title = "在线文档";
    private String group = "";
    /**
     * 描述
     **/
    private String description = "aif7-cloud 在线文档";
    /**
     * 版本
     **/
    private String version = "1.0";
    /**
     * 许可证
     **/
    private String license = "";
    /**
     * 许可证URL
     **/
    private String licenseUrl = "";
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "";

    private Contact contact = new Contact();

    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "com.aif7";
    
    private List<ApiKey> apiKeys = new ArrayList<>();

    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();
    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();

    /**
     * 分组文档
     **/
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();

    /**
     * host信息
     **/
    private String host = "";

    /**
     * 排序
     */
    private Integer order = 1;

    /**
     * 全局参数配置
     **/
    private List<GlobalOperationParameter> globalOperationParameters;

    public String getGroup() {
        if (group == null || "".equals(group)) {
            return title;
        }
        return group;
    }

    @Setter
    @Getter
    public static class GlobalOperationParameter {
        /**
         * 参数名
         **/
        private String name;

        /**
         * 描述信息
         **/
        private String description = "全局参数";

        /**
         * 指定参数类型
         **/
        private String modelRef = "String";

        /**
         * 参数放在哪个地方:header,query,path,body.form
         **/
        private String parameterType = "header";

        /**
         * 参数是否必须传
         **/
        private Boolean required = false;
        private Boolean allowMultiple = false;
        private AllowableValues allowableValues;
        private Boolean hidden = false;
        private String pattern = "";
        private String collectionFormat = "";
        /**
         * 默认值
         */
        private String defaultValue = "";
        /**
         * 允许为空
         */
        private Boolean allowEmptyValue = true;
        /**
         * 排序
         */
        private int order = 1;
    }

    @Data
    public static class DocketInfo {
        /**
         * 标题
         **/
        private String title = "在线文档";
        /**
         * 自定义组名
         */
        private String group = "";
        /**
         * 描述
         **/
        private String description = "aif7-cloud 在线文档";
        /**
         * 版本
         **/
        private String version = "1.0";
        /**
         * 许可证
         **/
        private String license = "";
        /**
         * 许可证URL
         **/
        private String licenseUrl = "";
        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";

        private Contact contact = new Contact();

        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";

        /**
         * swagger会解析的url规则
         **/
        private List<String> basePath = new ArrayList<>();
        /**
         * 在basePath基础上需要排除的url规则
         **/
        private List<String> excludePath = new ArrayList<>();

        private List<GlobalOperationParameter> globalOperationParameters;

        /**
         *
         */
        private List<ApiKey> apiKeys = new ArrayList<>();
        /**
         * 排序
         */
        private Integer order = 1;

        public String getGroup() {
            if (group == null || "".equals(group)) {
                return title;
            }
            return group;
        }
    }

    @Data
    public static class Contact {
        /**
         * 联系人
         **/
        private String name = "aif7";
        /**
         * 联系人url
         **/
        private String url = "https://github.com/aif7/aif7-cloud";
        /**
         * 联系人email
         **/
        private String email = "tongzx@jc.com";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Basic {
        private Boolean enable = false;
        private String username = "aif7";
        private String password = "aif7";
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Markdown {
        private Boolean enable = false;
        private String basePath = "classpath:markdown/*";
    }
}
