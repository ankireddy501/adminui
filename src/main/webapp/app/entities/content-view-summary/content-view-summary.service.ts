import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ContentViewSummary } from './content-view-summary.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ContentViewSummaryService {

    private resourceUrl = SERVER_API_URL + 'api/content-view-summaries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(contentViewSummary: ContentViewSummary): Observable<ContentViewSummary> {
        const copy = this.convert(contentViewSummary);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(contentViewSummary: ContentViewSummary): Observable<ContentViewSummary> {
        const copy = this.convert(contentViewSummary);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ContentViewSummary> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.viewDate = this.dateUtils
            .convertLocalDateFromServer(entity.viewDate);
    }

    private convert(contentViewSummary: ContentViewSummary): ContentViewSummary {
        const copy: ContentViewSummary = Object.assign({}, contentViewSummary);
        copy.viewDate = this.dateUtils
            .convertLocalDateToServer(contentViewSummary.viewDate);
        return copy;
    }
}
