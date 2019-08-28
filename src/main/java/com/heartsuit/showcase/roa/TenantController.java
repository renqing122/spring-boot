package com.heartsuit.showcase.roa;

import com.heartsuit.showcase.domain.Tenant;
import com.heartsuit.showcase.service.IMailService;
import com.heartsuit.showcase.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2019/8/24 0024 16:59
 */
@RestController
@RequestMapping("/tenant")
public class TenantController
{
        private TenantService tenantService;
        private IMailService iMailService;
        @Autowired
        public TenantController(TenantService tenantService,IMailService iMailService) {
                this.tenantService = tenantService;
                this.iMailService = iMailService;
        }
        /**
         * 新增租客
         * @param tenant 租客信息
         * @return 新增是否成功
         */
        @RequestMapping(value = "/insert", method = RequestMethod.POST)
        @ResponseBody
        public String insert(@RequestBody Tenant tenant) {
                String flag = this.tenantService.insert(tenant);
                if(flag.equals("0")){
                        String code=tenantService.findCodeByEmail(tenant).getCode();
                        iMailService.sendHtmlMail(tenant.getEmail(),"青年租房管理系统账户激活","<a href=\"http://114.116.9.214:8666/tenant/checkCode?code="+code+"\">激活请点击:"+code+"</a>");
                        return "0";
                }
                else {
                        return "1";
                }
        }

        /**
         * 查询所有租客
         * @return 所有租客信息
         */
        @RequestMapping(value = "/queryall", method = RequestMethod.GET)
        @ResponseBody
        public List<Tenant> query() {
                return tenantService.findAll();
        }

        /**
         * 更新邮箱激活状态
         * @param code 租客
         * @return 更新是否成功
         */
        @RequestMapping(value = "/checkCode",method = RequestMethod.GET)
        @ResponseBody
        public String updateActivationStatus(String code) {
                tenantService.updateActivationStatus(tenantService.findEmailByCode(tenantService.createTenantByCode(code)));
                return "true";
        }

        /**
         * 检查邮箱是否重复
         * @param tenant 租客信息
         * @exception
         * @return
         */
        @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
        @ResponseBody
        public String checkEmail(@RequestBody Tenant tenant) {
                return tenantService.checkEmail(tenant);
        }

        /**
         * 登录
         * @param tenant
         * @return
         */
        @RequestMapping(value = "/login", method = RequestMethod.POST)
        @ResponseBody
        public String login(@RequestBody Tenant tenant) {
                return tenantService.login(tenant);
        }

        @PostMapping("/greeting")
        public String greeting(@RequestBody HashMap<String, String> params) {
                return "Hello DevOps. Welcome " + params.get("name");
        }
}
