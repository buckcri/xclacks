
<span style="font-variant:small-caps;">At last, Sir Terry, we must walk together.</span> 

# XClacks

## About

XClacks is a Servlet 3.0 filter adding the http header `X-Clacks-Overhead: GNU Terry Pratchett` to all responses.

Please see [this The Guardian article](https://www.theguardian.com/books/shortcuts/2015/mar/17/terry-pratchetts-name-lives-on-in-the-clacks-with-hidden-web-code) for information about the origins and purpose of that header.

## How To Use

Add this jar to your classpath. You may also need to configure servlet filter classpath scanning in your framework/embedded web server: 

### Spring Boot 3.x or higher

Add `@ServletComponentScan("io.github.buckcri.xclacks")`, for example when declaring `@SpringBootApplication`:
````
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ServletComponentScan("io.github.buckcri.xclacks")
class MySpringBootApplication
````