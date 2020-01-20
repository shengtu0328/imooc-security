/**
 *
 */
package com.imooc.security.core.validate.code;

import com.imooc.security.core.validate.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author zhailiang
 *
 */
public interface ValidateCodeGenerator {

    ImageCode generate(ServletWebRequest request);

}
