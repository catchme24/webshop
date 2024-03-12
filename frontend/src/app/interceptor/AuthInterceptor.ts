import { isPlatformBrowser } from "@angular/common";
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from "@angular/common/http";
import { Inject, Injectable, PLATFORM_ID } from "@angular/core";
import { tap } from "rxjs";
import { Observable } from "rxjs/internal/Observable";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
    constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

    intercept(
        req: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {

        // Проверка, выполняется ли код на стороне браузера
        if (isPlatformBrowser(this.platformId)) {
            const token = localStorage.getItem("token");

            if (token) {
                const authReq = req.clone({
                    setHeaders: { Authorization: `Bearer ${token}` }
                });

                return next.handle(authReq).pipe(
                    tap(
                        (event) => {
                            if (event instanceof HttpResponse)
                                console.log('Server response');
                        },
                        (err) => {
                            if (err instanceof HttpErrorResponse) {
                                if (err.status == 401)
                                    console.log('Unauthorized');
                            }
                        }
                    )
                );
            } else {
                // Обработка отсутствия токена (например, перенаправление на страницу входа)
                console.log('Token is not available');
                // Возвращаем пустой Observable или делаем что-то еще
                return next.handle(req);
            }
        } else {
            // Если код выполняется на стороне сервера, просто передаем запрос дальше
            return next.handle(req);
        }
    }
}