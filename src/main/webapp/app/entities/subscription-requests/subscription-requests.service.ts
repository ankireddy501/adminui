import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SubscriptionRequests } from './subscription-requests.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SubscriptionRequestsService {

    private resourceUrl = SERVER_API_URL + 'api/subscription-requests';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(subscriptionRequests: SubscriptionRequests): Observable<SubscriptionRequests> {
        const copy = this.convert(subscriptionRequests);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(subscriptionRequests: SubscriptionRequests): Observable<SubscriptionRequests> {
        const copy = this.convert(subscriptionRequests);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<SubscriptionRequests> {
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
        entity.requestedDate = this.dateUtils
            .convertLocalDateFromServer(entity.requestedDate);
        entity.approvalDate = this.dateUtils
            .convertLocalDateFromServer(entity.approvalDate);
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(entity.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(entity.endDate);
    }

    private convert(subscriptionRequests: SubscriptionRequests): SubscriptionRequests {
        const copy: SubscriptionRequests = Object.assign({}, subscriptionRequests);
        copy.requestedDate = this.dateUtils
            .convertLocalDateToServer(subscriptionRequests.requestedDate);
        copy.approvalDate = this.dateUtils
            .convertLocalDateToServer(subscriptionRequests.approvalDate);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(subscriptionRequests.startDate);
        copy.endDate = this.dateUtils
            .convertLocalDateToServer(subscriptionRequests.endDate);
        return copy;
    }
}
