import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ImageContent } from './image-content.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ImageContentService {

    private resourceUrl = SERVER_API_URL + 'api/image-contents';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(imageContent: ImageContent): Observable<ImageContent> {
        const copy = this.convert(imageContent);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(imageContent: ImageContent): Observable<ImageContent> {
        const copy = this.convert(imageContent);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<ImageContent> {
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
     * Convert a returned JSON object to ImageContent.
     */
    private convertItemFromServer(json: any): ImageContent {
        const entity: ImageContent = Object.assign(new ImageContent(), json);
        entity.creationDate = this.dateUtils
            .convertLocalDateFromServer(json.creationDate);
        entity.updateDate = this.dateUtils
            .convertLocalDateFromServer(json.updateDate);
        return entity;
    }

    /**
     * Convert a ImageContent to a JSON which can be sent to the server.
     */
    private convert(imageContent: ImageContent): ImageContent {
        const copy: ImageContent = Object.assign({}, imageContent);
        copy.creationDate = this.dateUtils
            .convertLocalDateToServer(imageContent.creationDate);
        copy.updateDate = this.dateUtils
            .convertLocalDateToServer(imageContent.updateDate);
        return copy;
    }
}
