import {Inject, Injectable, PLATFORM_ID} from "@angular/core";
import {
    HttpErrorResponse,
    HttpEvent,
    HttpHandler,
    HttpInterceptor,
    HttpRequest,
    HttpResponse
} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {isPlatformBrowser} from "@angular/common";
import {tap} from "rxjs";

@Injectable()
export class ResponceTypeInterceptor implements HttpInterceptor {
    constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        const cloned = req.clone({

        });

        return next.handle(req);
    }
}
