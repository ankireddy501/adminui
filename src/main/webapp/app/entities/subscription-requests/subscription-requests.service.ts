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
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(subscriptionRequests: SubscriptionRequests): Observable<SubscriptionRequests> {
        const copy = this.convert(subscriptionRequests);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SubscriptionRequests> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to SubscriptionRequests.
     */
    private convertItemFromServer(json: any): SubscriptionRequests {
        const entity: SubscriptionRequests = Object.assign(new SubscriptionRequests(), json);
        entity.requestedDate = this.dateUtils
            .convertLocalDateFromServer(json.requestedDate);
        entity.approvalDate = this.dateUtils
            .convertLocalDateFromServer(json.approvalDate);
        entity.startDate = this.dateUtils
            .convertLocalDateFromServer(json.startDate);
        entity.endDate = this.dateUtils
            .convertLocalDateFromServer(json.endDate);
        return entity;
    }

    /**
     * Convert a SubscriptionRequests to a JSON which can be sent to the server.
     */
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
