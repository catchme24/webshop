import {HttpHeaders} from "@angular/common/http";

export type Options = {
    headers: HttpHeaders;
    responseType: 'json';
    observe: 'response';
};
