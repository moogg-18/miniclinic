package tw.edu.fju.miniclinic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginRequiredInterceptor
        implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        HttpSession session =
                request.getSession();

        Object loggedIn =
                session.getAttribute("loggedInDoctorId");

        // 已登入
        if (loggedIn != null) {
            return true;
        }

        // 未登入
        String path = request.getRequestURI();

        // API
        if (path.startsWith("/api/")) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter()
                    .write("{\"error\":\"請先登入\"}");

        } else {

            // 頁面
            response.sendRedirect("/login");
        }

        return false;
    }
}