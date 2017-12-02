import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MovieContent } from './movie-content.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MovieContentService {

    private resourceUrl = SERVER_API_URL + 'api/movie-contents';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(movieContent: MovieContent): Observable<MovieContent> {
        const copy = this.convert(movieContent);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(movieContent: MovieContent): Observable<MovieContent> {
        const copy = this.convert(movieContent);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MovieContent> {
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
     * Convert a returned JSON object to MovieContent.
     */
    private convertItemFromServer(json: any): MovieContent {
        const entity: MovieContent = Object.assign(new MovieContent(), json);
        entity.creationTime = this.dateUtils
            .convertLocalDateFromServer(json.creationTime);
        entity.updateDate = this.dateUtils
            .convertLocalDateFromServer(json.updateDate);
        return entity;
    }

    /**
     * Convert a MovieContent to a JSON which can be sent to the server.
     */
    private convert(movieContent: MovieContent): MovieContent {
        const copy: MovieContent = Object.assign({}, movieContent);
        copy.creationTime = this.dateUtils
            .convertLocalDateToServer(movieContent.creationTime);
        copy.updateDate = this.dateUtils
            .convertLocalDateToServer(movieContent.updateDate);
        return copy;
    }
}
