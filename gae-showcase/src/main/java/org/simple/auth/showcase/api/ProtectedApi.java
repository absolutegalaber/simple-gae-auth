package org.simple.auth.showcase.api;

import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Api(name = "showcase", version = "v1", description = "A protected resource.",
        scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT, Constant.API_EXPLORER_CLIENT_ID},
        audiences = {Constants.WEB_CLIENT}
)
@Slf4j
public class ProtectedApi {

    @ApiMethod(name = "protectedValue", path = "protectedValue", httpMethod = ApiMethod.HttpMethod.GET)
    public ProtectedObject protectedValue(HttpServletRequest request) {
        log.info("Request:     {}", request);
        log.info("HeaderNames: {}", request.getHeaderNames());
        ProtectedObject protectedObject = new ProtectedObject();
        protectedObject.setName("secret");
        protectedObject.setValue("value");
        return protectedObject;
    }
}
