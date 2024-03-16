package com.mt.common.system.controller;
import com.mt.common.core.web.BaseController;
import com.mt.common.core.web.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mt.common.core.web.Server;
@Api(tags = "服务器信息")
@RestController
@RequestMapping("/api/monitor/server")
public class ServerController extends BaseController {
    @GetMapping()
    @ApiOperation("服务器信息获取")
    public JsonResult getInfo() throws Exception{
        Server server=new Server();
        server.copyTo();
        return JsonResult.ok().setData(server);
    }

}
